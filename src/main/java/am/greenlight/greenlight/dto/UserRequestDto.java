package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.enumForUser.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @Size(min = 6, message = "Password length sold be at least 6 symbol")
    private String password;
    private String confirmPassword;
    @NotBlank(message = "Email is  required")
    @Email(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$", message = "the given email cannot exist")
    private String email;
    private Gender gender;
    private String picUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Parameter Date Unadjusted can not be blank or null")
    private LocalDate age;
    // @Min(value = 8, message = "phone number should not be less than 8")
    //   @Max(value = 15, message = "phone number not be greater than 15")
     private int phoneNumber;
    private String aboutMe;

    private Preference preference;

}