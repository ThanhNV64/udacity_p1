package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    int deleteFileById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFileByName(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    File getFileById(Integer fileId, Integer userId);

}
