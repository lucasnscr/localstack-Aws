package org.lucasnscr.awslocalstack.repository;

import org.lucasnscr.awslocalstack.model.Car;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CarRepository  extends CrudRepository<Car, String> {

    List<Car> findByModel(String model);
    List<Car> findByColor(String color);
}
