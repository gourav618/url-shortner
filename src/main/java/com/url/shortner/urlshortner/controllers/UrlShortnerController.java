package com.url.shortner.urlshortner.controllers;

import com.url.shortner.urlshortner.service.ShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlShortnerController {

    @Autowired
    ShortenUrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestParam(value = "destinationUrl") String destinationUrl){
        String shortUrl = service.shortenUrl(destinationUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDestinationUrl(@RequestParam(value = "destinationUrl") String destinationUrl,
                                                  @RequestParam(value = "shortUrl") String shortUrl) {
        Boolean updated = service.updateShortenUrl(shortUrl, destinationUrl);
        if (updated){
            return ResponseEntity.ok("destination url updated !!");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/destinationUrl")
    public ResponseEntity<?> getDestinationUrl(@RequestParam(value = "shortUrl") String shortUrl){
        String destinationUrl = service.getDestinationUrl(shortUrl);
        if (destinationUrl == null){
            return ResponseEntity.badRequest().body("No such short url found!!");
        }
        return ResponseEntity.ok(destinationUrl);
    }

    @PostMapping("/addDaysInExpiry")
    public ResponseEntity<?> updateExpiryDate(@RequestParam(value = "shortUrl") String shortUrl,
                                              @RequestParam(value = "daysToAdd") Integer daysToAdd) {
        Boolean updated = service.updateExpiryDate(shortUrl, daysToAdd);
        if (updated)
            return ResponseEntity.ok("Expiry Date Updated");

        return ResponseEntity.badRequest().body("No such short url found!!");
    }

}
