package twitter.data;

import twitter.User;

public interface UserRepository {

    User save(User user);

    User findByUsername(String username);

}
