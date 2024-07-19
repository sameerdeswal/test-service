package com.example.match.model;

import lombok.Data;

import java.util.List;

@Data
public class GetDrawMatchResponse {
    
    private String statusCode;
    private String statusMessage;
    private List<Match> matches;
}
