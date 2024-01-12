package com.bim.ui.controller;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.bim.ui.model.Author;

@Service
public class ApiService {

	private final WebClient webClient;

	public ApiService() {
		this.webClient = WebClient.create("http://localhost:8080/api"); // Replace with your API base URL
	}

	public List<Author> fetchData() {
		return webClient.get().uri("/authors") // Replace with your API endpoint
				.retrieve().bodyToFlux(Author.class).collectList().block();
	}

	public HttpStatusCode addAuthor(Author author) {
		return webClient.post().uri("/author/post").body(BodyInserters.fromValue(author)).retrieve().toBodilessEntity()
				.block().getStatusCode();
	}

//    public HttpStatusCode deleteAuthor(int authorId) {
//        return webClient.delete().uri("/delete/{authorId}", authorId) // Replace with your API endpoint
//                .retrieve().toBodilessEntity().block().getStatusCode();
//    }
    
    public Author getAuthorById(int authorId) {
        return webClient.get()
                .uri("/author/{id}", authorId)
                .retrieve()
                .bodyToMono(Author.class).block();
    }


    public HttpStatusCode updateAuthor(int authorId, Author updatedAuthor) {
        return webClient.put()
                .uri("/update/{authorId}", authorId)
                .body(BodyInserters.fromValue(updatedAuthor))
                .retrieve()
                .toBodilessEntity()
	            .block()
	            .getStatusCode();
    }
    



}
