package eg.edu.alexu.csd.oop.db.cs33;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Enter SQL queries & -1 to exit");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			
			String query = sc.nextLine();
			if(query.equals("-1")) {
				break;
			}
			
			QueryParser queryParser = new QueryParser();
			if(queryParser.commandChooser(query) == false) {
				System.out.println("Invalid SQL Statement");
			}
			
			
		}
		

	}

}
