package StoreManagement;

import StoreManagement.userManagement.role.Role;
import StoreManagement.userManagement.role.RoleRepository;
import StoreManagement.userManagement.user.UserRepository;
import StoreManagement.userManagement.user.UserStatus;
import StoreManagement.userManagement.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Initializes the database with preloaded data upon application startup.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                // Create and save roles
                List<Role> roles = createUserRole();
                roles = roleRepository.saveAll(roles);

                // Create and save user
                Users johnDoe = createUser(roles.get(0));
                userRepository.save(johnDoe);

                log.info("ApplicationRunner => Preloaded roles and admin user");
            } catch (Exception ex) {
                log.error("ApplicationRunner Preloading Error: {}", ex.getMessage());
                throw new RuntimeException("ApplicationRunner Preloading Error ", ex);
            }
        };
    }

    private List<Role> createUserRole() {
        Role admin = new Role("ADMIN", "Manages all aspects of the application.");
        Role storeManager = new Role("STORE_MANAGER", "Responsible for store operations and inventory management.");
        Role storeStaff = new Role("STORE_STAFF", "Assists in inventory tracking and sales operations.");

        return List.of(admin, storeManager, storeStaff);
    }

    private Users createUser(Role role) {
        return Users.builder()
                .username("john")
                .password(passwordEncoder.encode("123456"))
                .fullName("John Doe")
                .email("johndoes@gmail.com")
                .role(role)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }
}