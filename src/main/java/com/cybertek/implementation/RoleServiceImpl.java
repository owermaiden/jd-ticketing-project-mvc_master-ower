package com.cybertek.implementation;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;
import com.cybertek.mapper.RoleMapper;
import com.cybertek.repository.RoleRepository;
import com.cybertek.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
       List<Role> list = roleRepository.findAll();
       // we need to convert Role to RoleDTO.............mappers
       return list.stream().map(obj -> roleMapper.convertToDto(obj)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
       Role role = roleRepository.findById(id).get();
       return roleMapper.convertToDto(role);
    }
}
