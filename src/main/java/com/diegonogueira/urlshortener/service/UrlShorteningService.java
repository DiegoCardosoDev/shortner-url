package com.diegonogueira.urlshortener.service;

import com.diegonogueira.urlshortener.dto.ShortenUrlRequest;
import com.diegonogueira.urlshortener.dto.ShortenUrlResponse;
import com.diegonogueira.urlshortener.entities.UrlEntity;
import com.diegonogueira.urlshortener.repository.Urlrepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UrlShorteningService {

    private final Urlrepository urlRepository;

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request, String baseUrl) {
        String id;

        do {
            id = RandomStringUtils.randomAlphabetic(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.getUrl(), LocalDateTime.now().plusMinutes(1)));

        String redirectUrl = baseUrl.replace("shorten-url", id);

        return new ShortenUrlResponse(redirectUrl);
    }

}
