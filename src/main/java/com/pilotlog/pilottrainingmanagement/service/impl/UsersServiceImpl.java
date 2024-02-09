package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        super();
        this.usersRepository = usersRepository;
    }

    @Override
    public Users addUser(Users users){
        return usersRepository.save(users);
    }

    @Override
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @Override
    public Users getUsersById(String id){
//        Optional<Users> users = usersRepository.findById(id);
//        return users.get();
        return  usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", "Id", id));
    }

    @Override
    public Users editUsers(Users users, String id){
        Users existingUsers = usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Users", "Id", id)
        );

        existingUsers.setId_no(users.getId_no());
        existingUsers.setName(users.getName());
        existingUsers.setEmail(users.getEmail());
        existingUsers.setRank(users.getRank());
        existingUsers.setHub(users.getHub());
        existingUsers.setLicense_no(users.getLicense_no());
        existingUsers.setPhoto_profile(users.getPhoto_profile());
        existingUsers.setRole(users.getRole());
        existingUsers.setIs_active(users.getIs_active());
        existingUsers.setUpdated_at(users.getUpdated_at());
        existingUsers.setUpdated_by(users.getUpdated_by());
        usersRepository.save(existingUsers);

        return existingUsers;
    }

    @Override
    public void deleteUsers(String id) {
        // check whetther a users exist in a DB
        usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", "Id", id));

        usersRepository.deleteById(id);
    }

}
