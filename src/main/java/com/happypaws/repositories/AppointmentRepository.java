package com.happypaws.repositories;

import com.happypaws.domain.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Month;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    @Query("select a from appointment a where year(a.date) = ?1 and month(a.date) = ?2 and day(a.date) = ?3")
    List<Appointment> findAppointmentsByDate(int year, int month, int dayOfMonth);
    List<Appointment> findAll();
}
