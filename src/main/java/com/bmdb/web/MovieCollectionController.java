package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.MovieCollection;
import com.bmdb.business.User;
import com.bmdb.db.MovieCollectionRepo;
import com.bmdb.db.MovieRepo;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movie-collections")
public class MovieCollectionController {
	@Autowired
	private MovieCollectionRepo movieCollectionRepo;
	@Autowired
	private UserRepo userRepo;
	
	// list - return all movieCollections
	@GetMapping("/")
	public List<MovieCollection> listMovieCollections() {
		return movieCollectionRepo.findAll();			

	}
	
	// get - return 1 movieCollection for the given id
	@GetMapping("/{id}")
	public Optional<MovieCollection> getMovie(@PathVariable int id) {
		Optional<MovieCollection> mc = movieCollectionRepo.findById(id);
		if (mc.isPresent())
			return mc;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection not found");
	}

	// add - adds a new MovieCollection
	@PostMapping("/")
	public MovieCollection addMovieCollection(@RequestBody MovieCollection mc) {
		// add a new movie Collection
		// for all maintenance methods recalculate the collection value
		try {
			// 1 do maintenance
			mc = movieCollectionRepo.save(mc);
			// 2 recalcCollectionValue
			recalculateCollectionTotal(mc.getUser());
		}
		catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mc;
	}
	
	// update - update a MovieCollection
	@PutMapping("/")
	public MovieCollection updateMovieColletion(@RequestBody MovieCollection mc) {
		// update a movie

		try {
			if (movieCollectionRepo.existsById(mc.getId())) {
				mc = movieCollectionRepo.save(mc);
				recalculateCollectionTotal(mc.getUser());
			}
			else {
				// record doesn't exist
				System.out.println("Error updating MovieCollection.  id: "+
											mc.getId() + " doesn't exist!");
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection not found");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection Error");
		}
		return mc;
	}
	
	// delete - delete a MovieCollection
	@DeleteMapping("/{id}")
	public MovieCollection deleteMovie(@PathVariable int id) {
		// delete a movie
		MovieCollection mc = null;
		
		try {
			if (movieCollectionRepo.existsById(id)) {
				mc = movieCollectionRepo.findById(id).get();
				User u = mc.getUser();
				movieCollectionRepo.deleteById(id);
				recalculateCollectionTotal(u);
			}
			else {
				// record doesn't exist
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection not found");
			}
		}
		catch (DataIntegrityViolationException dive) {
			System.out.println(dive.getRootCause().getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Collection Error");
		}
		return mc;
	}
	
	// method will recalculate the collectionValue and save it in the User instance 
	private void recalculateCollectionTotal(User u) {
		// get a list of movieCollection
		// all movies for user
		List<MovieCollection> mcList = movieCollectionRepo.findAllByUserId(u.getId());
		// loop thru list to sum a total
		double total = 0.0;
		for (MovieCollection mc: mcList) {
			total += mc.getPurchasePrice();
		}
		// save that total in the User instance
		u.setCollectionValue(total);
		try {
			userRepo.save(u);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
}