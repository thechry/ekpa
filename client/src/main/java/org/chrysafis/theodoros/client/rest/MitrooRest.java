package org.chrysafis.theodoros.client.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.chrysafis.theodoros.client.conf.ImmutableApiConfiguration;
import org.chrysafis.theodoros.client.helpers.Helper;
import org.chrysafis.theodoros.model.Citizen;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MitrooRest 
{
	private final ImmutableApiConfiguration details;
	
	private RestClient client;
	
	
	public MitrooRest(ImmutableApiConfiguration details){
		this.details = details;
		initClient();
	}
	
	private void initClient() {
		String url = "http://" + details.getHost() + ":" + details.getPort() + "/" + details.getApi();
		client = RestClient.create(url);
	}
	
	public void getCitizens(MediaType type) {
		client.get()
		.accept(type)
		.exchange(
			(request, response) -> {
				if (response.getStatusCode().is4xxClientError())
					System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
						" and message: " + response.bodyTo(String.class));
				else if (response.getStatusCode().is5xxServerError())
					System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
						" and message: " + response.bodyTo(String.class));
				else if (response.getStatusCode().is2xxSuccessful()) {
					System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
					System.out.println("The list of citizens is: " + response.bodyTo(String.class));
				}
				return null;	
			}
		);
	}
	
	public void getCitizensWithParams(String name, String surname, String gender, String dob, MediaType type) {
		String queryPart = "";
		if (name != null && !name.isBlank()) queryPart += "name=" + name;
		if (surname != null && !surname.isBlank()) queryPart += "surname=" + surname;
		if (gender != null && !gender.isBlank()) queryPart += "gender=" + gender;
		if (dob != null && !dob.isBlank()) queryPart += "dob=" + dob;
		if (!queryPart.isBlank()) queryPart = "?" + queryPart;
		client.get()
			.uri(queryPart)
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The list of citizens is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	public void getCitizen(String tautotita, MediaType type) {
		client.get()
			.uri("/{id}",tautotita)
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The citizen with tautotita: " + tautotita + " is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
		
	private Citizen createCitizen(String _tautotita)
	{			
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Helper help = new Helper();
		
		String atautotita = null;
		String aname = null;
		String asurname = null;
		String agender = null;
		String adob = null;
		int aafm = 0;
		String aaddress = null;
		
		atautotita = help.CheckInput(_tautotita);
		
		
		System.out.println("Give citizen name");		 
		try {
			aname = help.CheckInput(reader.readLine());		
		} catch (IOException e) {
			e.printStackTrace();
		}
         
		System.out.println("Give citizen surname");
		try {
			asurname = help.CheckInput(reader.readLine());		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		System.out.println("Give citizen gender");
		try {
			agender = help.CheckInput(reader.readLine());		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Give citizen birth date");
		try {
			adob = help.CheckInput(reader.readLine());		
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Give citizen afm");
		try {			
			String anafm = reader.readLine();	
			
			if(anafm.length() == 9)
            {
               aafm = Integer.parseInt(anafm);
            }
			else
			{
				throw new IllegalArgumentException("Error in afm");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Give citizen address");
		try {
			aaddress = reader.readLine();		
		} catch (IOException e) {
			e.printStackTrace();
		}		
    	
		Citizen.CitizenBuilder cb = new Citizen.CitizenBuilder(atautotita, aname, asurname, agender, adob, aafm, aaddress);
    	
    	Citizen citizen = new Citizen(cb);
    	
    	return citizen;
    }
	
	public void addCitizen(String tautotita, MediaType type) {
		Citizen citizen = createCitizen(tautotita);
		client.post()
		.contentType(type)
		.body(citizen)
		.exchange(
			(request, response) -> {
				if (response.getStatusCode().is4xxClientError()) {
					System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is5xxServerError()) {
					System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is2xxSuccessful()) {
					System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
					System.out.println("The citizen with tautotita: " + tautotita + " was created successfully");
					System.out.println("The created citizen's URL is: " + response.getHeaders().get("Location"));
				}
				return null;
			}
		);
	}
	
	public void updateCitizen(String tautotita, MediaType type) {
		Citizen citizen = createCitizen(tautotita);
		citizen.SetCitizenName("Gians");
		client.put()
		.uri("/{id}",tautotita)
		.contentType(type)
		.body(citizen)
		.exchange(
			(request, response) -> {
				if (response.getStatusCode().is4xxClientError()) {
					System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is5xxServerError()) {
					System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is2xxSuccessful()) {
					System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
					System.out.println("The citizen with tautotita: " + tautotita + " has been successfully updated");
				}
				return null;
			}
		);
	}
	
	public void deleteCitizen(String tautotita) {
		client.delete()
		.uri("/{id}",tautotita)
		.exchange(
			(request, response) -> {
				if (response.getStatusCode().is4xxClientError()) {
					System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is5xxServerError()) {
					System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
				}
				else if (response.getStatusCode().is2xxSuccessful()) {
					System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
					System.out.println("The citizen with tautotita: " + tautotita + " has been successfully deleted");
				}
				return null;
			}
		);
	}
	
	
}




































