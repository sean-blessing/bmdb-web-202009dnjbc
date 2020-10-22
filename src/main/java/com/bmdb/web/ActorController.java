package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	// list all actors
	@GetMapping("")
	public List<Actor> getAllActors() {
		return actorRepo.findAll();
	}
	
	// get actor by id
	@GetMapping("/{id}")
	public Optional<Actor> getActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found");
		}
	}
	
	// add a actor
	@PostMapping("/")
	public Actor addActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// update a actor
	@PutMapping("/{id}")
	public Actor updateActor(@RequestBody Actor a, @PathVariable int id) {
		if (id == a.getId()) {
			return actorRepo.save(a);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor id does not match.");
		}
	}
	
	// delete a actor
	@DeleteMapping("/{id}")
	public Optional<Actor> deleteActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			actorRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found.");
		}
		return a;
	}
	
}
