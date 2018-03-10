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
		FileOutputStream exam = new FileOutputStream("Exam.txt", false);
		
		PrintWriter studentAns = new PrintWriter(ans);
		PrintWriter examW = new PrintWriter(exam);
	
		
		
		Exam testExam = new Exam(fileScanner);
		testExam.print();
		testExam.reorderQuestions();
		for(int i=0; i < 4; i++) {
			testExam.reorderMCAnswers(i);
		}
		testExam.save(examW);
		System.out.println("Enter your name: ");
		String name = ScannerFactory.getKeyboardScanner().nextLine();
		System.out.println();
		testExam.takeExam();
		studentAns.println(name + "\n");
		testExam.saveStudentAnswers(studentAns);
		
		
		studentAns.close();
		examW.close();
		
		File eFile = new File("Exam.txt");
		File saFile = new File("StudentAnswer.txt");
		Scanner studentAnswers = new Scanner(saFile);
		Scanner remakeExam = new Scanner(eFile);
		testExam = new Exam(remakeExam);
		testExam.print();
		testExam.restoreStudentAnswers(studentAnswers);
		testExam.reportQuestionValues();	
	}
}
