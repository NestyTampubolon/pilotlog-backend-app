package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.StatusUsers;
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

        Users usersc = new Users();
        usersc.setId_users(AuthenticationServiceImpl.getCompanyInfo().getId_company() + users.getId_no());
        usersc.setId_no(users.getId_no());
        usersc.setName(users.getName());
        usersc.setEmail(users.getEmail());
        usersc.setPassword(new BCryptPasswordEncoder().encode(users.getId_no()));
        usersc.setRank(users.getRank());
        usersc.setHub(users.getHub());
        usersc.setLicense_no(users.getLicense_no());
        usersc.setPhoto_profile(users.getPhoto_profile());
        usersc.setRole(users.getRole());
        usersc.setIs_active((byte) 1);
        if(!"ADMIN".equals(users.getRole())) {
            users.setLicense_no(users.getLicense_no());
            users.setStatus("NOT VALID");
        }
        usersc.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        usersc.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        usersc.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        usersc.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        usersc.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return usersRepository.save(usersc);
    }

    @Override
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @Override
    public List<Users> getAllInstructor() {
        return usersRepository.findInstructorUsers();
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
        existingUsers.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingUsers.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
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
        existingUsers.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
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
}
