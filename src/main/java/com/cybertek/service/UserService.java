package com.cybertek.service;

import com.cybertek.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO user);
    UserDTO findByID(String username);
    List<UserDTO> findAll();
    void delete(UserDTO user);
    void deleteByID(String username);

}
