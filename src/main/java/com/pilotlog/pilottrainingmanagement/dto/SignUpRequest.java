package com.pilotlog.pilottrainingmanagement.dto;

import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.Users;
import lombok.Data;

@Data
public class SignUpRequest {
    private Users users;
    private Company company;

}
