package org.chrysafis.theodoros.service.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import org.chrysafis.theodoros.service.model.Citizen;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

/**
 * Tests the Web application, by checking that the index page returns a code 200.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Deleting Citizen Testing")
public class ThirdIT  implements TestLifecycleLogger
{
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	@Test
    @Order(1)
    public void deleteWrongCitizen() throws Exception{
    	given().delete("/api/citizens/AX123400").then().assertThat().statusCode(404);
    }
    
    @Test
    @Order(4)
    public void deleteCorrectCitizen() throws Exception{
    	given().delete("/api/citizens/AX123450").then().assertThat().statusCode(204);
    }
    
    @Test
    @Order(3)
    public void getNonExistingCitizen() throws Exception{
    	given().accept("application/json").get("/api/citizens/AX123490").then().assertThat().statusCode(404);
    }
    
    @Test
    @Order(2)
    public void getExistingCitizens() throws Exception{
    	List<Citizen> citizen = given().accept("application/json").get("/api/citizens").then().assertThat().
    			statusCode(200).extract().as(new TypeRef<List<Citizen>>(){});
    	assertThat(citizen, hasSize(3));
    	assertThat(citizen.get(0).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654")));
    	assertThat(citizen.get(1).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654")));
    	assertThat(citizen.get(2).GetTautotita(),anyOf(equalTo("AX123450"),equalTo("AB123123"),equalTo("ZZ987654")));
    }
}
