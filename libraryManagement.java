package libraryManagement;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/*
NOTE:
    compile : javac -cp "lib/*" -d bin *.java
    run: java -cp "bin;lib/*" libraryManagement.libraryManagement
*/ 
class libraryManagement{
    public static void main(String[] args){
        Scanner obj = new Scanner(System.in);
        Library library = new Library("books_json.json");
        
        while(true){
            System.out.println("\nEnter your Role(either 1 or 2 or 3):");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            String role = obj.next();

            switch(role){
                case "1":
                    new User(library);
                    break;
                case "2":
                    new Admin(library);
                    break;
                case "3":
                    System.out.println("\nThanks for visiting! We hope to see you again at the Library soon.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input.");
            }
        }
    }
}
