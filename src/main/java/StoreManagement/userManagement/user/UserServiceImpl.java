package StoreManagement.userManagement.user;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.userManagement.dto.*;
import StoreManagement.userManagement.role.Role;
import StoreManagement.userManagement.role.RoleService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final RoleService roleService;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse register(UserRegistrationReq userReq) {
        if (userUtils.isEmailTaken(userReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken");

        if (userUtils.isUsernameTaken(userReq.getUsername()))
            throw new ResourceAlreadyExistsException("Username is already taken");

        Role role = roleService.getRoleById(userReq.getRoleId());

        Users loggedInUser = currentlyLoggedInUser.getUser();
        Users user = userUtils.createUser(userReq, loggedInUser, role);
        Users savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse editUser(UserUpdateReq updateReq) {
        Users user = currentlyLoggedInUser.getUser();

        if (updateReq.getFullName() != null)
            user.setFullName(updateReq.getFullName());

        // Update email if provided and different from the current email
        if (updateReq.getEmail() != null && !user.getEmail().equalsIgnoreCase(updateReq.getEmail())) {
            // Check if the new email is already taken
            if (userUtils.isEmailTaken(updateReq.getEmail()))
                throw new ResourceAlreadyExistsException("Email is already taken");

            user.setEmail(updateReq.getEmail());
        }

        // Update username if provided and different from the current username
        if (updateReq.getUsername() != null && !user.getUsername().equals(updateReq.getUsername())) {
            // Check if the new username is already taken
            if (userUtils.isUsernameTaken(updateReq.getUsername()))
                throw new ResourceAlreadyExistsException("Username is already taken");

            user.setUsername(updateReq.getUsername());
        }
        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse editUserRoleAndStatus(Long userId, UserRoleAndStatusUpdateReq updateReq) {
        Users user = userUtils.getById(userId);

        if (updateReq.getStatus() != null)
            changeStatus(user, updateReq.getStatus());

        if (updateReq.getRoleId() != null)
            changeUserRole(user, updateReq.getRoleId());

        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse me() {
        Users user = currentlyLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STORE_MANAGER')")
    public List<UserResponse> getUsers() {
        List<Users> users = userRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return users.stream().map(UserMapper::toUserResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(Long userId) {
        userUtils.getById(userId);
        userRepository.deleteById(userId);

    }

    private void changeStatus(Users user, String status) {
        if (!("ACTIVE".equals(status) || "SUSPENDED".equals(status) || "BANNED".equals(status)))
            throw new BadRequestException("Invalid status. Status should be one of: ACTIVE, SUSPENDED, BANNED");

        user.setUserStatus(UserStatus.getEnum(status));
    }

    private void changeUserRole(Users user, Short roleId) {
        Role role = roleService.getRoleById(roleId);
        user.setRole(role);
    }

}
