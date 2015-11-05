package twitter.data.hibernate4;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import twitter.Tweet;
import twitter.data.TweetRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class HibernateTweetRepository implements TweetRepository {

    private SessionFactory sessionFactory;

    @Inject
    public HibernateTweetRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession(){
        return sessionFactory.getCurrentSession();
    }

    public long count(){
        return findAll().size();
    }

    @Override
    public List<Tweet> findRecentTweets() {//findRecent()
        return findRecentTweets(10);
    }

    public List<Tweet> findRecentTweets(int count) {//findRecent(int)
        return (List<Tweet>) tweetCriteria()
                .setMaxResults(count)
                .list();
    }

    @Override
    public Tweet findOne(long id) {
        return (Tweet) currentSession().get(Tweet.class, id);
    }

    public Tweet saveAndReturn(Tweet tweet){
        Serializable id = currentSession().save(tweet);
        return new Tweet(
                (Long) id,
                //tweet.getUser(),
                tweet.getMessage(),
                tweet.getTime(),//tweet.getPostedTime(),

                tweet.getLatitude(),tweet.getLongitude());
    }

    public List<Tweet> findByUserId(long userId){
        return tweetCriteria()
                .add(Restrictions.eq("user.id", userId))
                .list();
    }

    public void delete(long id){
        currentSession().delete(findOne(id));
    }

    public List<Tweet> findAll() {
        return (List<Tweet>) tweetCriteria().list();
    }

    private Criteria tweetCriteria(){
        return currentSession().createCriteria(Tweet.class)
                .addOrder(Order.desc("time"));//postedTime
    }



    @Override
    public List<Tweet> findTweets(long max, int count) { //TODO del this from TweetRepo -> findRecent(int)
        return null;
    }

    @Override
    public void save(Tweet tweet) {//TODO void save - change to Tweet save(Tweet) like saveAndReturn

    }
}
