package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    private EncryptionService encryptionService;
    private UserService userService;

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        model.addAttribute("ListFileUpload", fileService.getAllFiles(userId));
        model.addAttribute("ListNote", noteService.getAllNotes(userId));
        model.addAttribute("ListCredentials", credentialService.getAllCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}
