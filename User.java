package libraryManagement;

import java.util.*;

public class User {
    Library lib;
    Scanner obj;
    private String userName = "";
    private String phoneNum = "";

    public User(Library library){
        this.lib = library;
        obj = new Scanner(System.in);
        userFunctionality();
    }

    void userFunctionality() {
        System.out.println("\n\n-------------- USER PANEL --------------");
    
        while(true){
            System.out.println("\n1. Borrow / Return / View Borrowed Books");
            System.out.println("2. Search Book By Name");
            System.out.println("3. Search Book By ISBN");
            System.out.println("4. View All Books");
            System.out.println("5. Back");

            String option = obj.next();
            System.out.println();

            switch(option){
                case "1":
                    if(userName.equals("")){
                        obj.nextLine();
                        System.out.print("Enter Name: ");
                        userName = obj.nextLine();
                        System.out.print("Enter Phone Number: ");
                        phoneNum = obj.nextLine();
                    }

                    while(true){
                        System.out.println("\n1. Borrow Book");
                        System.out.println("2. Return Book");
                        System.out.println("3. View Borrowed Books");
                        System.out.println("4. Back");

                        String option1 = obj.next();
                        System.out.println();

                        switch(option1){
                            case "1":
                                System.out.print("Enter Book ISBN: ");
                                int isbnNo = obj.nextInt();
                                obj.nextLine();
                                lib.borrowBook(isbnNo, userName, phoneNum);
                                break;
                            case "2":
                                System.out.print("Enter Book ISBN: ");
                                int isbnNo2 = obj.nextInt();
                                obj.nextLine();
                                lib.returnBook(isbnNo2, userName, phoneNum);
                                break;
                            case "3":
                                lib.viewBorrowedBooks(userName, phoneNum);
                                break;
                            case "4":
                                return;
                            default:
                                System.out.println("Invalid Input.");
                        }
                    }
                case "2":
                    System.out.print("Enter Book Name: ");
                    String bookName = obj.nextLine();
                    lib.search(bookName);
                    break;
                case "3":
                    System.out.print("Enter Book ISBN: ");
                    int isbnNo3 = obj.nextInt();
                    obj.nextLine();
                    lib.search(isbnNo3);
                    break;
                case "4":
                    lib.displayBookInfo();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid Input.");
            }
        }
    }
}
