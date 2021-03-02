package am.greenlight.greenlight.model;

import am.greenlight.greenlight.dto.UserChangeDto;
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

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate age;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column
    private String picUrl;

    @ManyToOne
    private Preference preference;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ARCHIVED;

    @Column
    private boolean phoneActive;

    @Column
    private String otp;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedDate;


    public void userChange(UserChangeDto userDto) {
        this.name = userDto.getName();
        this.surname = userDto.getSurname();
        this.gender = userDto.getGender();
        this.age = userDto.getAge();
    }
}
