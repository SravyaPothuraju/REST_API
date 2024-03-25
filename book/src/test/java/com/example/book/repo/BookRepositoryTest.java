package com.example.book.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;
import com.example.book.repo.BookRepository;

class BookRepositoryTest {

	@Mock
	private RestTemplate restTemplate;
   
	@InjectMocks
	BookRepository bookRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		bookRepository = new BookRepository( "mock-url", restTemplate);
	}

	@Test
	void addBook() {
		BookRequest bookRequest = new BookRequest((long) 1, "sample", "sample author", "desc", 3.5);
		BookResponse expectedresponse = new BookResponse((long) 1, "sample", "sample author", "desc", 3.5);

		ResponseEntity<BookResponse> responseEntity = ResponseEntity.ok(expectedresponse);

		when(restTemplate.exchange("mock-url", HttpMethod.POST, new HttpEntity<>(bookRequest),
				BookResponse.class)).thenReturn(responseEntity);

		BookResponse actualResponse = bookRepository.addBook(bookRequest);
		assertEquals(expectedresponse, actualResponse);
	}

	@Test
	void getBookById() {

		long id = 2;

		BookResponse[] expectedResponse = { new BookResponse((long) 1, "sample", "sample author", "desc", 3.5),
				new BookResponse((long) 2, "sample2", "sample author2", "desc2", 4.0) };

		ResponseEntity<BookResponse[]> responseEntity = ResponseEntity.ok(expectedResponse);

		when(restTemplate.exchange("mock-url", HttpMethod.GET, new HttpEntity<>(id), BookResponse[].class))
				.thenReturn(responseEntity);

		BookResponse[] actualResponse = bookRepository.getBookById(id);

		assertEquals(expectedResponse, actualResponse);
	}
}
