package com.pilotlog.pilottrainingmanagement;

import com.pilotlog.pilottrainingmanagement.model.Rank;
import com.pilotlog.pilottrainingmanagement.model.Role;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;

@SpringBootApplication
public class PilottrainingmanagementApplication implements CommandLineRunner {

	@Autowired
	private UsersRepository usersRepository;

	public static void main(String[] args) {
		SpringApplication.run(PilottrainingmanagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Users adminAccount = usersRepository.findByRole(Role.ADMIN);
		if(null ==  adminAccount){
			Users users = new Users();

			users.setId_users("ASDAFA");
			users.setId_no("12345");
			users.setName("Admin");
			users.setEmail("admin123@gmail.com");
			users.setPassword(new BCryptPasswordEncoder().encode("admin"));
			users.setRank(Rank.CAPT);
			users.setHub("CGK");
			users.setLicense_no(" ");
			users.setPhoto_profile("");
			users.setRole(Role.ADMIN);
			users.setIs_active((byte) 1);
			users.setCreated_at(Timestamp.valueOf("2024-02-05 14:03:44.067"));
			users.setUpdated_at(Timestamp.valueOf("2024-02-05 14:03:44.067"));
			users.setCreated_by("sda");
			users.setUpdated_by("sda");

			usersRepository.save(users);
		}
	}
}
