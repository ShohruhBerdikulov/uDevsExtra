package com.example.task1crud.component;

import com.example.task1crud.entity.RoleType;
import com.example.task1crud.entity.Rollar;
import com.example.task1crud.entity.User;
import com.example.task1crud.repository.RoleRepository;
import com.example.task1crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${spring.datasource.initialization-mode}")
    private String modeType;
//b yerlar ishlayotgan edi. security qoshgandan kn
    @Override
    public void run(String... args) throws Exception {
        if (modeType.equals("always")) {
            Rollar admin = roleRepository.save(new Rollar(1, Constans.admin, RoleType.ROLE_ADMIN));
            Rollar user = roleRepository.save(new Rollar(2, Constans.user, RoleType.ROLE_USER));
            userRepository.save(new User
                    ("Shohruh", passwordEncoder.encode("5555"), "9999", "aaaa@gmail.com", "+6666", admin));
            userRepository.save(new User
                    ("Shohruh", passwordEncoder.encode("2222"), "2222", "ddddd@gmail.com", "+ffff", user));
        }
    }
}
