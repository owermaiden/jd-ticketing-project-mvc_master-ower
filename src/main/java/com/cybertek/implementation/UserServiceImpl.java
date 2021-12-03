package com.cybertek.implementation;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> users = userRepository.findAll(Sort.by("firstName"));
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDto(user) ;
    }

    @Override
    public void save(UserDTO dto) {
        User user = userMapper.convertToEntity(dto);
        userRepository.save(user);
    }

    @Override
    public UserDTO update(UserDTO dto) {
        //Find current user from database
        User user = userRepository.findByUserName(dto.getUserName());
        // Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        //set id to the converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
       List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
       return  users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    // hard delete -- not good
    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }
}
