/* Stephen Swick
 * Netid: sswick2
 * Question class
 * This class will hold the Question object which will consist of a question for the user
 * and a collection of answer objects.  I decided to utilize the ArrayList so that when 
 * the user creates an exam, the user can add more than 5 answers per question.
 */
import java.util.*;

/* Abstract question class, will consist of a String for
 * the question text and 2 Answers, one for student selection
 * and one for the correct Answer, and finally a double to score
 * the question.  Comes with a constructor child classes can
 * call and a list of methods each child class must have.
 */
public abstract class Question{
	//protected variables that will be used within child classes
	protected String text;
	protected Answer rightAnswer;
	protected Answer studentAnswer;
	protected double maxValue;
	protected boolean isAnswered;
	//Basic constructor for a Question class
	protected Question(String txt, double mxValue) {
		this.text = txt;
		this.maxValue = mxValue;
		this.isAnswered = false;
	}
	
	protected Question(Scanner file) {
		this.maxValue = Double.parseDouble(file.nextLine());
		this.text = file.nextLine();
		this.isAnswered = false;
	}
	//list of basic methods that each child class should have
	
	abstract void print(); //print the question
	//set the right question
	public void setRightAnswer(Answer ans) {
		this.rightAnswer = ans;
	}
	abstract Answer getNewAnswer();
	//Scan students answer
	abstract void getAnswerFromStudent();
	//score the question
	abstract double getValue();
}

/* Abstract multiple choice question class, an extension of Question
 * Consists of an arrayList of answers that will hold all the possible choices
 * for the question.  Define print, addAnswer, and reorderAnswers here for the
 * children class to call on.
 */
abstract class MCQuestion extends Question{
	//protected variable of ArrayList of answers
	protected ArrayList<MCAnswer> answers;
	//compiler was yelling at me to define a constructor here
	//so just call Question's constructor
	protected MCQuestion(String txt, double mxValue) {
		super(txt,mxValue);
		this.answers = new ArrayList<MCAnswer>();
	}
	
	protected MCQuestion(Scanner file) {
		super(file);
		this.answers = new ArrayList<MCAnswer>();
	}
	//print method, Question will now handle printing the "a. , b., c., ... etc"
	public void print() {
		System.out.println(this.text);
		for(int i = 0; i < answers.size(); i++) {
			System.out.print("  " +(char)(i+'a')+"."); //print the letter choice
			this.answers.get(i).print(); //print the answer next
		}
	}
	
	//simple method to add an answer to the ArrayList
	public void addAnswer(MCAnswer ans) {
		this.answers.add(ans);
	}
	
	//simple method to randomize the answers
	public void reorderAnswers() {
		Collections.shuffle(this.answers);
	}
	
	public double getValue(MCAnswer studAns) {
		double credit = 0;
		for(int i = 0; i < this.answers.size(); i++) {
			credit += answers.get(i).getCredit(studAns);
		}
		return this.maxValue * credit;
	}
}


/* Multiple choice single answer question class, an extension of the MCQuestion class
 * Question class where multiple answers will be printed out and the user has to select
 * one answer. 
 */
class MCSAQuestion extends MCQuestion{
	//constructor for MCSAQuestion, call the parent class constructor
	public MCSAQuestion(String text, double maxValue) {
		super(text, maxValue);
	}
	
	public MCSAQuestion(Scanner file) {
		super(file);
		int numAs = Integer.parseInt(file.nextLine());
		for(int i = 0; i < numAs; i++) {
			double val = file.nextDouble();
			String ans = file.nextLine();
			answers.add(new MCSAAnswer(ans,val));
		}
	}
	
	//get a new MCSAAnswer and return it
	public MCSAAnswer getNewAnswer() {
		Scanner scan = ScannerFactory.getKeyboardScanner(); //set up scanner to read system.in
		System.out.println("Please enter an answer for this Question");
		String ans = scan.nextLine();
		System.out.println("Please enter a double for credit if answer is selected");
		while(!(scan.hasNextDouble())) {
			scan.nextLine();
		}
		double credit = scan.nextDouble();
		return  new MCSAAnswer(ans, credit);
	}
	
	public MCSAAnswer getNewAnswer(String text) {
		return new MCSAAnswer(text, 0.0);
	}
	
	public MCSAAnswer getNewAnswer(String text, double creditIfSelected) {
		return new MCSAAnswer(text,creditIfSelected);
	}
	
