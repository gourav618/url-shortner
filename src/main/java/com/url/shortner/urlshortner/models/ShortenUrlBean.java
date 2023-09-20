package com.url.shortner.urlshortner.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ShortenUrlBean {

    @CsvBindByName
    private String destinationUrl;
    @CsvBindByName
    private String shortenUrl;
    @CsvBindByName
    private Integer daysToValidity;
    @CsvBindByName
    @CsvDate("dd.MM.yyyy")
    private Date validSince;

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public Integer getDaysToValidity() {
        return daysToValidity;
    }

    public void setDaysToValidity(Integer daysToValidity) {
        this.daysToValidity = daysToValidity;
    }

    public Date getValidSince() {
        return validSince;
    }

    public void setValidSince(Date validSince) {
        this.validSince = validSince;
    }
}
