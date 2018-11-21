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

public class SecondDecryptionMethod{
	
	char[][] alphabet = new char[26][2];
	float[] numberOfAppearances = new float[26];
	char[] frequencyLetters = new char[26];
	float[] frequencyNumbers = new float[26];
	
	
	public SecondDecryptionMethod(){
		
		try{
			FileReader frequencies = new FileReader("chosenFrequencies.txt");
			FileReader cipher = new FileReader("chosenCipher.txt");
			FileWriter output = new FileWriter("output.txt");
			FileReader cipher2 = new FileReader("chosenCipher.txt");
			
			alphabet[0][0] = 'a';
			alphabet[1][0] = 'b';
			alphabet[2][0] = 'c';
			alphabet[3][0] = 'd';
			alphabet[4][0] = 'e';
			alphabet[5][0] = 'f';
			alphabet[6][0] = 'g';
			alphabet[7][0] = 'h';
			alphabet[8][0] = 'i';
			alphabet[9][0] = 'j';
			alphabet[10][0] = 'k';
			alphabet[11][0] = 'l';
			alphabet[12][0] = 'm';
			alphabet[13][0] = 'n';
			alphabet[14][0] = 'o';
			alphabet[15][0] = 'p';
			alphabet[16][0] = 'q';
			alphabet[17][0] = 'r';
			alphabet[18][0] = 's';
			alphabet[19][0] = 't';
			alphabet[20][0] = 'u';
			alphabet[21][0] = 'v';
			alphabet[22][0] = 'w';
			alphabet[23][0] = 'x';
			alphabet[24][0] = 'y';
			alphabet[25][0] = 'z';
			
			for(int i = 0; i < 26; i++){
				alphabet[i][1] = ' ';
			}
			for(int i = 0; i < 26; i++){
				numberOfAppearances[i] = 0.0f;
			}
			for(int i = 0; i < 26; i++){
				frequencyNumbers[i] = 0.0f;
			}
			
			
			int c;
			while((c = cipher.read()) != -1){
				//This goes through all the characters in the cipher text file
				char a = (char) c;
				if(Character.isLetter(a) == true){
					a = Character.toLowerCase(a);
					for(int i = 0; i < 26; i++){
						if(a == alphabet[i][0]){
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
						frequencyNumbers[nextEmpty-1] = frequencyFloat;
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
			
			
			
			//for loop where it compares the difference between the current closest and the next values in the frequencyNumbers array.
			//change to new item if difference is smallers
			
			
			int totalLetters = 0;
			for(int i = 0; i < 26; i++){
				totalLetters += numberOfAppearances[i];
			}
			
			for(int i = 0; i < 26; i++){
				numberOfAppearances[i] = ( (numberOfAppearances[i] / totalLetters) * 100);
				
			}
			
			
			for(int i = 0; i < 26; i++){
				
				float difference = 100.0f; //maximum difference is 100 because maximum value of both frequencies is 100 - if they have 100% frequency
				int smallestDifferenceLocation = -1;
				
				
				for(int j = 0; j < 26; j++){
					float newDifference = frequencyNumbers[j] - numberOfAppearances[i];
					if(newDifference < 0.0f){
						newDifference = newDifference * -1;
					}
					
					if(newDifference <= difference){
						difference = newDifference;
						smallestDifferenceLocation = j;
					}
					
					
				}
				
				alphabet[i][1] = frequencyLetters[smallestDifferenceLocation];
				
				
			}
			
			int e;
			while((e = cipher2.read()) != -1){
				//This loop decrypts the letters of the cypher and writes them to the output file
				char currentCharacter = (char) e;
				if(Character.isLetter(currentCharacter) == false){
					output.write(e);
				} else {
					currentCharacter = Character.toLowerCase(currentCharacter);
					for(int j = 0; j < 26; j++){
						if(currentCharacter == alphabet[j][0]){
							char substitutedCharacter = alphabet[j][1];
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