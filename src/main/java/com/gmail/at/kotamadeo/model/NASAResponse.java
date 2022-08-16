package com.gmail.at.kotamadeo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NASAResponse {
    private String copyright;
    private String date;
    private String explanation;
    @JsonProperty("hdurl")
    private String highResolutionUrl;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("service_version")
    private String serviceVersion;
    private String title;
    private String url;

    @Override
    public String toString() {
        return String.format("%s. %s.%n%s ©%s", title, date, explanation, copyright);
    }
}
