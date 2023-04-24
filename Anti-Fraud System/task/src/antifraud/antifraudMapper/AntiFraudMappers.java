package antifraud.antifraudMapper;

import antifraud.model.dto.UserDTO;
import antifraud.model.enitity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface AntiFraudMappers {
    default UserDTO toUserProfile(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.getRoleWithoutPrefix()
        );
    }


    default UserDetails toUserDetails(UserEntity userEntity) {
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole())
                .accountLocked(userEntity.isAccountLocked())
                .build();
    }
}

