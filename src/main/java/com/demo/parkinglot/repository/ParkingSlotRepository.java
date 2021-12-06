package com.demo.parkinglot.repository;

import com.demo.parkinglot.entity.Slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends CrudRepository<Slot, Integer> {

    List<Slot> findByIsAllocated(boolean allocated);
}
