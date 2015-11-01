package twitter.data;

import twitter.User;

/**
 * Created on 30.10.2015.
 */
public interface UserRepository {

    User save(User user);

    User findByUsername(String username);

}
