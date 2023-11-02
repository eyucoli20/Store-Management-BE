package StoreManagement.userManagement.accountManagement;

import StoreManagement.userManagement.dto.ChangePassword;
import StoreManagement.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    ResponseEntity<ApiResponse> changePassword(ChangePassword changePassword);

}
