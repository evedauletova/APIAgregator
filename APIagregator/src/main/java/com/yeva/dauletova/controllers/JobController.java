package com.yeva.dauletova.controllers;

import com.yeva.dauletova.models.JobResponse;
import com.yeva.dauletova.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobController {
    private JobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping("/jobs")
    public String jobsPage(Model model){
        model.addAttribute("job_response", new JobResponse());
           return "jobs";

    }
    @PostMapping("/jobs")
    public String getJobs(Model model, @RequestParam String query){
        JobResponse jobResponse = jobService.getJobs(query);
        model.addAttribute("job_response", jobResponse);
        return "jobs";
    }
}
