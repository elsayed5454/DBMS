package eg.edu.alexu.csd.oop.db.cs33;

import java.util.Scanner;

public class CommandLine {

	public static void main(String[] args) {
		
		System.out.println("Enter SQL queries & -1 to exit");
		Scanner sc = new Scanner(System.in);
		QueryParser queryParser = new QueryParser();
		while (sc.hasNextLine()) {
			String query = sc.nextLine();
			if (query.equals("-1")) {
	            queryParser.save();
				break;
			}
			if (queryParser.commandChooser(query) == false) {
				System.out.println("Invalid SQL Statement");
			}
		}
		sc.close();
		//save the table after the program ends
	}
}
