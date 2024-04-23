package com.yeva.dauletova.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class JobResponse {
    private String status;
    private List<Job>job;

}
