package tech.gomes.reading.management.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.indicator.RoleIndicator;
import tech.gomes.reading.management.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminGenerateConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${admin.password}")
    private String adminPassword;

    private static final String ROLE = "admin";

    @Override
    @Transactional
    public void run(String... args) {

        Optional<User> admin = userRepository.findByUsername(ROLE);

        admin.ifPresentOrElse(user -> log.info("Admin já cadastrado."), () -> {

            User newAdmin = new User();

            newAdmin.setEmail(ROLE);
            newAdmin.setUsername(ROLE);
            newAdmin.setPassword(bCryptPasswordEncoder.encode(adminPassword));
            newAdmin.setRole(RoleIndicator.ADMIN);

            userRepository.save(newAdmin);
        });
    }
}
