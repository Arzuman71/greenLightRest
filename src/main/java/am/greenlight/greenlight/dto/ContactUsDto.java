package am.greenlight.greenlight.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactUsDto {
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String message;
}
