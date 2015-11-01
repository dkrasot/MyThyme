package twitter.web;


import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import twitter.User;
import twitter.data.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

/**
 * Created on 31.10.2015.
 */
public class UserControllerTest {
    @Test
    public void shouldShowRegistration() throws Exception{
        UserRepository mockRepository = mock(UserRepository.class);
        UserController controller = new UserController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/user/register"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    public void shouldProcessRegistration() throws Exception{
        UserRepository mockRepository = mock(UserRepository.class);
        User unsaved = new User("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
        User saved = new User(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
        when(mockRepository.save(unsaved)).thenReturn(saved);

        UserController controller = new UserController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/user/register")
                .param("firstName", "Jack")
                .param("lastName", "Bauer")
                .param("username", "jbauer")
                .param("password", "24hours")
                .param("email", "jbauer@ctu.gov"))
                    .andExpect(redirectedUrl("/user/jbauer"));

        verify(mockRepository, atLeastOnce()).save(unsaved);
    }

    @Test
    public void shouldFailValidationWithNoData() throws Exception{
        UserRepository mockRepository = mock(UserRepository.class);
        UserController controller = new UserController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"))
                .andExpect(model().errorCount(5))
                .andExpect(model().attributeHasFieldErrors("user","firstName","lastName","username","password","email"));
    }
}
