package twitter.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import twitter.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 30.10.2015.
 */
@Repository
public class JdbcUserRepo implements UserRepository {
    private JdbcOperations jdbc;
    @Autowired
    public JdbcUserRepo(JdbcOperations jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public User save(User user) {
        jdbc.update("insert into Users (username, password, first_name, last_name, email)" +
                        " values (?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
        return user; // TODO: Determine value for id ???
    }
    @Override
    public User findByUsername(String username) {
        return jdbc.queryForObject(
                "select id, username, null, first_name, last_name, email from Users where username=?",
                new UserRowMapper(),
                username);
    }

    private static class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    null,
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"));
        }
    }
}
