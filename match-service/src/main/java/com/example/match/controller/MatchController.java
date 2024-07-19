package com.example.match.controller;

import com.example.match.config.CustomRateLimitConfig;
import com.example.match.model.GetDrawMatchResponse;
import com.example.match.model.Match;
import com.example.match.service.MatchService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;

@Log
@RestController
public class MatchController {
    
    @Autowired
    private MatchService matchService;
    
    @CustomRateLimitConfig(type = "match")
    @GetMapping("/match/draw")
    public GetDrawMatchResponse getDrawMatchResponse(@RequestParam("year") int year) {
        GetDrawMatchResponse drawMatchResponse = new GetDrawMatchResponse();
        try {
            List<Match> matchList = matchService.findDrawMatches(year);
            drawMatchResponse.setStatusCode("200");
            drawMatchResponse.setStatusMessage("Successfully get matches.");
            drawMatchResponse.setMatches(matchList);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while getting draw matches : " + e.getMessage(), e);
            drawMatchResponse.setStatusCode("500");
            drawMatchResponse.setStatusMessage("Error while fetching match data.");
        }
        return drawMatchResponse;
    }
}
