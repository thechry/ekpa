package org.chrysafis.theodoros.client.helpers;

import static org.assertj.core.api.Assertions.setMaxLengthForSingleLineDescription;

public class Helper 
{
	public String CheckInput(String input)
	{
		if(input == null || input.isEmpty() ||  input.trim().equals(""))
		{
			throw new IllegalArgumentException("Error empty or null input");
		}
		
		return input;
	}
	

}
