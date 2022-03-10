package org.lucasnscr.awslocalstack.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.lucasnscr.awslocalstack.model.Car;
import org.lucasnscr.awslocalstack.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CarService {

    private AmazonS3 amazonS3;
    private CarRepository carRepository;

    @Autowired
    public CarService(AmazonS3 amazonS3, CarRepository carRepository){
        this.amazonS3 = amazonS3;
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car){
        return carRepository.save(car);
    }

    public List<Car> findAll(){
        return StreamSupport
                .stream(carRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Car> findByColor(String color){
        return carRepository.findByColor(color);
    }

    public List<Car> findByModel(String model){
        return carRepository.findByModel(model);
    }

    public List<String> getAllDocumentsFromBuckets(String bucketName){
        if (!amazonS3.doesBucketExistV2(bucketName)){
            log.info("Bucket name is not available, try again with different bucket name");
            throw new NoSuchElementException("Bucket name is not available");
        }

        return amazonS3.listObjectsV2(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());

    }

    public void createBucket(String bucketName){
        amazonS3.createBucket(bucketName);
    }

    public void uploadDocument(MultipartFile file, String bucketName) throws IOException{
        String tempFileName = UUID.randomUUID() + file.getName();
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + tempFileName);
        file.transferTo(tempFile);
        amazonS3.putObject(bucketName, UUID.randomUUID() + file.getName(), tempFile);
        tempFile.deleteOnExit();
    }



}
