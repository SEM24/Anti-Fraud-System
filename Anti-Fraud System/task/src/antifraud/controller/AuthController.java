package antifraud.controller;

import antifraud.antifraudMapper.AntiFraudMappers;
import antifraud.model.dto.UserDTO;
import antifraud.model.request.ChangeUserAccessRequest;
import antifraud.model.request.ChangeUserRoleRequest;
import antifraud.model.request.NewUserRequest;
import antifraud.model.response.DeletedUserResponse;
import antifraud.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    UserService userService;
    AntiFraudMappers mapper;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> showAllUsers() {
        return userService.showAllUsers();
    }

    @PostMapping("/user")
    ResponseEntity<UserDTO> registerUser(@Valid @RequestBody NewUserRequest user) {
        log.info("user={}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

    @DeleteMapping("/user/{username}")
    ResponseEntity<DeletedUserResponse> deleteUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(username));
    }

    @PutMapping("/role")
    ResponseEntity<UserDTO> changeUserRole(@RequestBody ChangeUserRoleRequest changeUserRoleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserRole(changeUserRoleRequest));
    }

    @PutMapping("/access")
    ResponseEntity<Map<String, String>> changeUserAccess(@RequestBody ChangeUserAccessRequest changeUserAccessRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserAccess(changeUserAccessRequest));
    }
}
