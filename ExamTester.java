import java.util.*;

public class ExamTester {
	
	public static void main(String [] args) {
		Exam exam1 = new Exam("Yolo Exam");
		
		MCSAQuestion question1 = new MCSAQuestion("Hello what is my name?", 5.0);
		question1.addAnswer(new MCSAAnswer("Gunter", 0));
		question1.addAnswer(new MCSAAnswer("Stephen", 1.0));
		question1.addAnswer(new MCSAAnswer("Weedle", 0));
		question1.addAnswer(new MCSAAnswer("Mounia", 0));
		question1.addAnswer(new MCSAAnswer("Juan", 0));
		
		MCSAQuestion question5 = new MCSAQuestion("How you doin?  My name is lonely nice to meet you, whats my number?",5.0);
		question5.addAnswer(question5.getNewAnswer("911", 1.0));
		question5.addAnswer(question5.getNewAnswer("Chirp chirp", 0.0));
		question5.addAnswer(question5.getNewAnswer("8479026702", 0.0));
		question5.addAnswer(question5.getNewAnswer("77777777", 0.0));
		question5.addAnswer(question5.getNewAnswer("90210", 0.0));
		
		MCMAQuestion question2 = new MCMAQuestion("What am I listening to?", 5.0);
		question2.addAnswer(new MCMAAnswer("Puzzle", 0.5));
		question2.addAnswer(new MCMAAnswer("Kanye", 0));
		question2.addAnswer(new MCMAAnswer("King Gizzard", 0.5));
		question2.addAnswer(new MCMAAnswer("Beach House", 0));
		question2.addAnswer(new MCMAAnswer("Milk Music", 0));
		
		MCMAQuestion question6 = new MCMAQuestion("How am I feeling right now?", 5.0);
		question6.addAnswer(question6.getNewAnswer("Glitter", 0.33));
		question6.addAnswer(question6.getNewAnswer("like laffin", 0.0));
		question6.addAnswer(question6.getNewAnswer("Good?", 0.33));
		question6.addAnswer(question6.getNewAnswer("like poop", 0.0));
		question6.addAnswer(question6.getNewAnswer("BAD", 0.34));
		
		SAQuestion question3 = new SAQuestion("What am I rolling?",5.0);
		question3.setRightAnswer(new SAAnswer("dice"));
		
		SAQuestion question7 = new SAQuestion("What kind of jeans am I wearing?", 5.0);
		question7.setRightAnswer(question7.getNewAnswer("Pure Blue Japan"));
		
		NumQuestion question4 = new NumQuestion("What is 4+4?", 5.0);
		question4.setRightAnswer(new NumAnswer(8));
		
		NumQuestion question8 = new NumQuestion("Yo i have 3 birds and 10 dogs how many birds I got if a dog wallopped one?", 5.0);
		question8.setRightAnswer(question8.getNewAnswer(3));
		
		exam1.AddQuestion(question1);
		exam1.AddQuestion(question2);
		exam1.AddQuestion(question3);
		exam1.AddQuestion(question4);
		exam1.AddQuestion(question5);
		exam1.AddQuestion(question6);
		exam1.AddQuestion(question7);
		exam1.AddQuestion(question8);
		
		exam1.print();
		
		System.out.println("Randomizing exam");
		
		exam1.reorderQuestions();
		for(int i = 0; i<8; i++) {
			exam1.reorderMCAnswers(i);
		}
		
		exam1.print();
		System.out.println("**************** TAKING EXAM ****************\n");
		exam1.takeExam();
		exam1.reportQuestionValues();
	}
}
