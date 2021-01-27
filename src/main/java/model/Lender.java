package model;

import exception.DeniedLoanException;

public class Lender {

    double lenderFund;

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
        } else if (loan.getLoanAmount() > lenderFund) {
            loan.setStatus("on hold");
        }
        return loan;
    }
}
