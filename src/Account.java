import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;

    //Creating new account
    private ArrayList<Transaction> transactions;

    public Account (String name, User holder, Bank theBank) {
        //set the account name and User that holds this account, get UUID
        // and initialisation transaction
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();
    }


    public String getUUID() {
        return this.uuid;
    }


    //get the balance by adding the amount of transaction
    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        } return balance;
    }

    public String getSummaryLine() {
        //get the account balance
        double balance = this.getBalance();

        //summary line need to String format, depend on whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance,
                    this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance,
                    this.name);
        }
    }


    public void printTransactionHistory () {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction (double amount, String message) {
        Transaction newTransaction = new Transaction(amount, message, this);
        this.transactions.add(newTransaction);
    }

}