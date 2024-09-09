import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpenseTrackerApp {
    private static final String USER_FILE = "users.txt";
    private static final String EXPENSE_FILE = "expenses.txt";
    private static Map<String, User> users = new HashMap<>();
    private static List<Expense> expenses = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        loadUsers();
        loadExpenses();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Expense Tracker!");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                register(scanner);
            } else if (choice == 2) {
                login(scanner);
                if (currentUser != null) {
                    userMenu(scanner);
                }
            } else if (choice == 3) {
                saveUsers();
                saveExpenses();
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Registration successful!");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Category-wise Summation");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                addExpense(scanner);
            } else if (choice == 2) {
                viewExpenses();
            } else if (choice == 3) {
                viewCategoryWiseSummation();
            } else if (choice == 4) {
                currentUser = null;
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        try {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateString = scanner.nextLine();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            System.out.print("Enter category: ");
            String category = scanner.nextLine();

            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            expenses.add(new Expense(date, category, amount, description));
            System.out.println("Expense added successfully!");

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    private static void viewCategoryWiseSummation() {
        Map<String, Double> categorySum = new HashMap<>();

        for (Expense expense : expenses) {
            categorySum.put(expense.getCategory(), categorySum.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        for (String category : categorySum.keySet()) {
            System.out.println("Category: " + category + ", Total: " + categorySum.get(category));
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No user data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (Exception e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSE_FILE))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No expense data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading expense data: " + e.getMessage());
        }
    }

    private static void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXPENSE_FILE))) {
            oos.writeObject(expenses);
        } catch (Exception e) {
            System.out.println("Error saving expense data: " + e.getMessage());
        }
    }
}