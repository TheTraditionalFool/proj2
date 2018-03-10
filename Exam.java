/*Stephen Swick
 * Netid:sswick2
 * Exam class
 * Holds the Exam object, which will be a collection of Question objects
 * Similar to the Question class, the questions for the Exam will be held
 * in an ArrayList, so the Exam maker is allowed to create as many Questions
 * for the exam as he desires. 
 */
import java.util.*;
import java.io.*;

public class Exam {
	//private data types
	private ArrayList<Question> questions; //Array List for question
	private String text; //String for exam header
	
	//Constructor for Exam
	public Exam(String txt) {
		this.text = txt; //set the header 
		this.questions = new ArrayList<Question>(5); //create ArrayList for questions default set to 5
	}
	
	public Exam(Scanner file) {
		this.text = file.nextLine();
		this.questions = new ArrayList<Question>();
		while(file.hasNext()) {
			String qType = file.nextLine();
			if(qType.equals("SAQuestion")) {
				this.questions.add(new SAQuestion(file));
			}
			else if(qType.equals("MCSAQuestion")) {
				this.questions.add(new MCSAQuestion(file));
			}
			else if(qType.equals("MCMAQuestion")) {
				this.questions.add(new MCMAQuestion(file));
			}
			else if(qType.equals("NumQuestion")) {
				this.questions.add(new NumQuestion(file));
			}
		}
	}
	//Method to add a question to the exam
	public void AddQuestion(Question q) {
		this.questions.add(q); //add the question to the ArrayList
	}
	
	//Method to print the whole exam
	//Will print the title of the exm first, with some formatting
	//then will just loop through each question and utilize the Question object's method of print for each question
	public void print() {
		//print title
		System.out.print("------------ " + this.text + " ------------\n\n");
		//loop through each question
		for(int i = 0; i < questions.size(); i++) {
			System.out.print(i+1 + ". ");
			questions.get(i).print(); //get question at index i and print in index i+1, do this to start questions
			//at 1 and increment so on, i.e. question at index 0 is question 1 on the screen.
			System.out.print("\n"); //new line for format
		}
	}
	
	//Method to reorder the questions on the exam
	//Will reorder the questions on the exam, and the answers for each question
	public void reorderQuestions() {
		//just use Collections.shuffle to reorder
		Collections.shuffle(questions);
	}
	
	public void reorderMCAnswers(int position) {
		if(position < 0 || position >= this.questions.size()) { //error
			return;
		}
		if(this.questions.get(position) instanceof MCQuestion) { //make sure it is of type MCQuestion
			MCQuestion temp =  (MCQuestion)this.questions.get(position);
			temp.reorderAnswers(); //shuffle
			this.questions.set(position, temp);
		}
		else { //error trying to reorder a non MC question
			return;
		}
	}
	
	//Method to calculate the test takers score and return it
	//will call the helper method to calculate the score for the exam
	public double getValue() {
		double total = 0;
		for(int i=0; i<this.questions.size(); i++) {
			total += this.questions.get(i).getValue();
		}
		return total;
	}
	
	//function to get answer from student 
	//calls the question getAnswerFrom student
	public void getAnswerFromStudent(int position) {
		if(position < 0 || position >= this.questions.size()) {
			return; //error
		}
		this.questions.get(position).getAnswerFromStudent(); //get the answer from the student	
	}
	
	//Function to report the students score
	//gives the total and the what value they got for each question
	public void reportQuestionValues() {
		for(int i = 0; i < this.questions.size(); i++) {
			this.questions.get(i).print();
			System.out.println("Points received for this question: " + questions.get(i).getValue() + "\n");
		}
		
		System.out.println("Total score for the exam was: " + this.getValue());
	}
	
	//Function I wrote to enter exam taking mode
	//The test taker is able to answer each question individually and scroll through each question
	public void takeExam() {
		Scanner scan = ScannerFactory.getKeyboardScanner(); //create scanner for input
		
		int i = 0;
		int size = this.questions.size();
		
		//enter test taking loop
		while(true) {
			System.out.print(i+1 + ". ");
			this.questions.get(i).print(); //print current question
			System.out.println("Type 'next' to go to the next question, 'prev' to go to the previous, any other input "
					+ "to reanswer current question.  TYPE '1' to ANSWER!"); //instructions
			System.out.println("Type 'exit' to finish and score your exam."); //more instructions
			String input = scan.nextLine(); //scan user input, just scan the whole line and parse it with if statements
			if(input.equals("next")) { //check if the user wants to go to next question
				i++; //increment i
				if(i >= size) { //check if we go over the bounds
					i--;
					System.out.println("\nNo more questions left, type 'exit' if you're done\n");
					continue;
				}
				System.out.println();
				continue; //go back to start
			}
			else if(input.equals("prev")) { //check if user wants to go to previous question
				i--; //decrement
				if(i < 0) { //check if we go over the bounds
					i++;
					System.out.println("\nAlready at the beginning of the exam\n");
					continue;
				}
				System.out.println();
				continue;
			}
			else if(input.equals("exit")) { //check if the user is done
				System.out.println("\n\n\n");
				System.out.println("**************DONE**************\n\n");
				break; //break out of loop
			}
			else if(input.equals("1")) {
				this.questions.get(i).getAnswerFromStudent(); //get the answer for question
				System.out.println();
			}
			else {
				System.out.println("Not valid input\n");
				continue;
			}

		}
	}
	
	public void save(PrintWriter file) {
		file.println(this.text + "\n");
		for(int i = 0; i < this.questions.size(); i++) {
			this.questions.get(i).save(file);
		}
	}
	
	public void saveStudentAnswers(PrintWriter file) {
		for(int i = 0; i < this.questions.size(); i++) {
			this.questions.get(i).saveStudentAnswers(file);
		}
	}
	
	public void restoreStudentAnswers(Scanner file) {
		int i = 0;
		int length = this.questions.size();
		while(file.hasNext()) {
			String aType = file.nextLine();
			if(aType.equals("SAAnswer")) {
				if(this.questions.get(i) instanceof SAQuestion) {
					this.questions.get(i).restoreStudentAnswers(file);
					this.questions.get(i).setAnswered(true);
				}
				i++;
				if(i >= length) {
					return;
				}
			}
			else if(aType.equals("MCSAAnswer")) {
				if(this.questions.get(i) instanceof MCSAQuestion) {
					this.questions.get(i).restoreStudentAnswers(file);
					this.questions.get(i).setAnswered(true);
				}
				i++;
				if(i >= length) {
					return;
				}
			}
			else if(aType.equals("MCMAAnswer")) {
				if(this.questions.get(i) instanceof MCMAQuestion) {
					this.questions.get(i).restoreStudentAnswers(file);
					this.questions.get(i).setAnswered(true);
				}
				i++;
				if(i >= length) {
					return;
				}
			}
			else if(aType.equals("NumAnswer")) {
				if(this.questions.get(i) instanceof NumQuestion) {
					this.questions.get(i).restoreStudentAnswers(file);
					this.questions.get(i).setAnswered(true);
				}
				i++;
				if(i >= length) {
					return;
				}
			}
		}
	}
}
