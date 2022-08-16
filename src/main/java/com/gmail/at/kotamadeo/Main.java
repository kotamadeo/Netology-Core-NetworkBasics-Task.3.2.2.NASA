package com.gmail.at.kotamadeo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.at.kotamadeo.model.NASAResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String url = "https://api.nasa.gov/planetary/apod?api_key=F4ChtzseVKQLta1Nq68ih45Pl0W1QK1aHfe0Gl1J";
    private static final String pathDirectory = "\\Module_3-Network_basics-Task_3.2.2-NASA_API\\";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        printNasaResponse(objectMapper, url);
        saveContent(objectMapper, url, pathDirectory);
    }

    private static CloseableHttpClient httpClientConfiguration() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            return HttpClients.createDefault();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printNasaResponse(ObjectMapper objectMapper, String url) {
        try {
            NASAResponse nasaResponse = objectMapper.readValue(httpClientConfiguration()
                    .execute(new HttpGet(url)).getEntity().getContent(), new TypeReference<>() {
            });
            System.out.println(nasaResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveContent(ObjectMapper objectMapper, String url, String path) {
        String pathFile = null;
        try {
            NASAResponse nasaResponse = objectMapper.readValue(httpClientConfiguration()
                    .execute(new HttpGet(url)).getEntity().getContent(), new TypeReference<>() {
            });
            String[] temp = nasaResponse.getHighResolutionUrl().split("/");
            if (nasaResponse.getMediaType().equals("image") || nasaResponse.getMediaType().equals("video")) {
                pathFile = temp[temp.length - 1];
            }
            Path pathToFile = Path.of(path + pathFile);
            if (Files.notExists(pathToFile)) {
                try (InputStream inputStream = new URL(nasaResponse.getHighResolutionUrl()).openStream()) {
                    Files.copy(inputStream, pathToFile);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}