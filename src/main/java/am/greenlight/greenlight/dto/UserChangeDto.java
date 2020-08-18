package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.enumForUser.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangeDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @Size(min = 6, message = "Password length sold be at least 6 symbol")
    private String password;
    private String confirmPassword;

    private Gender gender;
    private String picUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Parameter Date Unadjusted can not be blank or null")
    private LocalDate age;
    @Size(min = 8, max = 15, message = "phone number is required")
    private int phoneNumber;
    private String aboutMe;

    private Preference preference;

}