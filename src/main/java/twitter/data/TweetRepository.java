package twitter.data;

import twitter.Tweet;

import java.util.List;

public interface TweetRepository {

    List<Tweet> findRecentTweets();

    List<Tweet> findTweets(long max, int count);

    Tweet findOne(long id);

    void save(Tweet tweet);
}
