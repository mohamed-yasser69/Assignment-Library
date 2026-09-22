import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1: addItem(); break;
                    case 2: addMember(); break;
                    case 3:
                        library.borrowItem(readLine("Member id: "), readLine("Item id: "));
                        System.out.println("Borrow successful.");
                        break;
                    case 4:
                        library.returnItem(readLine("Member id: "), readLine("Item id: "));
                        System.out.println("Return successful.");
                        break;
                    case 5: library.listCatalog(); break;
                    case 6: library.printReport(); break;
                    case 7: running = false; System.out.println("Goodbye!"); break;
                    case 8: library.searchByTitle(readLine("Title keyword: ")); break;
                    case 9: library.listAvailable(); break;
                    default: System.out.println("Invalid choice. Pick 1-9.");
                }
            } catch (LibraryException e) {
                System.out.println("Could not complete: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Library Lending System =====");
        System.out.println("1. Add Item");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Item");
        System.out.println("4. Return Item");
        System.out.println("5. List Catalog");
        System.out.println("6. Report");
        System.out.println("7. Exit");
        System.out.println("8. Search by Title   (bonus)");
        System.out.println("9. Available Items   (bonus)");
    }

    private static void addItem() {
        System.out.println("Kind: 1) Book  2) Magazine  3) DVD");
        int kind = readInt("Choose kind: ");
        LibraryItem item;
        switch (kind) {
            case 1:
                item = new Book(readLine("Title: "), readLine("Author: "), readInt("Pages: "));
                break;
            case 2:
                item = new Magazine(readLine("Title: "), readInt("Issue number: "));
                break;
            case 3:
                item = new DVD(readLine("Title: "), readInt("Runtime (minutes): "));
                break;
            default:
                System.out.println("Unknown kind.");
                return;
        }
        library.addItem(item);
        System.out.println("Added " + item.getId() + ".");
    }

    private static void addMember() throws LibraryException {
        String id = readLine("Member id: ");
        String name = readLine("Name: ");
        int max = readInt("Max items allowed: ");
        library.addMember(new Member(id, name, max));
        System.out.println("Member " + id + " added.");
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    /** Keeps asking until the user types a valid whole number. */
    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}