package antifraud.model.dto;

import lombok.Builder;

@Builder
public record UserDTO(
      Long id,  String name, String username, String role) {
}
