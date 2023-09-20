package com.url.shortner.urlshortner.service;

import com.url.shortner.urlshortner.models.ShortenUrlBean;
import com.url.shortner.urlshortner.repo.ShortenUrlRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ShortenUrlServiceImpl implements ShortenUrlService {

    @Autowired
    private ShortenUrlRepo shortenUrlRepo;

    @Override
    public String shortenUrl(String destinationUrl) {
        String randomString = RandomStringUtils.randomAlphanumeric(10, 30);

        boolean b = shortenUrlRepo.writeToCsv(randomString, destinationUrl);
        if (b){
            return randomString;
        }else {
            throw new RuntimeException("Error while writing to csv");
        }
    }

    @Override
    public Boolean updateShortenUrl(String shortenUrl, String destinationUrl) {
        return shortenUrlRepo.updateDestinationUrl(shortenUrl, destinationUrl);
    }

    @Override
    public String getDestinationUrl(String shortenUrl) {
        String destinationUrl = null;
        try {
            List<ShortenUrlBean> csvData = shortenUrlRepo.getCsvData();

            Optional<ShortenUrlBean> rowNeeded = csvData.stream()
                    .filter(row -> row.getShortenUrl().equals(shortenUrl))
                    .findFirst();

//            destinationUrl = rowNeeded.map(ShortenUrlBean::getDestinationUrl).orElse(null);
            if (rowNeeded.isPresent()){
                ShortenUrlBean shortenUrlBean = rowNeeded.get();
                boolean isExpired = checkIfExpired(shortenUrlBean);
                if (isExpired){
                    throw new RuntimeException("URL Expired!!");
                }
                destinationUrl = shortenUrlBean.getDestinationUrl();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return destinationUrl;
    }

    private boolean checkIfExpired(ShortenUrlBean shortenUrlBean) {
        long diffInMillis = Math.abs(new Date().getTime() - shortenUrlBean.getValidSince().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return diff > shortenUrlBean.getDaysToValidity();
    }

    @Override
    public Boolean updateExpiryDate(String shortenUrl, Integer daysToAdd) {
        return shortenUrlRepo.updateExpiryDate(shortenUrl, daysToAdd);
    }
}
