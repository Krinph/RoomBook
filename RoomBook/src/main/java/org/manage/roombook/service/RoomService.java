package org.manage.roombook.service;

import org.manage.roombook.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    RoomMapper roomMapper;

    public String selectRoomLocationById(int roomId) {
        return roomMapper.getRoomById(roomId).getLocation();
    }
}
