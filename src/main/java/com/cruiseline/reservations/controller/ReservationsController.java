package com.cruiseline.reservations.controller;

import com.cruiseline.reservations.model.Reservation;
import com.cruiseline.reservations.repository.ReservationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@Api(value = "/", tags = "Reservations", description = "Operations about reservations")
public class ReservationsController {

    @Autowired
    ReservationRepository reservationRepository;


    @ApiOperation(
            value = "Find all venue records for a given page and size",
            notes = "Expensive operation as it retrieves all the records", response = Reservation.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Reservation Not Found")})
    @GetMapping(value = "/findAll")
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
