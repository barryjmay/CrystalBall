package com.barrymay.crystalball;

import java.util.Random;

public class CrystalBall {
	//This code is the blueprint for the crystal ball object
	//An object has 2 main components:
	//Member variables (properties about the object)
	//Methods (abilities: things the object can do)
	
	public String[] mAnswers = {
			"It is certain",
			"It is decidely so",
			"All signs say YES",
			"The stars are not aligned",
			"My reply is NO",
			"It is doubtful",
			"Better not tell you now",
			"Concentrate and ask again",
			"Unable to answer now",
			"It is hard to say"};
	
	public String getAnAnswer(){
		
		String answer = null;
		
		Random randomGenerator = new Random();//Construct a new Random number generator
		int randomNumber = randomGenerator.nextInt(mAnswers.length);//Declare an int variable named randomNumber & use the randomGenerator variable and its nextInt() method to get a random number.
				
		answer = mAnswers[randomNumber]; //give me the value at index randomNumber in the answers array & assign this to answer
	
		return answer;
	}
	
}	
