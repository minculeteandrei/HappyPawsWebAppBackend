package com.happypaws.restcontrollers;

import com.happypaws.domain.Appointment;
import com.happypaws.domain.Role;
import com.happypaws.domain.User;
import com.happypaws.services.AppointmentService;
import com.happypaws.services.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/appointments")
public class AppointmentRestController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentRestController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping("/available-hours")
    public ResponseEntity<?> getAvailableHoursForDate(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day
    ) {
        try {
            Set<Integer> result = this.appointmentService.findAvailableHoursForDate(year, month, day);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return this.handleException();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAppointments(@RequestParam String role, @RequestParam String username) {
        try {
            List<Appointment> appointments;
            if (Role.DOCTOR.getLabel().equals(role)) {
                appointments = this.appointmentService.findAll();
                return new ResponseEntity<>(appointments, HttpStatus.OK);
            }
            if (Role.USER.getLabel().equals(role)) {
                User user = this.userService.findUserByUsername(username);
                if (user == null) {
                    JSONObject res = new JSONObject();
                    res.put("error", "No user found with entered username");
                    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
                }
                appointments = user.getAppointments();
                return new ResponseEntity<>(appointments, HttpStatus.OK);
            }
            JSONObject res = new JSONObject();
            res.put("error", "You do not have access to this resource");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return this.handleException();
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> putAppointment(@PathVariable("appointmentId") Long id, @RequestBody Appointment appointment) {
        JSONObject result = new JSONObject();
        try {
            Appointment oldAppointment = this.appointmentService.findById(id);
            appointment.setUser(oldAppointment.getUser());
            this.appointmentService.save(appointment);

            result.put("success", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            result.put("error", "Element was not found in db");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") Long id) {
        JSONObject result = new JSONObject();
        try {
            this.appointmentService.findById(id);
            this.appointmentService.deleteById(id);
            result.put("success", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            result.put("error", "Element was not found in db");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> postAppointment(@RequestParam String username, @RequestBody Appointment appointment) {
        JSONObject result = new JSONObject();
        try {
            LocalDateTime requestedDate = appointment.getDate().toLocalDateTime();
            Set<Integer> availableHours = this.appointmentService
                    .findAvailableHoursForDate(
                            requestedDate.getYear(),
                            requestedDate.getMonth().getValue(),
                            requestedDate.getDayOfMonth()
                    );
            if(!availableHours.contains(requestedDate.getHour())) {
                result.put("error", "Appointment time is not available");
                return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
            }

            User user = this.userService.findUserByUsername(username);
            appointment.setUser(user);
            this.appointmentService.save(appointment);

            result.put("success", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            result.put("error", "User was not found in db");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONObject> handleException() {
        JSONObject result = new JSONObject();
        result.put("error", "Internal server error");
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
