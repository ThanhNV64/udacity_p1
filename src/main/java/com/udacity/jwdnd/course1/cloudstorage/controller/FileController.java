package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/fileUpload")
@AllArgsConstructor
public class FileController {

    private FileService fileService;
    private UserService userService;

    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        String errMsg = "";

        if (fileUpload.isEmpty()) {
            errMsg = "File is empty";
        }

        if (fileService.checkFileExist(fileUpload, userId)) {
            errMsg = "File is already exist";
        }

        if (!errMsg.isBlank()) {
            redirectAttributes.addFlashAttribute("errMsg", errMsg);
            return "redirect:/result?error";
        }

        int cnt = fileService.insertFile(fileUpload, userId);
        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Upload file failed");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> handleViewFile(Integer fileId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        File file = fileService.getFileById(fileId, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    @GetMapping("/delete")
    public String handleDeleteFile(Integer fileId, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        int cnt = fileService.deleteFileById(fileId, userId);
        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Not exist file");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

}
