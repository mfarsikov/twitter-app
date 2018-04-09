package twitterapp.twitter.seen;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeenTweetsService {
    private final SeenTweetsRepository seenTweetsRepository;

    public void markTweetsAsSeen(String username, List<Tweet> tweets) {

        SeenTweets seenTweets = seenTweetsRepository.findByUserName(username)
                                                    .orElseGet(() -> new SeenTweets(username));

        Set<Integer> tweetsHashes = tweets.stream()
                                          .map(Tweet::getText)
                                          .map(Object::hashCode)
                                          .collect(Collectors.toSet());

        seenTweets.addHashes(tweetsHashes);

        seenTweetsRepository.save(seenTweets);
    }

    public Set<Integer> tweetsSeenBy(String username) {
        return seenTweetsRepository.findByUserName(username)
                                   .map(SeenTweets::getSeenTweetsHashes)
                                   .orElse(Collections.emptySet());
    }

}
