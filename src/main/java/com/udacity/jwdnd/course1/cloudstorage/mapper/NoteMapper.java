package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId} and userid = #{userId}")
    int updateNoteById(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    int deleteNoteById(Integer noteId, Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    Note getNoteById(Integer noteId, Integer userId);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle} and notedescription = #{noteDescription} and userid = #{userId}")
    Note checkDuplicate(Note note);

}
