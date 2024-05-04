package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.dto.MailBody;
import com.pilotlog.pilottrainingmanagement.model.ForgotPassword;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.ForgotPasswordRepository;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.EmailService;
import com.pilotlog.pilottrainingmanagement.utils.ChangePassword;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    //send mail for email verification
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable("email") String email){
        System.out.println("check");
        Users users = usersRepository.findByEmail(email).orElse(null);
        if (users == null) {
            return new ResponseEntity<>("Email is not registered", HttpStatus.NOT_FOUND);
        }

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request : " + otp)
                .subject("OTP for Forgot Password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 3 * 60 * 1000))
                .users(users)
                .build();

        System.out.println(fp);

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return  ResponseEntity.ok("Email sent for verification");
    }


    @PostMapping("/verifyOtp/{otp}/{email}")
    public  ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){
        Users users = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUsers(otp, users)
                .orElse(null);

        if (fp == null) {
            return new ResponseEntity<>("OTP does not match", HttpStatus.NOT_FOUND);
        }

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return  ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email){
        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        System.out.println(changePassword.password());
        String encodedPassword = new BCryptPasswordEncoder().encode(changePassword.password());
        usersRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed!");

    };

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
