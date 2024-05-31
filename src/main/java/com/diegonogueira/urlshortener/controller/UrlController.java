package com.diegonogueira.urlshortener.controller;

import com.diegonogueira.urlshortener.dto.ShortenUrlRequest;
import com.diegonogueira.urlshortener.dto.ShortenUrlResponse;
import com.diegonogueira.urlshortener.entities.UrlEntity;
import com.diegonogueira.urlshortener.repository.Urlrepository;
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

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                                         HttpServletRequest httpServlet){

        String id;

        do {

            id = RandomStringUtils.randomAlphabetic(5,10);

        }while (urlrepository.existsById(id));
        urlrepository.save(new UrlEntity(id,request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirctUrl = httpServlet.getRequestURL().toString().replace("shorten-url",id);

        return  ResponseEntity.ok(new ShortenUrlResponse(redirctUrl));
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
