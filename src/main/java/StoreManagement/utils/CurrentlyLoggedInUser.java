package StoreManagement.utils;

import StoreManagement.exceptions.customExceptions.ForbiddenException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.exceptions.customExceptions.UnauthorizedException;
import StoreManagement.userManagement.user.UserRepository;
import StoreManagement.userManagement.user.UserStatus;
import StoreManagement.userManagement.user.Users;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentlyLoggedInUser {

    private final UserRepository userRepository;

    public CurrentlyLoggedInUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new UnauthorizedException("Access denied. Please provide a valid authentication token.");

        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Please login again and try."));
        // If a user changes his or her email address, he or she must log in again.

        if (user.getUserStatus() != UserStatus.ACTIVE)
            throw new ForbiddenException("Access Denied: Your account is currently inactive.");

        return user;
    }
}