/* Exam Tester Class
 * Author: Stephen Swick
 * Netid: sswick2
 * Holds main that tests the classes
 */
import java.util.*;
import java.io.*;

public class ExamTester {
	
	public static void main(String [] args) throws IOException {
		System.out.println("Enter filename: ");
		String fileName = ScannerFactory.getKeyboardScanner().nextLine();
		File file = new File(fileName);
		Scanner fileScanner = new Scanner(file);
		FileOutputStream ans = new FileOutputStream("StudentAnswer.txt", false);
		FileOutputStream ques = new FileOutputStream("Questions.txt", false);
		FileOutputStream exam = new FileOutputStream("Exam.txt", false);
		
		PrintWriter studentAns = new PrintWriter(ans);
		PrintWriter questions = new PrintWriter(ques);
		PrintWriter examW = new PrintWriter(exam);
		
		MCSAAnswer ans1 = new MCSAAnswer("YOLO BOY", 1.0);
		MCMAAnswer ans2 = new MCMAAnswer("SHE SUCC ME", 1.0);
		SAAnswer ans3 = new SAAnswer("Dodgers are blowing it");
		NumAnswer ans4 = new NumAnswer(55.0);
		
		studentAns.println("MCSAAnswer");
		ans1.save(studentAns);
		studentAns.println("MCMAAnswer");
		ans2.save(studentAns);
		ans3.save(studentAns);
		ans4.save(studentAns);
		
		MCMAQuestion q1 = new MCMAQuestion("What's my number?", 5.0, 0.4);
		q1.addAnswer(new MCMAAnswer("2", -0.2));
		q1.addAnswer(new MCMAAnswer("911", .2));
		q1.addAnswer(new MCMAAnswer("8479026702", 0.2));
		q1.addAnswer(new MCMAAnswer("11111", -0.2));
		q1.addAnswer(new MCMAAnswer("42", 0.2));
		MCSAQuestion q2 = new MCSAQuestion("This is an abstract exam", 5.0);
		q2.addAnswer(new MCSAAnswer("NIIIIIIICE", 0.0));
		q2.addAnswer(new MCSAAnswer("NIIIIIIICE", 0.0));
		q2.addAnswer(new MCSAAnswer("NIIIIIIICE", 1.0));
		q2.addAnswer(new MCSAAnswer("NIIIIIIICE", 0.0));
		q2.addAnswer(new MCSAAnswer("NIIIIIIICE", 0.0));
		NumQuestion q3 = new NumQuestion("What is 2 gworps * 5 yapples?", 5.0);
		q3.setRightAnswer(new NumAnswer(32.76));
		SAQuestion q4 = new SAQuestion("Why do I buy all these things?", 5.0);
		q4.setRightAnswer(new SAAnswer("To fill the void"));
		
		q1.save(questions);
		q2.save(questions);
		q3.save(questions);
		q4.save(questions);
		
		Exam testExam = new Exam(fileScanner);
		testExam.print();
		testExam.save(examW);
		
		studentAns.close();
		questions.close();
		examW.close();
	}
}
