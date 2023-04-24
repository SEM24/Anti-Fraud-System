package antifraud.service;

import antifraud.model.dto.UserDTO;
import antifraud.model.request.ChangeUserAccessRequest;
import antifraud.model.request.ChangeUserRoleRequest;
import antifraud.model.request.NewUserRequest;
import antifraud.model.response.DeletedUserResponse;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDTO registerUser(NewUserRequest user);

    //    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SUPPORT')")
//    @Secured({"ROLE_ADMINISTRATOR", "ROLE_SUPPORT"})
    List<UserDTO> showAllUsers();

    //    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    @Secured({"ROLE_ADMINISTRATOR"})
    DeletedUserResponse deleteUser(String username);

    //    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    @Secured({"ROLE_ADMINISTRATOR"})
    UserDTO updateUserRole(ChangeUserRoleRequest changeUserRoleRequest);

    //    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    @Secured({"ROLE_ADMINISTRATOR"})
    Map<String, String> updateUserAccess(ChangeUserAccessRequest changeUserAccessRequest);
}
