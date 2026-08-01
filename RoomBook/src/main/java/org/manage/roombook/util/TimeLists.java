package org.manage.roombook.util;

import org.manage.roombook.entity.TimePeriod;

import java.time.*;
import java.util.ArrayList;

public class TimeLists {
    private final ArrayList<TimePeriod> timeLists = new ArrayList<>();
    public TimeLists() {
        int time = 10;
        while (time < 18) {
            LocalTime start = LocalTime.of(time, 0,0);
            LocalTime end = LocalTime.of(time+1,0,0);
            TimePeriod tp = new TimePeriod(start, end);
            timeLists.add(tp);
            time++;
        }
    }
    public ArrayList<TimePeriod> getTimeLists() {
        return new ArrayList<>(timeLists);
    }

//    public static HashMap<String, String> to_HashMap(ArrayList<TimePeriod> timePeriods) {
//        HashMap<String, String> map = new HashMap<>();
//        for (TimePeriod tp : timePeriods) {
//            map.put(tp.getStartTime(), tp.getEndTime());
//        }
//        return map;
//    }
}
