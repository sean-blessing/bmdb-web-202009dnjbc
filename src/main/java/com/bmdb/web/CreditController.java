package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepo;

@CrossOrigin
@RestController
@RequestMapping("/credits")
public class CreditController {
	
	@Autowired
	private CreditRepo creditRepo;
	
	// list all credits
	@GetMapping("/")
	public List<Credit> getAllCredits() {
		return creditRepo.findAll();
	}
	
	// get credit by id
	@GetMapping("/{id}")
	public Optional getCredit(@PathVariable int id) {
		Optional<Credit> m = creditRepo.findById(id);
		return m;
	}
	
	// add a credit
	@PostMapping("/")
	public Credit addCredit(@RequestBody Credit c) {
		return creditRepo.save(c);
	}
	
	// update a credit
	@PutMapping("/")
	public Credit updateCredit(@RequestBody Credit c) {
		return creditRepo.save(c);
	}
	
	// delete a credit
	@DeleteMapping("/")
	public void deleteCredit(@RequestBody Credit c) {
		creditRepo.delete(c);
	}
	
}
