package org.chrysafis.theodoros.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.chrysafis.theodoros.model.Citizen;
import org.chrysafis.theodoros.service.repos.ICitizenRepo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

public class DBTest  implements TestInterface
{
	@Autowired
	ICitizenRepo repo;
	
	/* Checking citizen insertion positive cases */
	@ParameterizedTest
	@Order(5)
	@CsvFileSource(resources="/positiveSingleCitizen.csv")
	void checkPositiveSingleCitizenWithDB(ArgumentsAccessor accessor) {
		Citizen citizen = CitizenUtility.createPositiveCitizen(accessor,0);
		repo.save(citizen);
		citizen = repo.findById(accessor.getString(0)).orElse(null);
		assertNotNull(citizen);
		CitizenUtility.checkCitizen(citizen,accessor);
	}
	
	/* Checking citizen deletion positive cases */
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"AX123456, Kotsous, Papakotsous, Aren, 12-02-1997",
		"AB123123, Giors, Papagiors, Aren, 02-05-1869",
		"ZZ987654, Mitsous, Papamitsous, Aren, 01-25-1991",
	})
	void checkCitizenDeletion(String tautotita, String name, String surname, String gender, String dob) {		
		Citizen citizen = new Citizen.CitizenBuilder(tautotita,name,surname, gender, dob, 0, "").build();
		repo.save(citizen);
		repo.deleteById(tautotita);
		citizen = repo.findById(tautotita).orElse(null);
		assertNull(citizen);
	}
	
	//Checking citizen update on obligatory fields
		@ParameterizedTest
		@Order(4)
		@CsvSource({
			"AX123450, Kotsous, Papakotsous, Aren, 12-02-1997",
			"AB123120, Giors, Papagiors, Aren, 02-05-1869",
			"ZZ987650, Mitsous, Papamitsous, Aren, 01-25-1991",
		})
		void checkCitizenUpdate(String tautotita, String name, String surname, String gender, String dob) {
			Citizen citizen = new Citizen.CitizenBuilder(tautotita, name, surname, gender, dob, 0, "").build();
			repo.save(citizen);
			Random r = new Random();
			int choice = r.nextInt(4);
			switch(choice) {
				case 0: citizen.SetCitizenName("NAME"); break;
				case 1: citizen.SetCitizenSurname("SURNAME"); break;
				case 2: citizen.SetCitizenGender("GENDER"); break;
				case 3: citizen.SetCitizenDoB("DOB"); break;
			}
			repo.save(citizen);
			citizen = repo.findById(tautotita).orElse(null);
			assertNotNull(citizen);
			if (choice == 0) {
				assertEquals(citizen.GetCitizenName(), "NAME");
				assertEquals(citizen.GetCitizenSurname(), surname);
				assertEquals(citizen.GetCitizenGender(), gender);
				assertEquals(citizen.GetCitizenDoB(), dob);
			}
			else if (choice == 1) {
				assertEquals(citizen.GetCitizenName(), name);
				assertEquals(citizen.GetCitizenSurname(), "SURNAME");
				assertEquals(citizen.GetCitizenGender(), gender);
				assertEquals(citizen.GetCitizenDoB(),dob);
			}
			else if (choice == 2) {
				assertEquals(citizen.GetCitizenName(), name);
				assertEquals(citizen.GetCitizenSurname(), surname);
				assertEquals(citizen.GetCitizenGender(), "GENDER");
				assertEquals(citizen.GetCitizenDoB(),dob);
			}
			else {
				assertEquals(citizen.GetCitizenName(), name);
				assertEquals(citizen.GetCitizenSurname(), surname);
				assertEquals(citizen.GetCitizenGender(), gender);
				assertEquals(citizen.GetCitizenDoB(),"DOB");
			}
		}
		
		//Checking citizen retrieval 
		@Test
		@Order(3)
		void checkCitizenRetrieval() {
			Citizen citizen1 = new Citizen.CitizenBuilder("AX123450", "Kotsous","Papakotsous", "Aren", "12-02-1997", 0, "").build();
			Citizen citizen2 = new Citizen.CitizenBuilder("AB123120", "Giors", "Papagiors", "Aren", "02-05-1869", 0, "").build();
			Citizen citizen3 = new Citizen.CitizenBuilder("ZZ987650", "Mitsous", "Papamitsous", "Aren", "01-25-1991", 0, "").build();
			repo.save(citizen1);
			repo.save(citizen2);
			repo.save(citizen3);
			List<Citizen> citizens = repo.findAll();
			
			//Checking if we have 3 citizens and these are citizen1, citizen2 & citizen3
			assertEquals(citizens.size(),3);
			int matches = 0;
			for (Citizen citizen: citizens) {
				if (citizen.equals(citizen1) || citizen.equals(citizen2) || citizen.equals(citizen3)) {
					matches++;
				}
			}
			assertEquals(matches,3);
			
			//Checking that with 1 surname, we get only one citizen
			citizens = repo.findByCitizenSurname("Papakotsous");
			assertEquals(citizens.size(),1);
			assertEquals(citizens.get(0),citizen1);
			citizens = repo.findByCitizenSurname("Papagiors");
			assertEquals(citizens.size(),1);
			assertEquals(citizens.get(0),citizen2);			
		}
}
