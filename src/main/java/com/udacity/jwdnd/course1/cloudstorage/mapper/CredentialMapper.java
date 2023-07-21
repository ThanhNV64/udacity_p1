package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId} and userid = #{userId}")
    int updateCredentialById(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} and userid = #{userId}")
    int deleteCredentialById(Integer credentialId, Integer userId);
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} and userid = #{userId}")
    Credential getCredentialById(Integer credentialId, Integer userId);
}
