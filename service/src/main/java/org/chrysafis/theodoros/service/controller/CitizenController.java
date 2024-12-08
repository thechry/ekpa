package org.chrysafis.theodoros.service.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.InetAddress;
import java.net.URI;

import org.chrysafis.theodoros.model.Citizen;
import org.chrysafis.theodoros.service.repos.ICitizenRepo;

//import org.chrysafis.theodoros.model.Citizen;

//import org.chrysafis.theodoros.service.model.Citizen;

//import org.chrysafis.theodoros.service.repos.ICitizenRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final ICitizenRepo repo;
    
    CitizenController(ICitizenRepo arepo)
    {
        this.repo = arepo;
    }
    
    @GetMapping(produces = {"application/json", "application/xml"})
    List<Citizen> GetCitizens()
    {
        return repo.findAll();
    }
    
    @GetMapping(value = "{id}", produces = {"application/json", "application/xml"})
    Citizen getCitizen(@PathVariable String id) {
		return repo.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given tautotita does not exist!"));
	}
    
    @PostMapping(consumes = {"application/json", "application/xml"})
	ResponseEntity<?> insertCitizen(@Valid @RequestBody Citizen citizen) {
		if (repo.findById(citizen.GetTautotita()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citizen with given tautotita already exists!");
		else {
			repo.save(citizen);
			try {
				String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/citizens/" + citizen.GetTautotita();
				return ResponseEntity.created(new URI(url)).build();
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while generating the response!");
			}
		}
	}
    
    @PutMapping(value = "{id}", consumes = {"application/json", "application/xml"})
	ResponseEntity<?> updateCitizen(@PathVariable String id, @Valid @RequestBody Citizen citizen) {
		if (!citizen.GetTautotita().equals(id))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update citizen with wrong id!");
		else 
		{
			int afmSize = String.valueOf(Math.abs(citizen.GetCitizenAfm())).length();
				if(afmSize != 9)
				{ 
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Afm not 9 digits!");
				}
				else 
				{
					return repo.findById(id)
					      .map(oldCitizen -> {
					          //oldCitizen.SetCitizenName(citizen.GetCitizenName());
					          //oldCitizen.SetCitizenSurname(citizen.GetCitizenSurname());
					          //oldCitizen.SetCitizenGender(citizen.GetCitizenGender());
					          //oldCitizen.SetCitizenDoB(citizen.GetCitizenDoB());
					          oldCitizen.SetCitizenAfm(citizen.GetCitizenAfm());
					          oldCitizen.SetCitizenAddress(citizen.GetCitizenAddress());
					          repo.save(oldCitizen);
					          return ResponseEntity.noContent().build();
					        })
					      .orElseThrow(() -> 
					      	new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given tautotita does not exist!"));
				}
		}
	}
	
    
    @DeleteMapping("{id}")
	ResponseEntity<?> deleteCitizen(@PathVariable String id) {
		return repo.findById(id)
			    .map(oldCitizen -> {
			         repo.deleteById(id);
			         return ResponseEntity.noContent().build();
			    })
			    .orElseThrow(() -> 
			      	 new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given tautotita does not exist!"));
	}

/**********************
     TODO: SearchCitizen
    **********************/
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        System.out.println("Fieldname is: " + fieldName + " ErrorMessage:" + errorMessage);
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
