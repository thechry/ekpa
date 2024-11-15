package org.chrysafis.theodoros.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import org.chrysafis.theodoros.service.model.Citizen;
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
		assertTrue(messages.contains("tautotita is invalid!"));
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
		assertFalse(messages.contains("tautotita is invalid!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "*John", "^John", "John&", "John%2" })
	void checkInvalidName(String name) {
		Citizen citizen = new Citizen();
		citizen.SetCitizenName(name);
		assertEquals(name,citizen.GetCitizenName());
		
		Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("name is invalid!"));
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
		assertFalse(messages.contains("name is invalid!"));
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
		assertTrue(messages.contains("surname cannot be blank!"));
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
		assertFalse(messages.contains("surname name cannot be blank!"));
	}
	
	// klp ++++
}
