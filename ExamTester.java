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
		PrintWriter studentAns = new PrintWriter(ans);
		
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
		
		studentAns.close();
		//exam testExam = new Exam(fileScanner);
		//testExam.print();
	}
}
