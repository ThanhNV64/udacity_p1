package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private FileMapper fileMapper;

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFileByName(MultipartFile file, Integer userId) {
        String fileName = file.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        fileName = fileName.substring(startIndex + 1);
        return fileMapper.getFileByName(fileName, userId);
    }

    public File getFileById(Integer fileId, Integer userId) {
        return fileMapper.getFileById(fileId, userId);
    }

    public int insertFile(MultipartFile file, Integer userId) throws IOException {
        String fileName = file.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        fileName = fileName.substring(startIndex + 1);

        File newFile = new File();
        newFile.setFileName(fileName);
        newFile.setContentType(file.getContentType());
        newFile.setFileSize(String.valueOf(file.getSize()));
        newFile.setFileData(file.getBytes());
        newFile.setUserId(userId);

        return fileMapper.insertFile(newFile);
    }

    public int deleteFileById(Integer fileId, Integer userId) {
        return fileMapper.deleteFileById(fileId, userId);
    }

    public boolean checkFileExist(MultipartFile file, Integer userId) {
        return getFileByName(file, userId) != null;
    }
}
