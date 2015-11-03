package twitter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import twitter.Tweet;
import twitter.data.TweetRepository;
import twitter.web.exceptions.DuplicateTweetException;
import twitter.web.exceptions.TweetNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created on 30.10.2015.
 */
@Controller
@RequestMapping("/tweets")
public class TweetController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private TweetRepository repo;

    @Autowired
    public TweetController(TweetRepository repo){
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Tweet> tweets(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
                              @RequestParam(value = "count", defaultValue = "20") int count){
        return repo.findTweets(max, count);
    }

    @RequestMapping(value = "/{tweetId}", method = RequestMethod.GET)
    public String tweet(@PathVariable("tweetId") long tweetId, Model model){
        Tweet tweet = repo.findOne(tweetId);
        if ( tweet == null ) {
            throw new TweetNotFoundException();
        }
        model.addAttribute(tweet);
        return "tweet";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveTweet(TweetForm form, Model model) throws Exception{
        try {
            repo.save(new Tweet(null, form.getMessage(), new Date(), form.getLatitude(), form.getLongitude()));
            return "redirect:/tweets";
        } catch (DuplicateTweetException e) {
            return "error/duplicate";
        }
    }

    @ExceptionHandler(DuplicateTweetException.class)
    public String handleNotFound(){
        //TODO ??? AppWideExceptionHandler or this ???
        return "error/duplicate";
    }
}
