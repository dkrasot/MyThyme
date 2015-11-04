package twitter.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import twitter.Tweet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcTweetRepo implements TweetRepository {
    private JdbcOperations jdbc;
    @Autowired
    public JdbcTweetRepo(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public List<Tweet> findRecentTweets() {
        return jdbc.query(
                "select id, message, created_at, latitude, longitude" +
                        " from Tweets" +
                        " order by created_at desc limit 20",
                new TweetRowMapper());
    }
    @Override
    public List<Tweet> findTweets(long max, int count) {
        return jdbc.query(
                "select id, message, created_at, latitude, longitude" +
                        " from Tweets" +
                        " where id < ?" +
                        " order by created_at desc limit 20",
// TODO? Get away hardcode + check count >0 ??limit 20 -> limit count??
                new TweetRowMapper(), max);
    }

    @Override
    public Tweet findOne(long id) {
        return jdbc.queryForObject(
                "select id, message, created_at, latitude, longitude" +
                        " from Tweets" +
                        " where id = ?",
                new TweetRowMapper(), id);
    }

    @Override
    public void save(Tweet tweet) {
        jdbc.update(
                "insert into Tweets (message, created_at, latitude, longitude)" +
                        " values (?, ?, ?, ?)",
                tweet.getMessage(),
                tweet.getTime(),
                tweet.getLatitude(),
                tweet.getLongitude());
    }

    private static class TweetRowMapper implements RowMapper<Tweet> {
        public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Tweet(
                    rs.getLong("id"),
                    rs.getString("message"),
                    rs.getDate("created_at"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"));
        }
    }
}
