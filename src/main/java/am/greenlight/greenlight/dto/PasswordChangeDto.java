package am.greenlight.greenlight.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeDto {

    @Size(min = 6, max = 25, message = "Password length sold be at least 6 symbol")
    private String oldPassword;
    @Size(min = 6, max = 25, message= "Password length sold be at least 6 symbol")
    private String password;
}
