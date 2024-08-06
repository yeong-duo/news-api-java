package com.yeongduo.newsapijava;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NaverNewsApiTest {

    public static class NaverNewsResponse {
        private String lastBuildDate;
        private int total;
        private int start;
        private int display;
        private List<NewsItem> items;

        // Getters and Setters
        public String getLastBuildDate() {
            return lastBuildDate;
        }

        public void setLastBuildDate(String lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getDisplay() {
            return display;
        }

        public void setDisplay(int display) {
            this.display = display;
        }

        public List<NewsItem> getItems() {
            return items;
        }

        public void setItems(List<NewsItem> items) {
            this.items = items;
        }
    }

    public static class NewsItem {
        private String title;
        private String originallink;
        private String link;
        private String description;
        private String pubDate;

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOriginallink() {
            return originallink;
        }

        public void setOriginallink(String originallink) {
            this.originallink = originallink;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }
    }

    @Test
    public void shouldFetchNewsFromNaverAPI() {
        String clientId = "...";
        String clientSecret = "...";
        String query = "Kotlin";

        URI uri = UriComponentsBuilder.fromHttpUrl("https://openapi.naver.com/v1/search/news.json")
                .queryParam("query", query)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "date")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);

        RequestEntity<?> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NaverNewsResponse> response = restTemplate.exchange(request, NaverNewsResponse.class);

        NaverNewsResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        System.out.println("Total Results: " + responseBody.getTotal());
        for (NewsItem item : responseBody.getItems()) {
            System.out.println("Title: " + item.getTitle() + ", Link: " + item.getLink());
        }
    }
}