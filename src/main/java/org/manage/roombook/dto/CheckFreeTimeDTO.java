package org.manage.roombook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.manage.roombook.entity.Reservation;

import java.time.LocalDate;

public class CheckFreeTimeDTO {
    @NotNull(message = "RoomId shouldn't be null")
    private Integer roomId;
    @NotNull(message = "Date shouldn't be null")
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
