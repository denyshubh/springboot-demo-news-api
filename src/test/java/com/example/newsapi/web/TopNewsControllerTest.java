package com.example.newsapi.web;

import com.example.newsapi.NewsApiApplication;
import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.model.TopNewsCluster;
import com.example.newsapi.model.TopNewsResponse;
import com.example.newsapi.model.WorldNewsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NewsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TopNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorldNewsClient worldNewsClient;

    private TopNewsResponse sample;

    @BeforeEach
    void setUp() {
        WorldNewsItem item = new WorldNewsItem();
        item.setId(1);
        item.setTitle("Headline");
        TopNewsCluster cluster = new TopNewsCluster();
        cluster.setNews(List.of(item));
        sample = new TopNewsResponse();
        sample.setTopNews(List.of(cluster));
        sample.setLanguage("en");
        sample.setCountry("us");
    }

    @Test
    void getTopNews_ok() throws Exception {
        Mockito.when(worldNewsClient.getTopNews(eq("us"), eq("en"), any(), any(), any()))
                .thenReturn(sample);

        mockMvc.perform(get("/api/news/top")
                        .param("source-country", "us")
                        .param("language", "en")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.language").value("en"))
                .andExpect(jsonPath("$.country").value("us"))
                .andExpect(jsonPath("$.top_news[0].news[0].title").value("Headline"));
    }

    @Test
    void getTopNews_validatesParams() throws Exception {
        mockMvc.perform(get("/api/news/top")
                        .param("source-country", "")
                        .param("language", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

