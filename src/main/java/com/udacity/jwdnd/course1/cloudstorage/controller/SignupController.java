package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@AllArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupView() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(User user, Model model, RedirectAttributes redirectAttributes) {
        String errMsg = "";

        if (userService.checkUserExist(user.getUsername())) {
            errMsg = "The username is already exist.";
        }

        if (errMsg.isBlank()) {
            int cnt = userService.createUser(user);
            if (cnt == 0) {
                errMsg = "Some strange error occurred. Please try again.";
            }
        }

        if (!errMsg.isBlank()) {
            model.addAttribute("signupError", errMsg);
            return "signup";
        }
        redirectAttributes.addFlashAttribute("signupSuccess", true);
        return "redirect:/login";
    }
}
