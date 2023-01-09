package ua.top.bootjava.web.ui;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.top.bootjava.model.User;

//@ApiIgnore
@Schema(hidden = true)
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileUIController {
//    UserService service;

    @GetMapping
    public String profile() {
        return "profile";
    }

/*
    @PostMapping
    public String updateProfile(@Valid User user, BindingResult result, SessionStatus status) {
        log.info("updateProfile user {}", user);
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            service.update(user, SecurityUtil.authId());
            SecurityUtil.get().update(user);
            status.setComplete();
            return "resumes";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", null, user_exist);
            return "profile";
        }
    }
*/

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("register", true);
        return "profile";
    }

/*    @PostMapping("/register")
    public String saveRegister(@Valid User user, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            if (!user.getEmail().matches(email_matcher)) {
                result.rejectValue("email", null, email_error);
                model.addAttribute("register", true);
                return "profile";
            }
            try {
                service.create(user);
                status.setComplete();
                return "redirect:/login" + invite_sign_in + user.getEmail();
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("email", null, user_exist);
                model.addAttribute("register", true);
                return "profile";
            }
        }
    }*/
}
