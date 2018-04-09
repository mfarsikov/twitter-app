package twitterapp.twitter.seen;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeenTweetsRepository extends JpaRepository<SeenTweets, String> {
    Optional<SeenTweets> findByUserName(String username);
}
