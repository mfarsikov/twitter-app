package twitterapp.twitter;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import twitterapp.twitter.seen.SeenTweetsService;
import twitterapp.user.User;

@Controller
@AllArgsConstructor
public class TwitterController {

    private final ConnectionRepository connectionRepository;
    private final Twitter twitter;
    private final SeenTweetsService seenTweetsService;

    @GetMapping("/")
    public String index() {
        return "redirect:/tweets";
    }

    @GetMapping("/tweets")
    public String tweets(Model model,
                         @ModelAttribute TwitterSearchRequestDto search,
                         @AuthenticationPrincipal User user) {

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        model.addAttribute(twitter.userOperations().getUserProfile());

        if (search.getQueryString() != null && !search.getQueryString().isEmpty()) {

            List<Tweet> allTweets = twitter.searchOperations()
                                           .search(search.getQueryString())
                                           .getTweets();

            List<Tweet> tweets = filterIfLucky(allTweets,
                                               search.getAction(),
                                               user.getUsername());

            seenTweetsService.markTweetsAsSeen(user.getUsername(), tweets);

            model.addAttribute("tweets", tweets);

        }
        return "tweets";
    }

    private List<Tweet> filterIfLucky(List<Tweet> tweets, String action, String username) {
        if (action.equals("I'm feeling lucky!")) {
            Set<Integer> seenTweetHashes = seenTweetsService.tweetsSeenBy(username);
            return tweets.stream()
                         .filter(twt -> !seenTweetHashes.contains(twt.getText().hashCode()))
                         .findAny()
                         .map(Collections::singletonList)
                         .orElse(Collections.emptyList());
        } else {
            return tweets;
        }
    }
}
