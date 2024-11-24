package org.chrysafis.theodoros.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.chrysafis.theodoros.client.conf.ImmutableApiConfiguration;
import org.chrysafis.theodoros.client.rest.MitrooRest;
import org.chrysafis.theodoros.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;

@SpringBootApplication
@EnableConfigurationProperties(ImmutableApiConfiguration.class)
public class App  implements CommandLineRunner
{
	
	@Autowired
	private MitrooRest mrc;
	
    public static void main(String[] args) 
    {
    	SpringApplication.run(App.class, args);
    	System.out.println("Menu");
    }

	@Override
	public void run(String... args) throws Exception 
	{
		MediaType xml = MediaType.APPLICATION_XML;
    	MediaType json = MediaType.APPLICATION_JSON;
    	
    	String answer = "";
    	String tautotis = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Citizen citizen = new Citizen();
        
        do{
            System.out.println("Menu");
            System.out.println("Select option (1,2,3,4,5) or 'Χ' to Exit");
            System.out.println("1. Insert Mitroo Record");
            System.out.println("2. Update Mitroo Record");
            System.out.println("3. Delete Mitroo Record");
            System.out.println("4. Search Mitroo Record");
            System.out.println("5. Show Mitroo Record");
            System.out.println();
            
            try 
            {
                answer = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switch(answer)
            {
                case "1":
                    System.out.println("Give Citizen's Tautotita to add");
                    System.out.println();
                    
                    try 
                    {
                    	tautotis = reader.readLine();
                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    mrc.addCitizen(tautotis, json);                   
                break;
                case "2":
                    System.out.println("Give citizen's Tautotita to update");
                    System.out.println();
                    
                    try 
                    {
                    	tautotis = reader.readLine();                    	
                    	
                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    // find citizen in db with this tautotita then
                    mrc.updateCitizen(tautotis, json);
                break;
                case "3":
                    System.out.println("Give Citizen's Tautotita to Delete");
                    System.out.println();
                    
                    try 
                    {
                    	tautotis = reader.readLine();                    	
                    	
                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    mrc.deleteCitizen(tautotis);
                break;
                case "4":
                    String param = null;
                    String name = null;
                    String surname = null;
                    String gender =null;
                    String dob = null;
                    
                    do {
                    	// Refactor
                    	System.out.println("Give Citizen's Parameteres for Search");
                        System.out.println("1. Search by name");
                        System.out.println("2. Search by surname");
                        System.out.println("3. Search by gender");
                        System.out.println("4. Search by dob");
                        System.out.println("X to exit");
                        System.out.println();
                        
                        try 
                        {
                        	param = reader.readLine();
                        } catch (IOException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        switch(param)
                        {
                        case "1":
                            mrc.getCitizensWithParams(name, null, null, null, json);
                        	break;
                        case "2":
                            mrc.getCitizensWithParams(null, surname, null, null, json);
                        	break;
                        case "3":
                            mrc.getCitizensWithParams(null, null, gender, null, json);
                        	break;
                        case "4":
                            mrc.getCitizensWithParams(null, null, null, dob, json);
                        	break;
                        }
                    	
                    }while(!(param == "X"));
                                       	
                    	
                    
                    
                break;
                case "5":
                    System.out.println("Give Citizen's Tautotita to Show");
                    System.out.println();
                    System.out.println("Δώστε ταυτότητα πολίτη");
                    try 
                    {
                        String tautotita = reader.readLine();                        
                                               
                    }
                    catch(IOException ex) 
                    {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    mrc.getCitizen(tautotis, json);
                    break;
                default:
                    System.out.println(" Επιλέξτε 1, 2, 3, 4, 5, X");
                    System.out.println();
                    break;
            }
        }
        while(!"X".equals(answer));
		
	}
}
