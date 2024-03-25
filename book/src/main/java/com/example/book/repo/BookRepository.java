package com.example.book.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;

@Repository
public class BookRepository {
  
	@Value("${com.example.book.url}")
	private String Url;
	

	@Autowired
	private  RestTemplate restTemplate;


	public BookRepository(@Value("${com.example.book.url}") String url, RestTemplate restTemplate) {
		super();
		this.Url = url;
		this.restTemplate = restTemplate;
	}

	public BookResponse addBook(BookRequest bookRequest) {
		
		HttpEntity<?> book= new HttpEntity<>(bookRequest);
		
		ResponseEntity<BookResponse> response = restTemplate.exchange(Url,HttpMethod.POST, book, BookResponse.class);
	
		
		return response.getBody();
	}

	public BookResponse[] getBookById(Long id) {
		
		HttpEntity<?> book= new HttpEntity<>(id);
		
		ResponseEntity<BookResponse[]> response = restTemplate.exchange(Url, HttpMethod.GET, book ,BookResponse[].class);
		
		return response.getBody();
	}
     
}
