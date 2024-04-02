package com.pilotlog.pilottrainingmanagement.dto;

import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import lombok.Data;

import java.util.Map;

@Data
public class AttendanceDetailRequest {
    private AttendanceDetail attendanceDetail;
    private Map<Long, Integer> ratings;


}
