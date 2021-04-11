package com.example.asclepiusjobs.dto;

import java.util.List;

public class SearchResultJobOffersDto {
    private int resultsCount;
    private Integer page;
    private List<JobOfferResultDto> results;

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public List<JobOfferResultDto> getResults() {
        return results;
    }

    public void setResults(List<JobOfferResultDto> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
