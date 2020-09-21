package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PreferenceService prefService;
    @Mock
    private User user;

    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, prefService);
    }

    @Test
    void save_Ok() {
        given(userRepository.save(user)).willReturn(user);
        User userResponse = userService.save(user);
        assertThat(userResponse).isEqualTo(user);
    }

    @Test
    void save_NullPointerException() {
        given(userRepository.save(any(User.class))).willThrow(NullPointerException.class);

        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            userService.save(any(User.class));
        });
        assertThat(thrown).isInstanceOf(NullPointerException.class);

    }

    @Test
    void findByEmail_Ok() {
        given(userRepository.findByEmail("poxos@mail.ru"));
        Optional<User> userResponse = userService.findByEmail("poxos@mail.ru");
        assertThat(userResponse).isNotNull();
    }

    @Test
    void findByEmail_Null() {
        given(userRepository.findByEmail("poxos@mail.ru")).willReturn(null);
        Optional<User> userResponse = userService.findByEmail("poxos@mail.ru");
        assertThat(userResponse).isNull();
    }


    @Test
    void findByNameAndSurname_Ok() {
        List<User> userList = new ArrayList<>();
        given(userRepository.findByNameAndSurname("poxos", "poxosyan")).willReturn(userList);
        List<User> userList2 = userService.findByNameAndSurname("poxos", "poxosyan");
        assertThat(userList2).isEqualTo(userList);
    }

    @Test
    void findByNameAndSurname_Null() {
        given(userRepository.findByNameAndSurname("poxos", "poxosyan")).willReturn(null);
        List<User> userList2 = userService.findByNameAndSurname("poxos", "poxosyan");
        assertThat(userList2).isNull();
    }

    @Test
    void getOne_Ok() {
        long id = 11;
        given(userRepository.getOne(id)).willReturn(user);
        User userResponse = userService.getOne(id);
        assertThat(userResponse).isEqualTo(user);
    }

    @Test
    void getOne_Null() {
        long id = 11;
        given(userRepository.getOne(id)).willReturn(null);
        User userResponse = userService.getOne(id);
        assertThat(userResponse).isNull();
    }
}