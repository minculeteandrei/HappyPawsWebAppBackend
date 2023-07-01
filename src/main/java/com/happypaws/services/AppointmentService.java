package com.happypaws.services;

import com.happypaws.domain.Appointment;

import java.util.List;
import java.util.Set;

public interface AppointmentService {
    Set<Integer> findAvailableHoursForDate(int year, int month, int day);
    List<Appointment> findAll();
    Appointment save(Appointment appointment);
    Appointment findById(Long id);
    void deleteById(Long id);
}
