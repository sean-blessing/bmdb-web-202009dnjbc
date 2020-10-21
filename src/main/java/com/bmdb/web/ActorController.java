package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepo;

@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	// list all actors
	@GetMapping("/")
	public List<Actor> getAllActors() {
		return actorRepo.findAll();
	}
	
	// get actor by id
	@GetMapping("/{id}")
	public Optional getActor(@PathVariable int id) {
		Optional<Actor> m = actorRepo.findById(id);
		return m;
	}
	
	// add a actor
	@PostMapping("/")
	public Actor addActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// update a actor
	@PutMapping("/")
	public Actor updateActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// delete a actor
	@DeleteMapping("/")
	public void deleteActor(@RequestBody Actor a) {
		actorRepo.delete(a);
	}
	
}
