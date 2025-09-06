package com.example.newsapi.service;

import com.example.newsapi.client.WorldNewsClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TopNewsServiceNullBodyTest {
	@Test
	void throwsWhenBodyNull() {
		WorldNewsClient client = Mockito.mock(WorldNewsClient.class);
		Mockito.when(client.fetchTopNews(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyBoolean(), Mockito.any()))
				.thenReturn(new WorldNewsClient.TopNewsResult(null, new HttpHeaders()));
		TopNewsService service = new TopNewsService(client);
		assertThrows(IllegalStateException.class, () -> service.getTopNews("us","en", null, false, null));
	}
}