package com.url.shortner.urlshortner.service;

public interface ShortenUrlService {

    String shortenUrl (String destinationUrl);

    Boolean updateShortenUrl(String shortenUrl, String destinationUrl);

    String getDestinationUrl(String shortenUrl);

    Boolean updateExpiryDate(String shortenUrl, Integer daysToAdd);
}
