package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class CarServiceTest {


    @Mock
    private CarRepository carRepository;

    @Mock
    private Car car;
    @Mock
    private MockMultipartFile file;

    private CarService carService;

    public CarServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.carService = new CarService(carRepository);
    }

    @Test
    void save_Ok() {
        given(carRepository.save(car)).willReturn(car);
        Car carResponse = carService.save(car);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void save_With_File_Ok() {
        given(carRepository.save(car)).willReturn(car);
        Car carResponse = carService.save(car, file);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void getOne_Ok() {
        long id = 11;
        given(carRepository.getOne(id)).willReturn(car);
        Car carResponse = carService.getOne(id);
        assertThat(carResponse).isEqualTo(car);
    }

    @Test
    void getOne_Null() {
        long id = 11;
        given(carRepository.getOne(id)).willReturn(null);
        Car carResponse = carService.getOne(id);
        assertThat(carResponse).isNull();
    }

    @Test
    void findCarByUserId_Ok() {
        List<Car> carList = new ArrayList<>();
        given(carRepository.findCarByUserId(11)).willReturn(carList);
        List<Car> carResponse = carService.findCarByUserId(11);
        assertThat(carResponse).isEqualTo(carList);
    }

    @Test
    void findCarByUserId_Null() {
        given(carRepository.findCarByUserId(11)).willReturn(null);
        List<Car> carResponse = carService.findCarByUserId(11);
        assertThat(carResponse).isNull();
    }

    @Test
    void findCarByUserIdAndStatus_Ok() {
        List<Car> carList = new ArrayList<>();
        given(carRepository.findCarByUserIdAndStatus(11, Status.ACTIVE)).willReturn(carList);
        List<Car> carResponse = carService.findCarByUserIdAndStatus(11, Status.ACTIVE);
        assertThat(carResponse).isEqualTo(carList);
    }

    @Test
    void findCarByUserIdAndStatus_Null() {
        given(carRepository.findCarByUserIdAndStatus(11, Status.ACTIVE)).willReturn(null);
        List<Car> carResponse = carService.findCarByUserIdAndStatus(11, Status.ACTIVE);
        assertThat(carResponse).isNull();
    }
}