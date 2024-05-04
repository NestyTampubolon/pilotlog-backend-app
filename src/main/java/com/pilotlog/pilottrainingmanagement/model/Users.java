package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_users;

    public Users() {
    }
    public Users(String id) {
        this.id_users = id;
    }


    @Column(name = "id_no", nullable = false)
    private String id_no;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rank")
    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Column(name = "hub")
    private String hub;

    @Column(name = "license_no")
    private String license_no;

    @Column(name = "photo_profile")
    private String photo_profile;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active", nullable = false)
    private byte is_active;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "updated_by", nullable = false)
    private String updated_by;

    @ManyToOne
    @JoinColumn(name="id_company")
    private Company id_company;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<ForgotPassword> forgotPasswords;

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


