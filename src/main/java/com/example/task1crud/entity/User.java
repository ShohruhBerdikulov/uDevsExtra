package com.example.task1crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String password;
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne
    private Rollar rollar;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp CreatedDate;



    private boolean accountNonExpired=true;
    private boolean AccountNonLocked=true;
    private boolean CredentialsNonExpired=true;
    private boolean enabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(rollar.getRoleType().name()));
        return grantedAuthorities;
    }

    public User(String name, String password, String username, String email, String phoneNumber, Rollar rollar) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rollar = rollar;
    }
}
