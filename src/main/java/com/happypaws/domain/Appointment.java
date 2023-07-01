package com.happypaws.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "appointment")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;
    private String animalName;
    private Animal animal;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private User user;
    private Timestamp date;

    @JsonIgnore
    public int getTime() {
        return this.date.toLocalDateTime().getHour();
    }
}
