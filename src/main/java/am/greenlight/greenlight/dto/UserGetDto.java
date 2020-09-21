package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.enumForUser.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetDto {
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate age;
    private String password;
    private int phoneNumber;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String picUrl;
    private String aboutMe;
    @ManyToOne
    private Preference preference;
    private LocalDate createdDate;

}
