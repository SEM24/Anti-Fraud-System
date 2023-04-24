package antifraud.service.impl;

import antifraud.antifraudMapper.AntiFraudMappers;
import antifraud.model.dto.UserDTO;
import antifraud.model.enitity.UserEntity;
import antifraud.model.enums.UserRoles;
import antifraud.model.request.ChangeUserAccessRequest;
import antifraud.model.request.ChangeUserRoleRequest;
import antifraud.model.request.NewUserRequest;
import antifraud.model.response.DeletedUserResponse;
import antifraud.repository.UserEntityRepository;
import antifraud.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserEntityRepository userRepo;
    PasswordEncoder passwordEncoder;
    AntiFraudMappers mapper;

    @Transactional
    @Override
    public UserDTO registerUser(NewUserRequest user) {
        if (userRepo.existsByUsernameIgnoreCase(user.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        String hashedPassword = passwordEncoder.encode(user.password());
        UserEntity userEntity = UserEntity.builder()
                .name(user.name())
                .username(user.username())
                .password(hashedPassword)
                .role(setUserRole())
                .isAccountLocked(setAccountLocked())
                .build();
        userEntity = userRepo.save(userEntity);

        return mapper.toUserProfile(userEntity);
    }

    @Override
    public List<UserDTO> showAllUsers() {
        return userRepo.findAll().stream().map(mapper::toUserProfile).toList();
    }

    @Transactional
    @Override
    public DeletedUserResponse deleteUser(String username) {
        UserEntity user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepo.delete(user);
        return new DeletedUserResponse(username, "Deleted successfully!");
    }

    //TODO TestMethod
    @Override
    public UserDTO updateUserRole(ChangeUserRoleRequest userRole) {
        UserEntity user = userRepo.findByUsernameIgnoreCase(userRole.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String role = userRole.role();
        List<String> roles = Stream.of(UserRoles.values()).map(Enum::name).toList();

        if (!roles.contains(role) || role.equals("ADMINISTRATOR"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (user.getRoleWithoutPrefix().equals(role))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        user.setRole("ROLE_" + role);
        userRepo.save(user);

        return mapper.toUserProfile(user);
    }

    @Override
    public Map<String, String> updateUserAccess(ChangeUserAccessRequest changeUserAccessRequest) {
        String username = changeUserAccessRequest.username();
        String operation = changeUserAccessRequest.operation().toLowerCase();

        UserEntity user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getRole().equals(UserRoles.ADMINISTRATOR.toString()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        userRepo.save(changeLockUser(operation, user));
        return Map.of("status", String.format(
                "User %s %sed!", user.getUsername(), operation));
    }
    //FIXME проверить этот метод
    private UserEntity changeLockUser(String operation, UserEntity user) {
        if (operation.equals("lock")) user.setAccountLocked(true);
        else if (operation.equals("unlock")) user.setAccountLocked(false);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return user;
    }

    /**
     * @return false if the user isn't exist in db. In this case he's administrator
     */
    private boolean setAccountLocked() {
        return !userRepo.findAll().isEmpty();
    }

    private String setUserRole() {
        return userRepo.findAll().isEmpty()
                ? UserRoles.ADMINISTRATOR.toString() : UserRoles.MERCHANT.toString();
    }
}
