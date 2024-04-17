package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
        return usersRepository.findAllByCompanyId(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public List<Users> getAllInstructor() {
        return usersRepository.findInstructorUsers();
    }

    @Override
    public List<Users> getAllCPTS() {
        return usersRepository.findCPTSUsers();
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

        if(users.getId_no() != null){
            existingUsers.setId_no(users.getId_no());
        }
        if(users.getName() != null){
            existingUsers.setName(users.getName());
        }
        if(users.getEmail() != null){
            existingUsers.setEmail(users.getEmail());
        }
        if(users.getRank() != null){
            existingUsers.setRank(users.getRank());
        }
        if(users.getHub() != null){
            existingUsers.setHub(users.getHub());
        }
        if(users.getLicense_no() != null){
            existingUsers.setLicense_no(users.getLicense_no());
        }
        if(users.getPhoto_profile() != null){
            existingUsers.setPhoto_profile(users.getPhoto_profile());
        }
        if(users.getRole() != null){
            existingUsers.setRole(users.getRole());
        }

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

    @Override
    public Users changePassword(Users users, String id) {
            Users existingUsers = usersRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Users", "Id", id)
            );

            existingUsers.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
            existingUsers.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            existingUsers.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
            usersRepository.save(existingUsers);
            return existingUsers;
    }

    @Value("${profile.directory}")
    private String profileDirectory;

    @Override
    public Users updatePhotoProfile(MultipartFile profile, String id) {
        Users existingUsers = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "Id", id));

        if (profile != null && !profile.isEmpty()) {
            try {
                System.out.println(profile);
                String profileFilename = saveProfile(profile);
                existingUsers.setPhoto_profile(profileFilename);
                return usersRepository.save(existingUsers);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile file.", e);
            }
        } else {
            throw new IllegalArgumentException("Profile is null or empty.");
        }
    }


    private String saveProfile(MultipartFile file) throws IOException {
        // Generate unique filename
        String filename = UUID.randomUUID().toString() + ".png";
        // Simpan file ke folder assets
        Files.copy(file.getInputStream(), Paths.get(profileDirectory + filename), StandardCopyOption.REPLACE_EXISTING);
        // Kembalikan alamat file gambar
        return filename;
    }

    public ResponseEntity<byte[]> loadProfile(String filename) {
        try {
            // Baca file gambar dari sistem file
            Path file = Paths.get(profileDirectory + filename);
            byte[] data = Files.readAllBytes(file);

            // Tentukan tipe konten sesuai dengan ekstensi file gambar
            String contentType = Files.probeContentType(file);

            // Buat header HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(data.length);

            // Kirim gambar sebagai respons HTTP
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Tangani kesalahan jika gagal membaca file
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
