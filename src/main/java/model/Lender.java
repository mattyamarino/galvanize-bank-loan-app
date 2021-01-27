package model;

import exception.DeniedLoanException;

public class Lender {

    private double lenderFund;
    private double pendingFunds;

    public double getAvailableFunds() {
        return this.lenderFund;
    }

    public void addDepositAmount(double depositAmount) {
        this.lenderFund += depositAmount;
    }

    public Loan processLoan(Loan loan) {
        if(loan.getStatus().equals("denied")){
            throw new DeniedLoanException("Denied Load Do Not Proceed");
        }
        if(loan.getLoanAmount() <= lenderFund) {
            loan.setStatus("approved");
            this.pendingFunds += loan.getLoanAmount();
            this.lenderFund -= loan.getLoanAmount();
        } else if (loan.getLoanAmount() > lenderFund) {
            loan.setStatus("on hold");
        }
        return loan;
    }

    public double getPendingFunds() {
        return this.pendingFunds;
    }

    public void setPendingFunds(double funds) {
        this.pendingFunds = funds;
    }
}
