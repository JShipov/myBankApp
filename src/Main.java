import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("Bank");
        User aUser = theBank.addUser("Yuliya", "Shipovskaya", "1111");
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currentUser;
        while (true) {
            currentUser = Main.mainMenuPrompt(theBank, scanner);
            Main.printUserMenu(currentUser, scanner);

        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID:\n ");
            userID = scanner.nextLine();
            System.out.print("Enter pin:\n ");
            pin = scanner.nextLine();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination.\nPlease try again");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scanner) {
        theUser.printAccountsSum();
        int choice;
        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println("\nEnter choice");
            System.out.println("1 - transaction history");
            System.out.println("2 - deposit");
            System.out.println("3 - withdraw");
            System.out.println("4 - transfer");
            System.out.println("5 - quit");

            choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Out of the range.");
            }
        } while (choice < 1 || choice > 5) ;

        switch (choice) {
            case 1:
                Main.showTransactionHistory(theUser, scanner);
                break;
            case 2:
                Main.depositFunds(theUser, scanner);
                break;
            case 3:
                Main.withdrawFunds(theUser, scanner);
                break;
            case 4:
                Main.transferFunds(theUser, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }

        if (choice != 5) {
            Main.printUserMenu(theUser, scanner);
        }
    }

    public static void showTransactionHistory (User theUser, Scanner scanner){
        //get the account to show action with
        int theAct;
        do {
            System.out.printf("Enter the number (1-%d) of the account whose transactions you wanna see\n"
                    , theUser.numAccounts());
            //get the number of account: method nextInt start counting from zero, that`s why we need to takeaway 1
            // from console input
            theAct = scanner.nextInt() - 1;
            //get the error about number of account
            if (theAct < 0 || theAct >= theUser.numAccounts()) {
                System.out.println("Invalid account");
            }
        } while (theAct < 0 || theAct >= theUser.numAccounts());
        //do print
        theUser.printActTransHistory(theAct);
    }

    public static void depositFunds (User theUser, Scanner scanner) {
        //get the account to deposit in, get the amount to deposit, get a message on send deposit and get actual balance after
        int toAct;
        double amount;
        double actBal;
        String message;

        //First do-while: get a number account to deposit in
        do {
            System.out.printf("Enter the account number 1-%d to deposit in\n", theUser.numAccounts());
            //get a number of account: method nextInt start counting from zero, that`s why we need to takeaway 1
            // from console input
            toAct = scanner.nextInt() - 1;
            if (toAct < 0 || toAct >= theUser.numAccounts()) {
                System.out.println("Invalid account");
            }
        } while (toAct < 0 || toAct > theUser.numAccounts());

        actBal = theUser.getActBalance(toAct);

        //Second do-while: get the deposit amount
        do {
            System.out.printf("Enter the amount to transfer $%.02f : ", actBal);
            amount = scanner.nextDouble();
            //get the error about deposit amount
            if (amount <= 0) {
                System.out.println("Amount must be greater than 0");
            }
        } while (amount <= 0);

        scanner.nextLine();

        //get a message
        System.out.println("Enter a message to clarify of transfer purpose: ");
        message = scanner.nextLine();
        //do deposit
        theUser.addActTransaction(toAct, amount,message);
    }

    public static void withdrawFunds (User theUser,Scanner scanner) {
        //get the account to withdraw from, get the amount to withdraw, get a message on withdraw and get actual balance after
        int fromAct;
        double amount;
        double actBal;
        String message;

        //First do-while: get the account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from ", theUser.numAccounts());
            //get the number of account, method nextInt start counting from zero, that`s why we need to takeaway 1
            // from console input
            fromAct = scanner.nextInt() - 1;
            //get the error about number of account
            if (fromAct < 0 || fromAct >= theUser.numAccounts()) {
                System.out.println("Invalid account");
            }
        } while (fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getActBalance(fromAct);

        //Second do-while:get the amount to withdraw
        do {
            System.out.printf("Amount to transfer ", actBal);
            amount = scanner.nextDouble();
            //get the error about withdraw amount, we can`t get negative account balance
            if (amount < 0) {
                System.out.println("Amount mustn`t be less than 0");
            } else if (amount > actBal) {
                System.out.printf("Amount to transfer is greater than $%.02f\n", actBal);
            }
        } while (amount < 0 || amount > actBal);
        scanner.nextLine();

        //get a message
        System.out.println("Enter a message to clarify of transfer purpose: ");
        message = scanner.nextLine();

        //do withdraw
        theUser.addActTransaction(fromAct, -1 * amount, message );
    }

    public static void transferFunds(User theUser, Scanner scanner) {
        //get the accounts number to transfer between, get the amount to transfer and get a message on transfer
        int fromAct;
        int toAct;
        double amount;
        double actBal;

        //First do-while:get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from ", theUser.numAccounts());
            //get the number of account, method nextInt start counting from zero, that`s why we need to takeaway 1
            // from console input
            fromAct = scanner.nextInt() - 1;
            //get the error about account transfer
            if (fromAct < 0 || fromAct >= theUser.numAccounts()) {
                System.out.println("Invalid account");
            }
        } while (fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getActBalance(fromAct);

        //Second do-while: get the account to transfer in
        do {
            System.out.printf("Enter the number 1-%d of the account to transfer to\n " , theUser.numAccounts());
            //get the number of account, method nextInt start counting from zero, that`s why we need to takeaway 1
            // from console input
            toAct = scanner.nextInt() - 1;
            //get the error about account transfer
            if (toAct < 0 || toAct >= theUser.numAccounts()) {
                System.out.println("Invalid account");
            }
        } while (toAct < 0 || toAct >= theUser.numAccounts());

        //Third do-while: get the amount to transfer
        do {
            System.out.printf("Amount to transfer ", actBal);
            amount = scanner.nextDouble();
            //het the error about amount
            if (amount < 0) {
                System.out.println("Amount mustn`t be less than 0");
            } else if (amount > actBal) {
                System.out.printf("Amount to transfer is greater than $%.02f\n", actBal);
            }
        } while (amount < 0 || amount > actBal);

        //do the transfer
        theUser.addActTransaction (fromAct, -1 * amount, String.format("Transfer to %s ", theUser.getActUUID(toAct)));
        theUser.addActTransaction (toAct, amount, String.format("Transfer to account ", theUser.getActUUID(fromAct)));
    }
}