package antifraud.model.request;

import jakarta.validation.constraints.NotNull;

public record NewUserRequest(
        @NotNull String name,
        @NotNull String username,
        @NotNull String password) {
}
