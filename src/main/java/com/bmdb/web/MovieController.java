package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	// list all movies
	@GetMapping("/")
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}
	
	// list all movies for rating
	@GetMapping("")
	public List<Movie> getAllMoviesByRating(@RequestParam String rating) {
		return movieRepo.findByRating(rating);
	}
	// get movie by id
	@GetMapping("/{id}")
	public Optional<Movie> getMovie(@PathVariable int id) {
		Optional<Movie> m = movieRepo.findById(id);
		return m;
	}
	
	// add a movie
	@PostMapping("/")
	public Movie addMovie(@RequestBody Movie m) {
		return movieRepo.save(m);
	}
	
	// update a movie
	@PutMapping("/")
	public Movie updateMovie(@RequestBody Movie m) {
		return movieRepo.save(m);
	}
	
	// delete a movie
	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable int id) {
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent()) {
			movieRepo.delete(m.get());
		}
		else  {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
		}
	}
	
}
