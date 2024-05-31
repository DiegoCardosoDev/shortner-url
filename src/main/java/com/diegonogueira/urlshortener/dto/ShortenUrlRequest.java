package com.diegonogueira.urlshortener.dto;


import lombok.Getter;
import lombok.Setter;


public record ShortenUrlRequest(String url) {


    public String getUrl() {
        return url;
    }


}
