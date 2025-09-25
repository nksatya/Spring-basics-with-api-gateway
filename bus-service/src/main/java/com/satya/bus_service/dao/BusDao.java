package com.satya.bus_service.dao;

import com.satya.bus_service.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusDao extends JpaRepository<Bus,Integer> {

}
