package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Role;
import com.pilotlog.pilottrainingmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value= "SELECT * FROM Users  WHERE id_company = :companyId", nativeQuery = true)
    List<Users> findAllByCompanyId(String companyId);

    @Query(value= "SELECT * FROM Users  WHERE id_company = :companyId AND is_active = 1 AND role != 'ADMIN'", nativeQuery = true)
    List<Users> findAllPilotByCompanyId(String companyId);



    Optional<Users> findByEmail(String email);
    Users findByRole(Role role);

    List<Users> findByRoleIn(List<Role> roles);

    default List<Users> findInstructorUsers() {
        List<Role> instructorRoles = Arrays.asList(
                Role.INSTRUCTOR,
                Role.TRAINEE_INSTRUCTOR,
                Role.INSTRUCTOR_CPTS,
                Role.TRAINEE_INSTRUCTOR_CPTS
        );
        return findByRoleIn(instructorRoles);
    }

    default List<Users> findCPTSUsers() {
        List<Role> cptsRoles = Arrays.asList(
                Role.CPTS,
                Role.TRAINEE_CPTS,
                Role.INSTRUCTOR_CPTS,
                Role.TRAINEE_INSTRUCTOR_CPTS
        );
        return findByRoleIn(cptsRoles);
    }

}
