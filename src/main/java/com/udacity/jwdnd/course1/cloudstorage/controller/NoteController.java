package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    @PostMapping("/save")
    public String handleSaveNote(Authentication authentication, RedirectAttributes redirectAttributes, Note note) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        Integer noteId = note.getNoteId();
        note.setUserId(userId);
        int cnt = 0;

        if (noteId == null) {
            cnt = noteService.insertNote(note);
        } else {
            if (noteService.checkNoteExist(noteId, userId)) {
                cnt = noteService.updateNoteById(note);
            }
        }

        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Save Note failed. Try again!");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String handleDeleteNote(Authentication authentication, RedirectAttributes redirectAttributes, Integer noteId) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        int cnt = noteService.deleteNoteById(noteId, userId);

        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Delete Note failed. Try again!");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
