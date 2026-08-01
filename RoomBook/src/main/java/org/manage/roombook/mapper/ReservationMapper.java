package org.manage.roombook.mapper;

import org.apache.ibatis.annotations.*;
import org.manage.roombook.entity.Reservation;
import org.manage.roombook.entity.Room;
import org.manage.roombook.entity.TimePeriod;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Mapper
public interface ReservationMapper {
    @Select("Select * FROM reservations")
    List<Reservation> selectAll();

    @Select("SELECT * FROM reservations WHERE room_id=#{roomId}")
    ArrayList<Reservation> selectByRoomId(int roomId);

    @Select("SELECT * FROM reservations WHERE user_id=#{userId}")
    ArrayList<Reservation> selectByUserId(int userId);

    @Select("SELECT * FROM roominfo WHERE id=#{roomId}")
    Room selectRoomById(int roomId);

    @Select("SELECT 1 FROM reservations WHERE room_id=#{roomId} AND date=#{date} AND start_time<#{endTime} AND end_time>#{startTime}")
    ArrayList<Reservation> checkConflict(Reservation reservation);

    @Select("SELECT id FROM roominfo WHERE id NOT IN (SELECT room_id FROM reservations WHERE date=#{date} AND status='confirmed' AND start_time<=#{startTime} AND end_time>=#{endTime})")
    ArrayList<Integer> checkFreeRoom(Reservation reservation);

    @Select("SELECT start_time, end_time FROM reservations WHERE room_id=#{roomId} AND date=#{date} AND status='confirmed'")
    ArrayList<TimePeriod> checkUsedTime(Reservation reservation);

    @Transactional
    @Insert("INSERT INTO reservations (room_id, user_id, date, start_time, end_time, status) VALUES (#{roomId}, #{userId}, #{date}, #{startTime}, #{endTime}, 'confirmed')")
    int insertReserve (Reservation reservation);
}
