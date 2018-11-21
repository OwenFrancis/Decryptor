package com.ojf763.decryptor.model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.NumberFormatException;

public class FirstDecryptionMethod{
	
	
	char[] alphabet = new char[26];
	int[] numberOfAppearances = new int[26];
	char[] substitutedAlphabet = new char[26];  //store the letters after shift that correspond to letters before shift in order.
	char[] frequencyLetters = new char[26];
	float[] frequencyNumbers = new float[26];
	
	
	
	public FirstDecryptionMethod(){
		
		try{
			FileReader frequencies = new FileReader("chosenFrequencies.txt");
			FileReader cipher = new FileReader("chosenCipher.txt");
			FileWriter output = new FileWriter("output.txt");
			FileReader cipher2 = new FileReader("chosenCipher.txt");
			
			alphabet[0] = 'a';
			alphabet[1] = 'b';
			alphabet[2] = 'c';
			alphabet[3] = 'd';
			alphabet[4] = 'e';
			alphabet[5] = 'f';
			alphabet[6] = 'g';
			alphabet[7] = 'h';
			alphabet[8] = 'i';
			alphabet[9] = 'j';
			alphabet[10] = 'k';
			alphabet[11] = 'l';
			alphabet[12] = 'm';
			alphabet[13] = 'n';
			alphabet[14] = 'o';
			alphabet[15] = 'p';
			alphabet[16] = 'q';
			alphabet[17] = 'r';
			alphabet[18] = 's';
			alphabet[19] = 't';
			alphabet[20] = 'u';
			alphabet[21] = 'v';
			alphabet[22] = 'w';
			alphabet[23] = 'x';
			alphabet[24] = 'y';
			alphabet[25] = 'z';
			
			for(int i = 0; i < 26; i++){
				numberOfAppearances[i] = 0;
			}
			for(int i = 0; i < 26; i++){
				frequencyNumbers[i] = 0.0f;
			}
			
			
			int c;
			while((c = cipher.read()) != -1){
				//This goes through all the characters in the cipher text file and stores how many times each letter appears in an array
				char a = (char) c;
				if(Character.isLetter(a) == true){
					a = Character.toLowerCase(a);
					for(int i = 0; i < 26; i++){
						if(a == alphabet[i]){
							numberOfAppearances[i] += 1;
						}
						
					}
					
				}
			}
			
			
			
			int d;
			String frequencyString = "";
			int nextEmpty = 0;
			boolean numberNeeded = false;
			while((d = frequencies.read()) != -1){  //this loop goes through the frequency file and stores the frequencies in an array
				char b = (char) d;
				if(Character.isLetter(b) == true){
					frequencyLetters[nextEmpty] = b;
					nextEmpty += 1;
					if(numberNeeded == true){
						frequencyString = frequencyString.replaceAll("\\s+",""); 
						//The line above was taken from stack overflow: https://stackoverflow.com/questions/15633228/how-to-remove-all-white-spaces-in-java
						//It removes all whitespace, such as spaces, from a string. This is neccessary to successfully parse the string to a float
						float frequencyFloat = Float.parseFloat(frequencyString);
						frequencyNumbers[nextEmpty-2] = frequencyFloat;
						frequencyString = "";
					}
					numberNeeded = true;
				} else if(numberNeeded == true) {
					frequencyString += b;
				}
				
				
				
			}
			frequencyString = frequencyString.replaceAll("\\s+","");
			float frequencyFloat = Float.parseFloat(frequencyString);
			frequencyNumbers[25] = frequencyFloat;
			
			
			
			
			
			
			for(int i = 0; i < 26; i++){ 
			//This loop contains loops which find the position of the largest value in the number of appearences and frequencies arrays
			//It then finds the letters corresponding to those largest numbers, and stores the letter from alphabet in substitutedAlphabet,
			//in the same position that the letter corresponding to the frequency is in the alphabet array.
				int maxValue = 0;
				int maxLocation1 = 0;
				float maxFloat = 0;
				int maxLocation2 = 0;
				
				for(int j = 0; j < 26; j++){
					int currentValue = numberOfAppearances[j];
					if(currentValue >= maxValue){
						maxValue = numberOfAppearances[j];
						maxLocation1 = j;
					}
				}
				
				
				
				numberOfAppearances[maxLocation1] = -1;
				for(int j = 0; j < 26; j++){
					if(frequencyNumbers[j] >= maxFloat){
						maxFloat = frequencyNumbers[j];
						maxLocation2 = j;
					}
				}
				
				
				frequencyNumbers[maxLocation2] = -1;
				for(int j = 0; j < 26; j++){
					if(frequencyLetters[maxLocation2] == alphabet[j]){
						substitutedAlphabet[j] = alphabet[maxLocation1];
					}
				}
			}
			
			
			int e;
			while((e = cipher2.read()) != -1){
				//This loop decrypts the letters of the cypher and writes them to the output file
				char currentCharacter = (char) e;
				if(Character.isLetter(currentCharacter) == false){
					output.write(e);
				} else {
					currentCharacter = Character.toLowerCase(currentCharacter);
					for(int i = 0; i < 26; i++){
						if(currentCharacter == alphabet[i]){
							char substitutedCharacter = substitutedAlphabet[i];
							int substitutedASCII = (int) substitutedCharacter; //converting back to ASCII
							output.write(substitutedASCII);
						}
						
					}
					
				}
			}
			
			
			frequencies.close();
			cipher.close();
			output.close();
			cipher2.close();
		}
		catch (FileNotFoundException f){
			System.out.println("File not found");
		}
		catch (IOException f){
			System.out.println("I/O Error");
		}
		catch (NumberFormatException f){
			System.out.println("There is a character in the frequencies file that is not a letter, digit or decimal point");
		}
		
		
		
	}
	
	
	
	
	
	
	
}