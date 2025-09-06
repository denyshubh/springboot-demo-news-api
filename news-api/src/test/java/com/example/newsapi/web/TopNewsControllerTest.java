package com.example.newsapi.web;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.dto.TopNewsResponse;
import com.example.newsapi.service.TopNewsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TopNewsController.class)
class TopNewsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TopNewsService service;

	@Test
	void getTopNews_ok_forwardsHeadersAndBody() throws Exception {
		TopNewsResponse.NewsItem item = new TopNewsResponse.NewsItem(1L, "title", "text", "summary", "url", null, null, null, null, List.of());
		TopNewsResponse.NewsCluster cluster = new TopNewsResponse.NewsCluster(List.of(item));
		TopNewsResponse body = new TopNewsResponse(List.of(cluster), "en", "us");
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-API-Quota-Request", "1");
		headers.add("X-API-Quota-Used", "2");
		headers.add("X-API-Quota-Left", "3");
		Mockito.when(service.getTopNews("us", "en", LocalDate.of(2024, 5, 29), false, null))
				.thenReturn(new WorldNewsClient.TopNewsResult(body, headers));

		mockMvc.perform(get("/api/top-news")
					.param("source-country", "us")
					.param("language", "en")
					.param("date", "2024-05-29")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(header().string("X-API-Quota-Request", "1"))
				.andExpect(header().string("X-API-Quota-Used", "2"))
				.andExpect(header().string("X-API-Quota-Left", "3"))
				.andExpect(jsonPath("$.language", is("en")))
				.andExpect(jsonPath("$.country", is("us")))
				.andExpect(jsonPath("$.top_news[0].news[0].title", is("title")));
	}
}