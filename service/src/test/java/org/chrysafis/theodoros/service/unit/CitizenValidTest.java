package org.chrysafis.theodoros.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.chrysafis.theodoros.model.Citizen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CitizenValidTest implements TestInterface
{
	private static Validator validator = null;
	
	@BeforeAll
	static void constructValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	private static List<String> getMessages(Set<ConstraintViolation<Citizen>> viols){
		List<String> messages = new ArrayList<String>();
		for (ConstraintViolation<Citizen> viol: viols) {
			messages.add(viol.getMessage());
		}
		return messages;
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "tautotis123", "123Tautotis", "I", "123" })
	void checkInvalidTautotita(String tautotita) {
		Citizen citizen = new Citizen();
		citizen.SetTautotita(tautotita);
		assertEquals(tautotita,citizen.GetTautotita());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("Tautotita must have 8 chars!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "AX123456", "AB123123", "ZZ987654"})
	void checkValidTautotita(String tautotita) {
		Citizen citizen = new Citizen();
		citizen.SetTautotita(tautotita);
		assertEquals(tautotita,citizen.GetTautotita());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("Tautotita must have 8 chars!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "", "John512345123451234512345John123451234512345123451234512345" })
	void checkInvalidName(String name) {
		Citizen citizen = new Citizen();
		citizen.SetCitizenName(name);
		assertEquals(name,citizen.GetCitizenName());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("Name must be between 2-50 chars"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Kotsous", "Giors", "Mitsous"})
	void checkValidName(String name) {
		Citizen citizen = new Citizen();
		citizen.SetCitizenName(name);
		assertEquals(name,citizen.GetCitizenName());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("Name must be between 2-50 chars"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
	void checkInvalidSurname(String surname) {
		Citizen citizen = new Citizen();
		citizen.SetCitizenSurname(surname);
		assertEquals(surname,citizen.GetCitizenSurname());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("Surname must be 2-50 chars"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Papakotsous", "Papagiors", "Papamitsous" })
	void checkValidSurname(String surname) {
		Citizen citizen = new Citizen();
		citizen.SetCitizenSurname(surname);
		assertEquals(surname, citizen.GetCitizenSurname());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("Surname must be 2-50 chars"));
	}
	
	// klp ++++
}
