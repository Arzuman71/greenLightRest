package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForUser.Gender;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue()
    private long id;
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

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;
    private LocalDate createdDate;
    @Enumerated(value = EnumType.STRING)
    private State state = State.ACTIVE;
    private Boolean active;
    private String token;


}
