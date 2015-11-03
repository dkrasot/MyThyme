package twitter.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by D.Krasota on 03.11.2015.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tweet Not Found")
public class TweetNotFoundException extends RuntimeException {

}
