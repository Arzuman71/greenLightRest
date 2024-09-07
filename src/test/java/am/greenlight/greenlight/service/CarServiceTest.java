package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.CarRepo;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class CarServiceTest {

    @Mock
    private CarRepo carRepo;
    @Mock
    private Car car;
    @Mock
    private MockMultipartFile file;
    @InjectMocks
    private CarService carService;

    public CarServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_Ok() {
        given(carRepo.save(car)).willReturn(car);
        Car carResponse = carService.save(car);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void save_With_File_Ok() {
        given(carRepo.save(car)).willReturn(car);
        Car carResponse = carService.save(car, file);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void getOne_Ok() {
       long id = 11;
        given(carRepo.getOne(id)).willReturn(car);
        Car carResponse = carService.getOne(id);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void getOne_Null() {
        long id = 11;
        given(carRepo.getOne(id)).willReturn(null);
        Car carResponse = carService.getOne(id);
        assertThat(carResponse).isNull();
    }

    @Test
    void findCarByUserId_Ok() {
        given(carRepo.findCarByUserId(11)).willReturn(ImmutableList.of());
        List<Car> carResponse = carService.findCarByUserId(11);
        assertThat(carResponse).isNotNull();
    }

    @Test
    void findCarByUserId_Null() {
        given(carRepo.findCarByUserId(11)).willReturn(null);
        List<Car> carResponse = carService.findCarByUserId(11);
        assertThat(carResponse).isNull();
    }

    @Test
    void findCarByUserIdAndStatus_Ok() {
        given(carRepo.findCarByUserIdAndStatus(11, Status.ACTIVE)).willReturn(ImmutableList.of());
        List<Car> carResponse = carService.findCarByUserIdAndStatus(11, Status.ACTIVE);
        assertThat(carResponse).isNotNull();
    }

    @Test
    void findCarByUserIdAndStatus_Null() {
        given(carRepo.findCarByUserIdAndStatus(11, Status.ACTIVE)).willReturn(null);
        List<Car> carResponse = carService.findCarByUserIdAndStatus(11, Status.ACTIVE);
        assertThat(carResponse).isNull();
    }
}