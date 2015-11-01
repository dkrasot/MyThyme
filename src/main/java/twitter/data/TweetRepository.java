package twitter.data;

import twitter.Tweet;

import java.util.List;

/**
 * Created on 30.10.2015.
 */
public interface TweetRepository {

    List<Tweet> findRecentTweets();

    List<Tweet> findTweets(long max, int count);

    Tweet findOne(long id);

    void save(Tweet tweet);
}
