
/* Stephen Swick
 * Netid: sswick2
 * Answer java class
 * Class holds the Answer object which will be a 
 * choice the user can choose when taking the exam
 * consists of a string that is weighted with a
 * selected value and unselected value.
 */

import java.util.*;
import java.io.*;
/* Answer is now an abstract class, so a basic answer
 * will just consist of its string and each child
 * class must be able to calculate its own credit
 * and print
 */

public abstract class Answer {
	
	protected Answer() {

	}
	
	protected Answer(Scanner file) {
		
	}
	//Method to print out an answer onto the screen
	//Question class will now handle printing the letters infront of each answer choice
	//so just print out the string
	abstract void print();
	
	//default getCredit method just compares the current answer to the right answer
	//if theyre equal return full credit, otherwise it is wrong
	abstract double getCredit(Answer rightAnswer);
	
	abstract void save(PrintWriter file);
}

/* Abstract class for the Multiple choice answer
 * it consists of a string from Answer and a boolean to determine if the answer has been selected
 * has its own print method, and setter for setting the boolean
 */
abstract class MCAnswer extends Answer {
	//protected variable that will be used in the children class
	protected boolean isSelected;
	protected String ans;
	protected double creditIfSelected;
	
	protected MCAnswer(String answer, double credit) {
		this.ans = answer;
		this.isSelected = false;
		this.creditIfSelected = credit;
	}
	
	protected MCAnswer(Scanner file) {
		this.ans = file.nextLine();
	}
	
	//overrides Answers print, but it is just the same thing
	public void print() {
		if(this.isSelected == true) {
			System.out.println(this.ans + " *** ");
		}
		else {
			System.out.println(this.ans);
		}
	}
	
	//setter for isSelected
	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}
	
	public double getCredit(Answer rightAnswer) {
		if(rightAnswer instanceof MCAnswer) {
			MCAnswer tmp = (MCAnswer) rightAnswer;
			if(this.ans.equals(tmp.ans)) {
				return this.creditIfSelected;
			}
			else return 0.0;
		}
		else return 0.0;
	}
	
	public void save(PrintWriter file) {
		file.println(this.ans);
		file.println();
	}
	
	public String getString() {
		return this.ans;
	}
}

/* Multiple choice single answer class, which  is a child of the MCAnswer
 * Allows the exam maker to make MCSAAnswer object, which just consists
 * of a string and a boolean for determining selection.
 * Calculates if the answer is right or wrong with getCredit method
 */
class MCSAAnswer extends MCAnswer{
	
	//constructor for MCSAAnswer initializes all of its values
	MCSAAnswer(String text, double credit){
		super(text, credit);
	}
	
	MCSAAnswer(Scanner file){
		super(file);
	}
}


/* Multiple Choice Multiple Answer class, extends MCAnswer
 * Same as MCSAAnswer except its getCredit calculation is slightly different
 * Since we have to account for multiple answers being right a different
 * calculation is made for this.
 */
class MCMAAnswer extends MCAnswer{
	//constructor for MCMAAnswer
	MCMAAnswer(String text, double credit){
		super(text, credit);
	}
	
	
	MCMAAnswer(Scanner file){
		super(file);
	}
}

/* SAAnswer class, which contains the short answer object
 * A short answer is defined by being just a string that holds the answer
 * A short answer is compared with the right answer by making it case insensitive
 * TODO: Implement synonym handler
 */
class SAAnswer extends Answer{
	protected String ans;
	
	//constructor is just the string holding the answer choice 
	SAAnswer(String answer){
		this.ans = answer;
	}
	
	SAAnswer(Scanner file){
		this.ans = file.nextLine();
	}
	//print out the string for the answer
	public void print() {
		System.out.println(this.ans);
	}
	
	//function to award credit for the student
	//makes the cancer case insensitive 
	public double getCredit(Answer rightAnswer) { 
		if(rightAnswer instanceof SAAnswer) { //check for cast
			SAAnswer rightAns = (SAAnswer) rightAnswer; //type cast
			if(this.ans.toUpperCase().equals(rightAns.ans.toUpperCase())) { //if theyre the same word award credit
				return 1.0;
			}
			else {
				return 0.0;
			}
		}
		else {
			return 0.0;
		}
	}
	
	public void save(PrintWriter file) {
		file.println("SAAnswer");
		file.println(this.ans);
		file.println();
	}
	
	public String getString() {
		return this.ans;
	}
}

class NumAnswer extends Answer{
	private double ans;
	
	NumAnswer(){ //default constructor does nothing
		
	}
	
	NumAnswer(double a){ //constructor where it sets the double the answer holds 
		this.ans = a;
	}
	
	NumAnswer(Scanner file){
		this.ans = file.nextDouble();
	}
	//print their answer
	public void print() {
		System.out.println(this.ans);
	}
	
	//Basic credit function gives credit, and your answer may be right if youre just 0.5 within range
	//just checks if the answer is right or wrong
	public double getCredit(Answer rightAnswer) {
		if(rightAnswer instanceof NumAnswer) {
			NumAnswer rightNum = (NumAnswer)rightAnswer;
			if(Math.abs(this.ans - rightNum.ans) <= 0.5) {
				return 1.0;
			}
			else if(Math.abs(this.ans - rightNum.ans) <= 1.0) {
				return 0.8;
			}
			else if(Math.abs(this.ans - rightNum.ans) <= 1.5) {
				return 0.7;
			}
			else if(Math.abs(this.ans - rightNum.ans) <= 2.0) {
				return 0.5;
			}
			else {
				return 0.0;
			}
		}
		else { //error
			return 0.0;
		}
	}
	
	//setter for if they called the constructor that doesnt do anything
	public void setAnswer(double a) {
		this.ans = a;
	}
	
	public void save(PrintWriter file) {
		file.println("NumQuestion");
		file.println(this.ans);
		file.println();
	}
	
	public double getString() {
		return this.ans;
	}
}
