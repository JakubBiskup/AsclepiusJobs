package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.Role;
import com.example.asclepiusjobs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
