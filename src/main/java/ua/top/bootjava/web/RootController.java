package ua.top.bootjava.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.top.bootjava.service.ResumeService;
import ua.top.bootjava.service.UserService;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;

import static ua.top.bootjava.SecurityUtil.setTestAuthUser;
import static ua.top.bootjava.util.UsersUtil.asAdmin;

@Slf4j
@Schema(hidden = true)
@Controller
@AllArgsConstructor
public class RootController {
    public UserService userService;
    public ResumeService resumeService;

//    @GetMapping(value = { "/"})
    public String root() {
        return "redirect:login";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/resumes")
    public String getResumes(Model model) {
        log.info("resumes");
        setTestAuthUser(asAdmin());
        List<ResumeTo> resumes = resumeService.getAllTos();
        model.addAttribute("resumes", resumes);
        return "resumes";
    }
}
