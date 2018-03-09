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
		String tok = fileScanner.nextLine();
		if(tok.equals("MCSAAnswer")) {
			MCSAAnswer ans1 = new MCSAAnswer(fileScanner);
			ans1.print();
		}
		if(tok.equals("SAQuestion")) {
			SAQuestion question1 = new SAQuestion(fileScanner);
			question1.print();
			question1.getAnswerFromStudent();
			System.out.println("Score: " + question1.getValue());
		}
		fileScanner.nextLine();
		tok = fileScanner.nextLine();
		if(tok.equals("MCSAQuestion")) {
			MCSAQuestion question2 = new MCSAQuestion(fileScanner);
			question2.print();
			question2.getAnswerFromStudent();
			System.out.println("Score: " + question2.getValue());
		}
	}
}
