package org.chrysafis.theodoros.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.chrysafis.theodoros.model.Citizen;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

public class CitizenUtility
{
	//Creating a citizen from each CSV row
		public static Citizen createPositiveCitizen(ArgumentsAccessor accessor, int start) {
			String tautotita = accessor.getString(start + 0);
			String name = accessor.getString(start + 1);
			String surname = accessor.getString(start + 2);
			String gender = accessor.getString(start + 3);			
			String dob = accessor.getString(start + 4);
			int afm = accessor.getInteger(start + 5);
			String address = accessor.getString(start + 6);
			
			//return new Citizen.CitizenBuilder(tautotita, name, surname, gender, dob).CitizenAfm(afm).CitizenAddress(address).build();
			return new Citizen.CitizenBuilder(tautotita, name, surname, gender, dob, afm, address).build();
		}
		
		//Trying to create a citizen from each CVS row - The creation of citizen will deliberately fail
		public static void createNegativeCitizen(ArgumentsAccessor accessor) {
			String tautotita = accessor.getString(0);
			String name = accessor.getString(1);
			String surname = accessor.getString(2);
			String gender = accessor.getString(3);
			String dob = accessor.getString(4);						
			int afm = accessor.getInteger(5);
			
			//Citizen citizen = new Citizen.CitizenBuilder(tautotita, name, surname, gender, dob).CitizenAfm(afm).build();
			Citizen citizen = new Citizen.CitizenBuilder(tautotita, name, surname, gender, dob, afm, "").build();
			System.out.println("Got citizen: " + citizen);
		}
		
		//Checking if a created citizen from a CVS row has correct values
		public static void checkCitizen(Citizen citizen, ArgumentsAccessor accessor) {
			assertEquals(citizen.GetTautotita(),accessor.get(0));
			assertEquals(citizen.GetCitizenName(),accessor.get(1));			
			assertEquals(citizen.GetCitizenSurname(),accessor.getString(2));
			assertEquals(citizen.GetCitizenGender(),accessor.getString(3));
			assertEquals(citizen.GetCitizenDoB(),accessor.getString(4));
			assertEquals(citizen.GetCitizenAfm(),accessor.getInteger(5));
			assertEquals(citizen.GetCitizenAddress(),accessor.getString(6));
		}
}
