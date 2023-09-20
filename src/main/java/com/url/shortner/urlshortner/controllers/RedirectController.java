package com.url.shortner.urlshortner.controllers;

import com.url.shortner.urlshortner.service.ShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RedirectController {

    @Autowired
    ShortenUrlService service;

    @GetMapping("/{shortenString}")
    public void redirectToFullUrl(HttpServletResponse response,
                                  @PathVariable String shortenString) {
        try {
            String destinationUrl = service.getDestinationUrl(shortenString);
            response.sendRedirect(destinationUrl);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
