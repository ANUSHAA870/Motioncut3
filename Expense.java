import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    private Date date;
    private String category;
    private double amount;
    private String description;

    public Expense(Date date, String category, double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: " + amount + ", Description: " + description;
    }
}