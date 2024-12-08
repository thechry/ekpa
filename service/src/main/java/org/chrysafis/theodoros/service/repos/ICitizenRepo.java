package org.chrysafis.theodoros.service.repos;

import java.util.List;

import org.chrysafis.theodoros.model.Citizen;

//import org.chrysafis.theodoros.service.model.Citizen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitizenRepo extends JpaRepository<Citizen, String>{
	List<Citizen> findByTautotita(String tautotita);

	List<Citizen> findByCitizenSurname(String string);
}
