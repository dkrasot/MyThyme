package twitter.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import twitter.Tweet;
import twitter.data.TweetRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.CoreMatchers.hasItems;

/**
 * Created on 31.10.2015.
 */
public class TweetControllerTest {

    @Test
    public void testTweet() throws Exception{
        Tweet expectedTweet = new Tweet("Hello World", new Date());
        TweetRepository mockRepository = mock(TweetRepository.class);
        when(mockRepository.findOne(12345)).thenReturn(expectedTweet);

        TweetController controller = new TweetController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/tweets/12345"))
                .andExpect(view().name("tweet"))
                .andExpect(model().attributeExists("tweet"))
                .andExpect(model().attribute("tweet", expectedTweet));
    }
    @Test
    public void mustSaveTweet() throws Exception{
        TweetRepository mockRepository = mock(TweetRepository.class);
        TweetController controller = new TweetController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/tweets")
                .param("message","Hello World")
                .param("latitude","-89.1234567")
                .param("longitude","12.3456789"))
                .andExpect(redirectedUrl("/tweets"));
        verify(mockRepository, atLeastOnce())
                .save(new Tweet(null, "Hello World", new Date(), -89.1234567, 12.3456789));
    }

    @Test
    public void shouldShowRecentTweets() throws Exception{
        List<Tweet> expectedTweets = createTweetList(20);
        TweetRepository mockRepository = mock(TweetRepository.class);
        when(mockRepository.findTweets(Long.MAX_VALUE, 20)).thenReturn(expectedTweets);//findRecentTweets ???
        // findRecentTweets()
        TweetController controller = new TweetController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/tweets.html")) //!!! .jsp
                .build();

        mockMvc.perform(get("/tweets"))
                .andExpect(view().name("tweets"))
                .andExpect(model().attributeExists("tweetList"))
                .andExpect(model().attribute("tweetList", hasItems(expectedTweets.toArray())));
    }

    @Test
    public void shouldShowPagedTweets() throws Exception{
        List<Tweet> expectedTweets = createTweetList(50);
        TweetRepository mockRepository = mock(TweetRepository.class);
        when(mockRepository.findTweets(12345678, 50)).thenReturn(expectedTweets);

        TweetController controller = new TweetController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/tweets.html")) //!!! .jsp
                .build();

        mockMvc.perform(get("/tweets?max=12345678&count=50"))
                .andExpect(view().name("tweets"))
                .andExpect(model().attributeExists("tweetList"))
                .andExpect(model().attribute("tweetList", hasItems(expectedTweets.toArray())));

        new Tweet()
    }


    private List<Tweet> createTweetList(int count) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0; i < count; i++) {
            tweets.add(new Tweet("Tweet " + i, new Date()));
        }
        return tweets;
    }
}
