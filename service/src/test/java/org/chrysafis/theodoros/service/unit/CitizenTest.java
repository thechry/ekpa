package org.chrysafis.theodoros.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.chrysafis.theodoros.model.Citizen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class CitizenTest implements TestInterface
{
	//Checking multiple positive cases for citizen creation through its Builder interface
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleCitizen.csv")
	void checkPositiveSingleCitizen(ArgumentsAccessor accessor) {
		Citizen citizen = CitizenUtility.createPositiveCitizen(accessor,0);
		CitizenUtility.checkCitizen(citizen,accessor);
	}
	
	//Checking multiple negative cases where obligatory citizen fields have null or empty or white space values
	@ParameterizedTest
	@CsvFileSource(resources="/negativeSingleCitizen.csv")
	void checkNegativeSingleCitizen(ArgumentsAccessor accessor) {
		Exception e = assertThrows(IllegalArgumentException.class, ()-> CitizenUtility.createNegativeCitizen(accessor));
		
		assertEquals(accessor.getString(6), e.getMessage());
	}
	
	//Checking that all fields of a citizen are null when its empty constructor is used
	@Test
	void checkEmptyConstructor() {
		Citizen citizen = new Citizen();
		assertNull(citizen.GetTautotita());
		assertNull(citizen.GetCitizenName());
		assertNull(citizen.GetCitizenSurname());
		assertNull(citizen.GetCitizenGender());
		assertNull(citizen.GetCitizenDoB());
		//assertNull(citizen.GetCitizenAfm());
		assertEquals(citizen.GetCitizenAfm(), 0);
		assertNull(citizen.GetCitizenAddress());		
	}
	
	//Checking multiple negative cases for wrong tautotita values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforTautotita(String tautotita) {
		Citizen citizen = new Citizen();
		
		Exception e = assertThrows(IllegalArgumentException.class, ()-> citizen.SetTautotita(tautotita));
		assertEquals("Tautotita cannot be null or empty", e.getMessage());
	}
		
		
}
