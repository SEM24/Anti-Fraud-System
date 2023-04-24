package antifraud.model.enitity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Column(updatable = false)
    private String username;
    @NotEmpty
    private String password;

    @Column
    @NotEmpty
    @JsonIgnore
    private String role;
    /**
     * True if locked, false if unlocked
     */
    @JsonIgnore
    private boolean isAccountLocked;

    /**
     * Returns the plain user role because web security requires a prefix while other methods don't
     *
     * @return The user's role without the "ROLE_" prefix.
     */
    public String getRoleWithoutPrefix() {
        return this.role.substring(5);
    }
}
