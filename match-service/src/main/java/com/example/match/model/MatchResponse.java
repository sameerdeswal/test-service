package com.example.match.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchResponse {
    
    private String competition;
    private int page;
    @JsonProperty("per_page")
    private int perPage;
    private int total;
    @JsonProperty("total_pages")
    private int totalPages;
    private List<Match> data;
    
    public String getCompetition() {
        return competition;
    }
    
    public void setCompetition(String competition) {
        this.competition = competition;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPerPage() {
        return perPage;
    }
    
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public List<Match> getData() {
        return data;
    }
    
    public void setData(List<Match> data) {
        this.data = data;
    }
}
