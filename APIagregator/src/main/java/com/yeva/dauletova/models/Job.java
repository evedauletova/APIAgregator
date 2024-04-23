package com.yeva.dauletova.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job {
    private String employerName;
    private String employerLogo;
    private String jobTitle;
    private String jobDescription;
    private String jobCity;
    private String jobState;
    private String country;
    private String jobLink;
    private double minSalary;
    private double maxSalary;
    private boolean jobIsRemote;


}
