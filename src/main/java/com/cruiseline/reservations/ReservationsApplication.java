package com.cruiseline.reservations;

import com.cruiseline.reservations.model.Reservation;
import com.cruiseline.reservations.repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableSwagger2
public class ReservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ReservationRepository reservationRepository) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Reservation>> typeReference = new TypeReference<List<Reservation>>(){};
            //JsonFactory f = new JsonFactory();
            //JsonParser jp = f.createJsonParser(new File("people.json"));
            InputStream inputStream = TypeReference.class.getResourceAsStream("/people.json");
            try {
                List<Reservation> people = Arrays.asList(mapper.readValue(inputStream,Reservation[].class));
                if(Objects.nonNull(people)){
                    people.forEach(reservation -> {
                        System.out.println(reservation);
                        reservationRepository.save(reservation);
                    });
                    long recordsCount = reservationRepository.count();
                    System.out.println("Total records inserted : " + recordsCount);
                }

                reservationRepository.saveAll(people);
                System.out.println("People Saved!");
            } catch (IOException e){
                System.out.println("Unable to save people: " + e.getMessage());
            }
        };
    }
}
