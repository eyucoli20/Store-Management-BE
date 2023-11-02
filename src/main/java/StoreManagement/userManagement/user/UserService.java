package StoreManagement.userManagement.user;

import StoreManagement.userManagement.dto.UserRegistrationReq;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.dto.UserRoleAndStatusUpdateReq;
import StoreManagement.userManagement.dto.UserUpdateReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse register(UserRegistrationReq userReq);

    UserResponse me();

    List<UserResponse> getUsers();

    UserResponse editUser(UserUpdateReq updateReq);

    UserResponse editUserRoleAndStatus(Long userId, UserRoleAndStatusUpdateReq updateReq);
    void deleteUser(Long userId);
}
