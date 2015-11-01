package twitter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter.User;
import twitter.data.UserRepository;

import javax.validation.Valid;

/**
 * Created on 30.10.2015.
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository repo;

    @Autowired
    public UserController(UserRepository repo){
        this.repo = repo;
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegForm(Model model){
        model.addAttribute(new User());
        return "registerForm";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistration(@Valid User user, Errors errors){
        //1. BindingResult can be used instead of Errors
        //2. note - for example: if name of User in Model would be profile: @Valid User profile then we must add @ModelAttribute("profile) like that;
        //method (@Valid @ModelAttribute("profile) User profile, ...) {...   in HTML template - th:object="${profile}"
        if(errors.hasErrors()){
            return "registerForm";
        }
        repo.save(user);
        return "redirect:/user/"+ user.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showUserProfile(@PathVariable String username, Model model){
        User user = repo.findByUsername(username);
        model.addAttribute(user);
        return "profile";
    }
}
