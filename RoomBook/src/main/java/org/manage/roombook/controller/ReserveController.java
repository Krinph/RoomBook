package org.manage.roombook.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.manage.roombook.dto.CheckFreeRoomDTO;
import org.manage.roombook.dto.CheckFreeTimeDTO;
import org.manage.roombook.dto.GoReserveDTO;
import org.manage.roombook.entity.Reservation;
import org.manage.roombook.entity.Result;
import org.manage.roombook.entity.Room;
import org.manage.roombook.entity.TimePeriod;
import org.manage.roombook.exception.BusinessException;
import org.manage.roombook.exception.ConflictException;
import org.manage.roombook.service.ReservationService;
import org.manage.roombook.util.ErrorType;
import org.manage.roombook.util.SecurityUtil;
import org.manage.roombook.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
public class ReserveController {
    @Autowired
    private ReservationService reservationService;

//    @GetMapping("/reservation")
//    public String user() {
//        return "reservePage";
//    }

    // Check Free Room
    @PostMapping("/CFR")
    @ResponseBody
    public Result<ArrayList<Room>> checkFreeRoom(@Valid @RequestBody CheckFreeRoomDTO dto) {
        LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now();
        LocalTime startTime = dto.getStartTime() != null ? dto.getStartTime() : LocalTime.now();
        LocalTime endTime = dto.getEndTime() != null ? dto.getEndTime() : LocalTime.of(18, 0, 0);

        List<Integer> freeRoom = reservationService.checkFreeRoom(date, startTime, endTime);
        ArrayList<Room> roomList = new ArrayList<>();
        for (Integer roomId : freeRoom) {
            roomList.add(reservationService.selectRoom(roomId));
        }
        return Result.success(roomList);
    }

    // Check Free Time
    @PostMapping("/CFT")
    @ResponseBody
    public Result<ArrayList<TimePeriod>> checkFreeTime(@Valid @RequestBody CheckFreeTimeDTO dto) {
        LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now();
        ArrayList<TimePeriod> freeTime = reservationService.checkFreeTime(dto.getRoomId(), date);
        return Result.success(freeTime);
    }

    // Reserve
    @PostMapping("/reserve")
    @ResponseBody
    public Result<String> goReserve(@Valid @RequestBody GoReserveDTO dto, HttpServletRequest request) {
        Claims claims = SecurityUtil.getChaim(request);
        int userId = Integer.parseInt(claims.getSubject());
        Reservation res = dto.toEntity();
        res.setUserId(userId);
        if (reservationService.checkConflict(res)) {
            throw new ConflictException("The time period is conflict");
        }
        if (reservationService.insertReservation(res)) {
            return Result.success("");
        } else {
            throw new BusinessException(ErrorType.SYSTEM_ERROR);
        }
    }

//    @GetMapping("/checkHistory")
//    public String checkHistory() {
//        return "historyPage";
//    }

    // Check History
    @PostMapping("/CH")
    @ResponseBody
    public Result<ArrayList<ReservationVO>> checkHistory(HttpServletRequest request) {
        Claims claims = SecurityUtil.getChaim(request);
        int userId = Integer.parseInt(claims.getSubject());
        ArrayList<ReservationVO> history = reservationService.selectReservationByUser(userId);
        return Result.success(history);
    }
}
