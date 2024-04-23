package com.yeva.dauletova.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeva.dauletova.models.Job;
import com.yeva.dauletova.models.JobResponse;
import com.yeva.dauletova.models.WeatherObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Value("${api.key.jobs}")
    private String apiKey;
    public JobResponse getJobs(String query){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsearch.p.rapidapi.com/search?query="+query+"&page=1&num_pages=1")
                .build();
       String jobResponse = webClient.get().header("X-RapidAPI-Key", apiKey ).retrieve().bodyToMono(String.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        JobResponse jobs = new JobResponse();

        try {
            JsonNode jsonNode = objectMapper.readTree(jobResponse);
            jobs.setStatus(jsonNode.get("status").asText());
            JsonNode dataArray = jsonNode.get("data");
            List<Job>jobList = new ArrayList<>();
            for (JsonNode jobNode: dataArray){
                String employerName = jobNode.get("employer_name").asText();
                String employerLogo = jobNode.get("employer_logo").asText();
                String jobTitle = jobNode.get("job_title").asText();
                String jobDescription = jobNode.get("job_description").asText();
                String jobCity = jobNode.get("job_city").asText();
                String jobState = jobNode.get("job_state").asText();
                String jobCountry = jobNode.get("job_country").asText();
                String jobLink = jobNode.get("job_apply_link").asText();
                double minSalary = jobNode.get("job_min_salary").asDouble();
                double maxSalary = jobNode.get("job_max_salary").asDouble();
                boolean jobIsRemote = jobNode.get("job_is_remote").asBoolean();
                jobList.add(new Job(employerName, employerLogo, jobTitle, jobDescription
                ,jobCity, jobState, jobCountry, jobLink, minSalary, maxSalary, jobIsRemote));
            }
            jobs.setJob(jobList);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jobs;

    }
}
