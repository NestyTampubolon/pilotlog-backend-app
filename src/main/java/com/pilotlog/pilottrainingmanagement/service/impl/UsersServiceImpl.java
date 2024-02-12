package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.AuthenticationService;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

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
        existingUsers.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        existingUsers.setRank(users.getRank());
        existingUsers.setHub(users.getHub());
        existingUsers.setLicense_no(users.getLicense_no());
        existingUsers.setPhoto_profile(users.getPhoto_profile());
        existingUsers.setRole(users.getRole());
        existingUsers.setIs_active(users.getIs_active());
        existingUsers.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingUsers.setUpdated_by(getUserInfo());
        usersRepository.save(existingUsers);

        return existingUsers;
    }

    @Override
    public void deleteUsers(String id) {
        // check whetther a users exist in a DB
        usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", "Id", id));

        usersRepository.deleteById(id);
    }

    @Override
    public Users activationUsers(Users users, String id) {
        Users existingUsers = usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Users", "Id", id)
        );
        LocalDateTime timestamp = LocalDateTime.now();

        existingUsers.setIs_active((byte) 0);
        existingUsers.setUpdated_at(Timestamp.valueOf(timestamp));
        existingUsers.setUpdated_by(getUserInfo());
        usersRepository.save(existingUsers);
        return existingUsers;
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return usersRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
            }
        };
    }

    public String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();
        String userId = user.getId_users();
        return userId;
    }
}
