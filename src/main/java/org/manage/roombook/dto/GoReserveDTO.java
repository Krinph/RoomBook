package org.manage.roombook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.manage.roombook.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoReserveDTO {
    @NotNull(message = "RoomId shouldn't be null")
    private Integer roomId;

    @NotNull(message = "UserId shouldn't be null")
    private Integer userId;

    @NotNull(message = "Date shouldn't be null")
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "StartTime shouldn't be null")
    @FutureOrPresent
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @NotNull(message = "EndTime shouldn't be null")
    @Future
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setRoomId(this.roomId);
        reservation.setUserId(this.userId);
        reservation.setDate(this.date);
        reservation.setStartTime(this.startTime);
        reservation.setEndTime(this.endTime);
        return reservation;
    }
}
