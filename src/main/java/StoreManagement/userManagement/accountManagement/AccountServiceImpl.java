package StoreManagement.userManagement.accountManagement;

import StoreManagement.userManagement.dto.ChangePassword;
import StoreManagement.userManagement.user.UserRepository;
import StoreManagement.userManagement.user.UserUtils;
import StoreManagement.userManagement.user.Users;
import StoreManagement.utils.ApiResponse;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final PasswordEncoder passwordEncoder;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword changePassword) {
        Users user = currentlyLoggedInUser.getUser();
        userUtils.validateOldPassword(user, changePassword.getOldPassword());
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
    }

}
