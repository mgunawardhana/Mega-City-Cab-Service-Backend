package com.megacity.backend.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.megacity.backend.domain.enums.Role;
import jakarta.annotation.Nullable;
import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest {
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String nic;
    private String phone_number;
}
