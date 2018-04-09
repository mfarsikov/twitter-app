package twitterapp.twitter.seen;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class SeenTweets {
    @Id
    private String userName;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> seenTweetsHashes = new HashSet<>();

    public SeenTweets(String userName) {
        this.userName = userName;
    }

    public void addHashes(Set<Integer> tweetsHashes) {
        seenTweetsHashes.addAll(tweetsHashes);
    }
}
