package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForUser.Gender;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
    private String phoneNumber;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String picUrl;
    private String about;
    @ManyToOne
    private Preference preference;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ARCHIVED;
    private boolean phoneActive;
    private String otp;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedDate;


    public void userChange(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.about = user.getAbout();
    }
}
