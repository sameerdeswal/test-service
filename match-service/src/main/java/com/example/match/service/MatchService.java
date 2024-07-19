package com.example.match.service;

import com.example.match.model.Match;
import com.example.match.model.MatchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class MatchService {
    
    
    @Value("${match.url}")
    private String matchUrl;
    
    public List<Match> findDrawMatches(int year) {
        RestTemplate restTemplate = new RestTemplate();
        matchUrl = matchUrl.replace("{year}", String.valueOf(year));
        ResponseEntity<MatchResponse> responseEntity = restTemplate.getForEntity(matchUrl, MatchResponse.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<Match> matches = Objects.requireNonNull(responseEntity.getBody()).getData();
            return matches.stream().filter(match -> Integer.parseInt(match.getTeam1goals()) == Integer.parseInt(match.getTeam2goals())).collect(Collectors.toList());
        }
        throw new RuntimeException("Error occurred while fetch match data");
    }
}
