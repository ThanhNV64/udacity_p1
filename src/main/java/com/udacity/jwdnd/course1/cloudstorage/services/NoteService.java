package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public Note getNoteById(Integer noteId, Integer userId) {
        return noteMapper.getNoteById(noteId, userId);
    }

    public Integer insertNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public Integer deleteNoteById(Integer noteId, Integer userId) {
        return noteMapper.deleteNoteById(noteId, userId);
    }

    public Integer updateNoteById(Note note) {
        return noteMapper.updateNoteById(note);
    }

    public boolean checkNoteExist(Integer noteId, Integer userId) {
        return getNoteById(noteId, userId) != null;
    }
}
