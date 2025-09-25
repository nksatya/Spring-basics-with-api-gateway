package com.satya.bus_service.controller;

import com.satya.bus_service.model.Bus;
import com.satya.bus_service.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("bus")
public class BusController {

    @Autowired
    BusService busService;

    @GetMapping("get/{busId}")
    public ResponseEntity<Optional<Bus>> getBus(@PathVariable Integer busId){
        return busService.getBusById(busId);
    }

    @PostMapping("create")
    public ResponseEntity<String> createBus(@RequestBody Bus bus){
        return busService.createNewBus(bus);
    }
}