	//get the Students answer choice
	public void getAnswerFromStudent() {
		if(this.isAnswered == true) {
			if(this.studentAnswer instanceof MCSAAnswer) {
					MCSAAnswer tmp = (MCSAAnswer) this.studentAnswer;
					tmp.setSelected(false);
					this.studentAnswer = tmp;
			}
		}
		//Scanner scan = new Scanner(System.in);
		Scanner scan = ScannerFactory.getKeyboardScanner();
		char choice = scan.nextLine().trim().toLowerCase().charAt(0); //scan their input, get the first char 
		int index = (int)choice - 97; //normalize the choice to index form
		while(index < 0 || index >= this.answers.size() ) {
			System.out.println("Not a valid choice try again.\n");
			choice = scan.nextLine().trim().toLowerCase().charAt(0); //scan their input, get the first char 
			index = (int)choice - 97; //normalize the choice to index form	
		}
		this.isAnswered = true;
		this.studentAnswer = answers.get(index); //return the choice
		if(this.studentAnswer instanceof MCSAAnswer) {
			MCSAAnswer tmp = (MCSAAnswer) this.studentAnswer;
			tmp.setSelected(true);
			this.studentAnswer = tmp;
		}
	}
	
	//Calculate the students score for this answer
	public double getValue() {
		if(this.isAnswered == false) return 0.0;
		if(this.studentAnswer instanceof MCSAAnswer) {
			return super.getValue((MCSAAnswer)this.studentAnswer); //call the super function
		}
		else return 0.0; // error
	}
	
	//simple setter method for setting the right answer
	public void setRightAnswer(Answer ans) {
		this.rightAnswer = ans;
	}
}

class MCMAQuestion extends MCQuestion{
	protected ArrayList<Answer> studentAnswer;
	public double baseCredit;
	
	//basic constructor for the class, calls the parent constructor
	//also replaces studentAnswer and rightAnswer for arrayLists
	public MCMAQuestion(String text, double maxValue, double bCredit) {
		super(text, maxValue);
		baseCredit = bCredit;
		studentAnswer = new ArrayList<Answer>();
	}
	
	public MCMAQuestion(Scanner file) {
		super(file);
		int numAs = Integer.parseInt(file.nextLine());
		for(int i = 0; i < numAs; i++) {
			double val = file.nextDouble();
			String ans = file.nextLine();
			answers.add(new MCMAAnswer(ans,val));
		}
	}
	
	//get a new MCSAAnswer and return it
	public MCMAAnswer getNewAnswer() {
		Scanner scan = ScannerFactory.getKeyboardScanner(); //set up scanner to read system.in
		System.out.println("Please enter an answer for this Question");
		String ans = scan.nextLine();
		System.out.println("Please enter a double for credit if answer is selected");
		while(!(scan.hasNextDouble())) {
			scan.next();
		}
		double credit = scan.nextDouble();
		return new MCMAAnswer(ans, credit);
	}
	
	//overloaded method to return a MCMAAnswer with default 0 point value
	public MCMAAnswer getNewAnswer(String text) {
		return new MCMAAnswer(text, 0.0);
	}
	
	//overloaded method to return a MCMAAnswer with credit specified
	public MCMAAnswer getNewAnswer(String text, double creditIfSelected) {
		return new MCMAAnswer(text,creditIfSelected);
	}
	
	//get the Students answer choice
	public void getAnswerFromStudent() {
		if(this.isAnswered == true) { //conditon to clear the previous answer choice
			for(int i = 0; i < this.studentAnswer.size(); i++) { //if they already answered we must set everything to false
				if(this.studentAnswer.get(i) instanceof MCMAAnswer) { //loop to set all student answers to false
					MCMAAnswer tmp = (MCMAAnswer)this.studentAnswer.get(i);
					tmp.setSelected(false);
					this.studentAnswer.set(i, tmp);
				}
			}
			this.studentAnswer.clear(); //empty the array 
		}
		Scanner scan = ScannerFactory.getKeyboardScanner();
		String line = scan.nextLine().trim().toLowerCase();
		line = line.replaceAll("\\s+","");
		for(int i = 0; i<line.length(); i++) {
			char choice = line.charAt(i); //scan their input, get the first char 
			int index = (int)choice - 97; //normalize the choice to index form
			if(index < 0 || index >= this.answers.size()) {
				System.out.println("Not a valid answer choice, discarding choice '"+ choice + "'\n");
				continue;
			}
			this.studentAnswer.add(answers.get(index)); //return the choice
		}
		this.isAnswered = true; //set bool to true
		for(int i = 0; i < this.studentAnswer.size(); i++) { //loop through each of their answers and 
			if(this.studentAnswer.get(i) instanceof MCMAAnswer) {
				MCMAAnswer tmp = (MCMAAnswer)this.studentAnswer.get(i);
				tmp.setSelected(true);
				this.studentAnswer.set(i, tmp);
			}
		}
	}
	
