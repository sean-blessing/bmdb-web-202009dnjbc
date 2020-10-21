package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepo;

@CrossOrigin
@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	// list all movies
	@GetMapping("/")
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}
	
	// get movie by id
	@GetMapping("/{id}")
	public Optional getMovie(@PathVariable int id) {
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
	@DeleteMapping("/")
	public void deleteMovie(@RequestBody Movie m) {
		movieRepo.delete(m);
	}
	
}
