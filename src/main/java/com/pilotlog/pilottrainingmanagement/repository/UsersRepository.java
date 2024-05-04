package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Role;
import com.pilotlog.pilottrainingmanagement.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value= "SELECT * FROM Users  WHERE id_company = :companyId", nativeQuery = true)
    List<Users> findAllByCompanyId(String companyId);

    @Query(value= "SELECT * FROM Users  WHERE id_company = :companyId AND is_active = 1 AND role != 'ADMIN'", nativeQuery = true)
    List<Users> findAllPilotByCompanyId(String companyId);

    @Query(value = "SELECT " +
            "SUM(CASE WHEN u.status = 'VALID' THEN 1 ELSE 0 END) AS validPilotsCount, " +
            "COUNT(u.id_users) AS allPilotsCount " +
            "FROM Users u " +
            "WHERE u.id_company = :companyId AND u.is_active = 1 AND u.role != 'ADMIN'", nativeQuery = true)
    Map<String, BigInteger> findAllPilotsCountsByCompanyId(String companyId);



    Optional<Users> findByEmail(String email);
    Users findByRole(Role role);


    @Query(value="SELECT * FROM Users WHERE role IN ('INSTRUCTOR', 'TRAINEE_INSTRUCTOR', 'INSTRUCTOR_CPTS', 'TRAINEE_INSTRUCTOR_CPTS') AND id_company = :companyId", nativeQuery = true)
    List<Users> findInstructorUsers(String companyId);

    default List<Users> findInstructorUsers() {
        List<Role> instructorRoles = Arrays.asList(
                Role.INSTRUCTOR,
                Role.TRAINEE_INSTRUCTOR,
                Role.INSTRUCTOR_CPTS,
                Role.TRAINEE_INSTRUCTOR_CPTS
        );
        // Menggunakan metode findInstructorUsers dengan parameter companyId null
        return findInstructorUsers(null);
    }

    List<Users> findByRoleIn(List<Role> roles);

    default List<Users> findCPTSUsers() {
        List<Role> cptsRoles = Arrays.asList(
                Role.CPTS,
                Role.TRAINEE_CPTS,
                Role.INSTRUCTOR_CPTS,
                Role.TRAINEE_INSTRUCTOR_CPTS
        );
        return findByRoleIn(cptsRoles);
    }

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.password = ?2 WHERE u.email = ?1")
    void updatePassword(String email, String password );


}
