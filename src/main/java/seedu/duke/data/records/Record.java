package seedu.duke.data.records;

public class Record {
    protected double amount;
    //protected int month;

    public Record(double amount) {
        this.amount = amount;
//      this.month = month;
    }

    public double getAmount() {
        return amount;
    }
}
