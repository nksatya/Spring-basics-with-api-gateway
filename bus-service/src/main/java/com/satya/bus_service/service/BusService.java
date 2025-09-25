package com.satya.bus_service.service;

import com.satya.bus_service.dao.BusDao;
import com.satya.bus_service.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusService {
    @Autowired
    BusDao busDao;

    public ResponseEntity<Optional<Bus>> getBusById(Integer busId){
        try{
            return new ResponseEntity<>(busDao.findById(busId), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createNewBus(Bus bus){
        try{
            busDao.save(bus);
            return new ResponseEntity<>("Bus Created!", HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
