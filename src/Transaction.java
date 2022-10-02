import java.util.Date;
public class Transaction {
    private double amount;
    private Date timestamp;
    private String message;
    private Account inAccount;


    //Transaction without a message
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        //account in which transaction was performed
        this.inAccount = inAccount;
        //time and date of transaction
        this.timestamp = new Date();
        //message for transaction
        this.message = "";
    }

    //Transaction with a message
    public Transaction(double amount, String message, Account inAccount) {
        this(amount, inAccount);
        this.message = message;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine () {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString()
                    ,this.amount, this.message);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString()
                    ,-this.amount, this.message);
        }
    }
}
