package org.manage.roombook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.manage.roombook.entity.Room;

@Mapper
public interface RoomMapper {
    @Select("SELECT * FROM roominfo WHERE id = #{roomId}")
    Room getRoomById(int roomId);
}
