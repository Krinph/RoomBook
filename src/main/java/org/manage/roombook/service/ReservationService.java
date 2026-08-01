package org.manage.roombook.service;

import org.manage.roombook.entity.Reservation;
import org.manage.roombook.entity.Room;
import org.manage.roombook.entity.TimePeriod;
import org.manage.roombook.mapper.ReservationMapper;
import org.manage.roombook.mapper.RoomMapper;
import org.manage.roombook.util.TimeLists;
import org.manage.roombook.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    RoomService roomService;

    public List<Reservation> getAllReservations() {
        return reservationMapper.selectAll();
    }

    public Room selectRoom(int roomId) {
        return reservationMapper.selectRoomById(roomId);
    }

    public ArrayList<Reservation> selectReservationByRoom (int roomId) {
        return reservationMapper.selectByRoomId(roomId);
    }

    public ArrayList<ReservationVO> selectReservationByUser (int userId) {
        ArrayList<ReservationVO> reservationVOs = new ArrayList<>();
        ArrayList<Reservation> reservations = reservationMapper.selectByUserId(userId);
        for (Reservation res : reservations) {
            ReservationVO vo = new ReservationVO(res);
            vo.setRoomLocation(roomService.selectRoomLocationById(res.getRoomId()));
            reservationVOs.add(vo);
        }
        return reservationVOs;
    }

    public boolean checkConflict(Reservation reservation) {
        return !reservationMapper.checkConflict(reservation).isEmpty();
    }

    public ArrayList<Integer> checkFreeRoom(LocalDate date, LocalTime startTime, LocalTime endTime) {
        Reservation reservation = new Reservation();
        reservation.setDate(date);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        return reservationMapper.checkFreeRoom(reservation);
    }

    public ArrayList<TimePeriod> checkFreeTime(int roomId, LocalDate date) {
        Reservation reservation = new Reservation();
        reservation.setRoomId(roomId);
        reservation.setDate(date);
        ArrayList<TimePeriod> usedTime =  reservationMapper.checkUsedTime(reservation);
        TimeLists timeLists = new TimeLists();
        ArrayList<TimePeriod> freeTime = timeLists.getTimeLists();
        for (TimePeriod used : usedTime) {
            freeTime.removeIf(free -> !free.getStartTime().isBefore(used.getStartTime()) && !free.getEndTime().isAfter(used.getEndTime()));
        }
        return freeTime;
    }

    public boolean insertReservation (Reservation reservation) {
        int i = reservationMapper.insertReserve(reservation);
        return (i == 1);
    }
}
