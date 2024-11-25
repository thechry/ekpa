package org.chrysafis.theodoros.service.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.context.WebApplicationContext;

import org.chrysafis.theodoros.service.model.Citizen;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the Web application, by checking that the index page returns a code 200.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisplayName("Adding Citizen Testing")
@TestMethodOrder(OrderAnnotation.class)
public class SecondIT  implements TestLifecycleLogger
{
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	@Test
    @Order(1)
    public void addWrongCitizen() throws Exception{
    	Citizen citizen = new Citizen();
    	given().contentType("application/json").body(citizen).when().post("/api/citizens").then().assertThat().statusCode(400);
    }
	
	private Citizen createCitizen(String tautotita) {
    	Random r = new Random();
    	Citizen citizen = new Citizen();
    	citizen.SetTautotita(tautotita);
    	int nameId = r.nextInt(10) + 1;
    	citizen.SetCitizenName("Name" + nameId);
    	int surnameId = r.nextInt(10) + 1;
    	citizen.SetCitizenSurname("Surname" + surnameId);
    	citizen.SetCitizenGender("Aren");
    	int yearId = r.nextInt(110) + 1 ;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate dobDate = LocalDate.now().minusYears(yearId);
    	citizen.SetCitizenDoB(dobDate.format(formatter).toString());
    	
    	return citizen;
    }
    
    @Test
    @Order(2)
    public void postCorrectCitizen() throws Exception{
    	Citizen citizen = createCitizen("AX123450");
    	given().contentType("application/json").body(citizen).when().post("/api/citizens").then().assertThat().statusCode(201);
    }
    
    @Test
    @Order(3)
    public void getExistingCitizen() throws Exception{
    	given().accept("application/json").get("/api/citizens/AX123450").then().
    	assertThat().statusCode(200).and().body("tautotita", equalTo("AX123450"));
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "AX123455", "AB123123", "ZZ987654" })
    @Order(4)
    void addCorrectCitizen(String tautotita) {
    	Citizen citizen = createCitizen(tautotita);
    	given().contentType("application/json").body(citizen).when().post("/api/citizens").then().assertThat().statusCode(201);
    }
    
    @Test
    @Order(5)
    public void getExistingCitizens() throws Exception{
    	List<Citizen> citizen = given().accept("application/json").get("/appi/citizens").then().assertThat().statusCode(200).
    			extract().as(new TypeRef<List<Citizen>>(){});
    	assertThat(citizen, hasSize(3));
    	assertThat(citizen.get(0).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654"),equalTo("ZZ987654")));
    	assertThat(citizen.get(1).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654"),equalTo("ZZ987654")));
    	assertThat(citizen.get(2).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654"),equalTo("ZZ987654")));
    }
}
