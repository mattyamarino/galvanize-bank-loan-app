package model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private String qualification;
    private double loanAmount;
    private String status;
    private LocalDate creationDate;

    public Loan(String qualification, double loanAmount, String status) {
        this.qualification = qualification;
        this.loanAmount = loanAmount;
        this.status = status;
        this.creationDate = LocalDate.now();
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return Double.compare(loan.loanAmount, loanAmount) == 0 &&
                Objects.equals(qualification, loan.qualification) &&
                Objects.equals(status, loan.status) &&
                Objects.equals(creationDate, loan.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualification, loanAmount, status, creationDate);
    }
}
