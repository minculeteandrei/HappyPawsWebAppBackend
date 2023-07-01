package com.happypaws.services;

import com.happypaws.domain.Appointment;
import com.happypaws.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImp implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImp(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Set<Integer> findAvailableHoursForDate(int year, int month, int day) {
        List<Appointment> appointments = this.appointmentRepository.findAppointmentsByDate(year, month, day);
        HashSet<Integer> availableHours = new HashSet<>();
        for (int i = 9; i <= 18; i++) {
            availableHours.add(i);
        }

        for (Appointment appointment : appointments) {
            availableHours.remove(appointment.getTime());
        }
        return availableHours;
    }

    @Override
    public List<Appointment> findAll() {
        return this.appointmentRepository.findAll();
    }

    @Override
    public Appointment save(Appointment appointment) {
        return this.appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long id) {
        return this.appointmentRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        this.appointmentRepository.deleteById(id);
    }
}
