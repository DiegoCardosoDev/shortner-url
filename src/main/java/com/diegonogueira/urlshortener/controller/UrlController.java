package com.diegonogueira.urlshortener.controller;

import com.diegonogueira.urlshortener.dto.ShortenUrlRequest;
import com.diegonogueira.urlshortener.dto.ShortenUrlResponse;
import com.diegonogueira.urlshortener.entities.UrlEntity;
import com.diegonogueira.urlshortener.repository.Urlrepository;
import com.diegonogueira.urlshortener.service.UrlShorteningService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class UrlController {

    private final Urlrepository urlrepository;
    private final UrlShorteningService urlShorteningService;

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                                         HttpServletRequest httpServlet) {
        String baseUrl = httpServlet.getRequestURL().toString();
        ShortenUrlResponse response = urlShorteningService.shortenUrl(request, baseUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable String id ){
       var url = urlrepository.findById(id);

       if (url.isEmpty()){
           return ResponseEntity.notFound().build();
       }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