	//calculate the value for the question
	public double getValue() {
		double valueSoFar = this.baseCredit*this.maxValue;
		if(this.isAnswered == false) return valueSoFar;
		for(int i = 0; i < this.studentAnswer.size(); i++ ) { //loop through each answer in the ArrayList of student answers
			if(this.studentAnswer.get(i) instanceof MCMAAnswer) {
				valueSoFar += super.getValue((MCMAAnswer)this.studentAnswer.get(i)); //call getCredit for each answer
			}
		}
		return valueSoFar; //total credit* maxvalue
	}
}
	
//Single Answer question class, extends Question
//Question where the student fills in the blank essentially
class SAQuestion extends Question{
	
	//constructor, calls parent constructor
	public SAQuestion(String text, double mxValue) {
		super(text,mxValue);
	}
	
	public SAQuestion(Scanner file) {
		super(file);
		this.rightAnswer = new SAAnswer(file.nextLine());
	}
	
	//method to create an answer for this question
	public SAAnswer getNewAnswer() {
		Scanner scan = ScannerFactory.getKeyboardScanner(); //set up scanner to read system.in
		System.out.println("Please enter an answer for this Question");
		String ans = scan.nextLine();
		return new SAAnswer(ans);
	}
	
	//overloaded method to create an answer for this question, with string input
	public SAAnswer getNewAnswer(String text) {
		return new SAAnswer(text);
	}
	
	//Method to scan in student answer and make it an object
	public void getAnswerFromStudent() {
		Scanner scan = ScannerFactory.getKeyboardScanner();
		this.studentAnswer = new SAAnswer(scan.nextLine());
		this.isAnswered = true;
	}
	
	//return the students score for the question.
	public double getValue() {
		if(this.isAnswered == false) return 0.0;
		return this.maxValue * this.studentAnswer.getCredit(this.rightAnswer);
	}
	
	//set the right answer for this question
	public void setRightAnswer(Answer ans) {
		if(ans instanceof SAAnswer) {
			this.rightAnswer = ans;
		}
	}
	
	//print the question
	public void print() {
		System.out.println(this.text);
		if(this.isAnswered == true) { //if it is answered print their answer too
			System.out.print("Your answer: ");
			this.studentAnswer.print();
		}
	}
}

//Number question class, extends question
//This is for questions that involve math and the like
class NumQuestion extends Question{
	
	//constructor, just call parent constructor
	NumQuestion(String text, double maxValue){
		super(text, maxValue);
	}
	
	NumQuestion(Scanner file){
		super(file);
		this.rightAnswer = new NumAnswer(Double.parseDouble(file.nextLine()));
	}
	
	//print the question, and answer if answered
	public void print() {
		System.out.println(this.text);
		if(this.isAnswered == true) { //check if answered
			System.out.print("Your answer: ");
			this.studentAnswer.print();
		}
	}
	
	//setter for setting the right answer
	public void setRightAnswer(Answer ans) {
		this.rightAnswer = ans;
	}
	
	//Scan the students answer
	public void getAnswerFromStudent() {
		Scanner scan = ScannerFactory.getKeyboardScanner();
		while(!(scan.hasNextDouble())) { //make sure they input a double
			scan.nextLine();
		}
		this.isAnswered = true;
		this.studentAnswer = new NumAnswer(scan.nextDouble());
	}
	
	//get the score for the answer
	public double getValue() {
		if(this.isAnswered == false) return 0.0;
		return this.maxValue*this.studentAnswer.getCredit(this.rightAnswer);
	}
	
	//Method that will return a NumAnswer for the question
	public NumAnswer getNewAnswer() {
		Scanner scan = ScannerFactory.getKeyboardScanner();
		System.out.println("Enter an answer for this question");
		while(!(scan.hasNextDouble())) {
			scan.nextLine();
		}
		return new NumAnswer(scan.nextDouble());
	}
	
	//overloaded method that will return a NumAnswer for this question with the answer already specified
	public NumAnswer getNewAnswer(double x) {
		return new NumAnswer(x);
	}
}
