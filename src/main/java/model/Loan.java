package model;

import java.util.Objects;

public class Loan {
    private String qualification;
    private double loanAmount;
    private String status;

    public Loan(String qualification, double loanAmount, String status) {
        this.qualification = qualification;
        this.loanAmount = loanAmount;
        this.status = status;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return Double.compare(loan.loanAmount, loanAmount) == 0 &&
                Objects.equals(qualification, loan.qualification) &&
                Objects.equals(status, loan.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualification, loanAmount, status);
    }
}
