package model;

import exception.DeniedLoanException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lender {

    List<Loan> loans = new ArrayList<>();
    private double lenderFund;
    private double pendingFunds;

    public double getAvailableFunds() {
        return this.lenderFund;
    }

    public void addDepositAmount(double depositAmount) {
        this.lenderFund += depositAmount;
    }

    public double getPendingFunds() {
        return this.pendingFunds;
    }

    public void setPendingFunds(double funds) {
        this.pendingFunds = funds;
    }

    public void setLenderFund(double lenderFund) {
        this.lenderFund = lenderFund;
    }

    public Loan processLoan(Loan loan) {
        if(loan.getStatus().equals("denied")){
            throw new DeniedLoanException("Denied Loan Do Not Proceed");
        }
        if(loan.getLoanAmount() <= lenderFund) {
            loan.setStatus("approved");
            this.pendingFunds += loan.getLoanAmount();
            this.lenderFund -= loan.getLoanAmount();
        } else {
            loan.setStatus("on hold");
        }
        return loan;
    }

    public void checkForExpiredLoans() {
        LocalDate finalValidDate = LocalDate.now().minusDays(3);
        for(Loan loan: loans) {
            if(loan.getCreationDate().isBefore(finalValidDate)) {
                loan.setStatus("expired");
                setPendingFunds(getPendingFunds() - loan.getLoanAmount());
                setLenderFund(getAvailableFunds() + loan.getLoanAmount());
            }
        }
    }

    public List<Loan> getLoansByStatus(String requestedStatus) {
        return loans.stream()
                .filter(loan -> loan.getStatus().equals(requestedStatus))
                .collect(Collectors.toList());
    }


    public void generateLoan(Borrower borrower, double amountToBorrow) {
        loans.add(borrower.getQualification(amountToBorrow));
    }

    public Loan reviewLoan(Loan loanToReview, boolean isAccepted) {
        if(isAccepted) {
            loanToReview.setStatus("accepted");
        } else {
            loanToReview.setStatus("rejected");
            setLenderFund(getAvailableFunds() + loanToReview.getLoanAmount());
        }
        setPendingFunds(getPendingFunds() - loanToReview.getLoanAmount());
        return loanToReview;
    }

    public void reviewLoanFromList(Loan loanToReview, boolean isAccepted) {
        for(Loan loan: loans) {
            if(loanToReview == loan) {
                loan = reviewLoan(loan, isAccepted);
                break;
            }
        }
    }
}
