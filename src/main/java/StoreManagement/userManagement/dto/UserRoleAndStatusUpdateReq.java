package StoreManagement.userManagement.dto;

import lombok.Data;

@Data
public class UserRoleAndStatusUpdateReq {

    private String status;

    private Short roleId;
}
