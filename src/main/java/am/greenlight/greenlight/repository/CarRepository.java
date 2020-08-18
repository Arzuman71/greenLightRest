package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarByUserId(long id);

    List<Car> findCarByUserIdAndState(long id, State state);

}
