package twitter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import twitter.User;
import twitter.data.UserRepository;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository repo;

    @Autowired
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegForm(Model model) {
        model.addAttribute(new User());
        return "registerForm";
    }



//    @RequestMapping(value="/register", method = RequestMethod.POST)
//    public String processRegistration(
//            @RequestPart(value = "profilePicture") MultipartFile profilePicture, @Valid User user, Errors errors) throws IOException {
//        profilePicture.transferTo(new File("/data/twitter/"+profilePicture.getOriginalFilename()));
//        // check for place on disk...
//        return "redirect:/user/"+ user.getUsername();
//    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistration( @Valid UserForm userForm, Errors errors) throws IllegalStateException, IOException {
        if(errors.hasErrors()){
            return "registerForm";
        }
        User user = userForm.toUser();
        repo.save(user);
        MultipartFile profilePicture = userForm.getProfilePicture();
        profilePicture.transferTo(new File("/tmp/twitter/"+ user.getUsername() + ".jpg"));
        return "redirect:/user/"+ user.getUsername();
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String processRegistration(
            @RequestPart(value="profilePicture", required=false) Part fileBytes,
            RedirectAttributes redirectAttributes,
            @Valid User user, Errors errors) throws IOException {
//1. Part - alternative to MultipartFile for Servlet 3.0 containers
// we don't need configuring of StandardServletMultipartResolver bean if using Part instead of MultipartFile
//3. If name of User in Model would be profile: @Valid User profile then we must add @ModelAttribute("profile"):
//  method (@Valid @ModelAttribute("profile) User profile, ...) {...   in HTML template - th:object="${profile}"
//4. BindingResult can be used instead of Errors

        if (errors.hasErrors()) {
            return "registerForm";
        }
        repo.save(user);
        redirectAttributes.addAttribute("username", user.getUsername());
        redirectAttributes.addFlashAttribute(user);
//        return "redirect:/user/" + user.getUsername();
        //вариант с {} экранирует небезопасные символы
        return "redirect:/user/{username}";
//for Example: if we add another attr by addAttribute (userId) but don't write it to redirect' {}
// we will get redirect:/user/usernameValue?userId=25
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showUserProfile(@PathVariable String username, Model model){
        //we are checking Model (maybe user was adding like Flash attribute)
        if (!model.containsAttribute("user")) {
            User user = repo.findByUsername(username);
            model.addAttribute(user);
        }
        return "profile";
    }
}
