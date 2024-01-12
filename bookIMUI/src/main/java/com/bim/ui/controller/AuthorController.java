package com.bim.ui.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.bim.ui.model.Author;

@Controller
public class AuthorController {

	private ApiService apiService;

	public AuthorController(ApiService apiService) {
		this.apiService = apiService;
	}

	@GetMapping("/home")
	public String showHome() {
		return "home";
	}

	@GetMapping("/authors")
	public String yourPage(Model model) {
		List<Author> data = apiService.fetchData();
		model.addAttribute("author", data); // block() used for simplicity, consider using async Thymeleaf
											// templates
		return "authorlist"; // Replace with the name of your Thymeleaf template
	}

	@GetMapping("/addAuthor")
	public String showAddAuthorForm(Model model) {
		model.addAttribute("auth", new Author());
		return "authorform"; // Assuming you have a Thymeleaf template named add-employee.html
	}

	@PostMapping("/addAuthor")
	public String addAuthor(@ModelAttribute Author author) {
		// Assuming you have a method in ApiService to save the employee
		apiService.addAuthor(author);
		return "redirect:/authors"; // Redirect to the employee list after adding a new employee
	}

//	@GetMapping("/delete/{authorId}")
//	public String deleteEmployee(@PathVariable int authorId) {
//		apiService.deleteAuthor(authorId);
//		return "redirect:/authors";
//	}
//
	@GetMapping("/edit/{authorId}")
	public String showEditForm(@PathVariable int authorId, Model model) {
		Author author = apiService.getAuthorById(authorId);// Ensure subscription to trigger the reactive chain
		model.addAttribute("auth", author);
		return "editauthorform";
	}

	@PostMapping("/update/{authorId}")
	public String updateAuthor(@PathVariable("authorId") int authorId,
			@ModelAttribute Author updatedAuthor) {
		apiService.updateAuthor(authorId, updatedAuthor);
		return "redirect:/authors";
	}

}
