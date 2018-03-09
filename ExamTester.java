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
		Exam testExam = new Exam(fileScanner);
		testExam.print();
	}
}
