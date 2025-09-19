package tech.gomes.reading.management.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.indicator.RoleIndicator;
import tech.gomes.reading.management.repository.UserRepository;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AdminGenerateConfig implements CommandLineRunner {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Optional<User> admin = userRepository.findByUsername("admin");

        admin.ifPresentOrElse((user) -> System.out.println("Admin já cadastrado."), () -> {

            User newAdmin = new User();

            newAdmin.setUsername("admin");
            newAdmin.setPassword(bCryptPasswordEncoder.encode(adminPassword));
            newAdmin.setRole(RoleIndicator.ADMIN);

            userRepository.save(newAdmin);
        });
    }
}
