package com.example.newsapi.web;

import com.example.newsapi.service.TopNewsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TopNewsController.class)
class TopNewsControllerValidationTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TopNewsService service;

	@Test
	void badCountryReturns400() throws Exception {
		Mockito.when(service.getTopNews(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyBoolean(), Mockito.any()))
				.thenThrow(new IllegalArgumentException("source-country must be a 2-letter ISO 3166 code"));

		mockMvc.perform(get("/api/top-news")
					.param("source-country", "usa")
					.param("language", "en")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("validation_error"))
				.andExpect(jsonPath("$.message", containsString("source-country")));
	}
}