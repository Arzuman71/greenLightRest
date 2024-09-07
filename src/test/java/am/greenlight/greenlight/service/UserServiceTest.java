package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.UserRepo;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private PreferenceService prefService;
    @Mock
    private  EmailService emailService;
    @Mock
    private User user;
    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_Ok() {
        given(userRepo.save(user)).willReturn(user);
        User userResponse = userService.save(user);
        assertThat(userResponse).isEqualTo(user);
    }

    @Test
    void save_NullPointerException() {
        given(userRepo.save(user)).willThrow(NullPointerException.class);

        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            userService.save(user);
        });
        assertThat(thrown).isInstanceOf(NullPointerException.class);

    }

    @Test
    void findByEmail_Ok() {
        given(userRepo.findByEmail("poxos@mail.ru")).willReturn(Optional.of(new User()));
       User userResponse = userService.findByEmail("poxos@mail.ru");
        assertThat(userResponse).isNotNull();
    }

    @Test
    void findByEmail_Null() {
        given(userRepo.findByEmail("poxos@mail.ru")).willReturn(null);
        User userResponse = userService.findByEmail("poxos@mail.ru");
        assertThat(userResponse).isNull();
    }


    @Test
    void findByNameAndSurname_Ok() {
        given(userRepo.findByNameAndSurname("poxos", "poxosyan")).willReturn(ImmutableList.of());
        List<User> userList2 = userService.findByNameAndSurname("poxos", "poxosyan");
        assertThat(userList2).isNotNull();
    }

    @Test
    void findByNameAndSurname_Null() {
        given(userRepo.findByNameAndSurname("poxos", "poxosyan")).willReturn(null);
        List<User> userList2 = userService.findByNameAndSurname("poxos", "poxosyan");
        assertThat(userList2).isNull();
    }

    @Test
    void getOne_Ok() {
        long id = 11;
        given(userRepo.getOne(id)).willReturn(user);
        User userResponse = userService.getOne(id);
        assertThat(userResponse).isEqualTo(user);
    }

    @Test
    void getOne_Null() {
        long id = 11;
        given(userRepo.getOne(id)).willReturn(null);
        User userResponse = userService.getOne(id);
        assertThat(userResponse).isNull();
    }
}