package StoreManagement.userManagement.user;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.userManagement.dto.UserRegistrationReq;
import StoreManagement.userManagement.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users createUser(UserRegistrationReq userReq, Users loggedInUser, Role role) {
        return Users.builder()
                .username(userReq.getUsername())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .fullName(userReq.getFullName())
                .email(userReq.getEmail())
                .role(role)
                .userStatus(UserStatus.ACTIVE)
                .registeredBy(loggedInUser)
                .build();
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void validateOldPassword(Users user, String oldPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect old Password!");
        }
    }

    public Users getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
