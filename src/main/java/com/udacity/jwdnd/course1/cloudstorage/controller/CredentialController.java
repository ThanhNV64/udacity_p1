package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
@AllArgsConstructor
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    @GetMapping("/view")
    @ResponseBody
    public Credential getCredential(Integer credentialId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        Credential credential = credentialService.getCredentialById(credentialId, userId);
        if (credential == null) {
            return null;
        }
        credentialService.decryptPassword(credential);
        return credential;
    }

    @PostMapping("/save")
    public String handleAddCredential(Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        Integer credentialId = credential.getCredentialId();
        credential.setUserId(userId);
        int cnt = 0;

        if (credentialId == null) {
            cnt = credentialService.insertCredential(credential);
        } else {
            if (credentialService.getCredentialById(credentialId, userId) != null) {
                cnt = credentialService.updateCredentialById(credential);
            }
        }

        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Save Credential failed. Try again!");
            return "redirect:/result?error";
        }

        return "redirect:/result?success";
    }

    @GetMapping ("/delete")
    public String handleDeleteCredential(Authentication authentication, RedirectAttributes redirectAttributes, Integer credentialId) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        int cnt = credentialService.deleteCredentialById(credentialId, userId);
        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Delete Credential failed. Try again!");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
