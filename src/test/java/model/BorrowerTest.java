package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorrowerTest {

    @Test
    public void loanQualified_qualified_borrower(){
        Borrower borrower = new Borrower(30,630,50000);
        Loan loanResult = borrower.getQualification(180000);
        Loan expectedLoanResult = new Loan("qualified", 180000, "qualified");
        assertEquals(expectedLoanResult, loanResult);
    }

    @Test
    public void loanQualified_partially_qualified_borrower(){
        Borrower borrower = new Borrower(30,630,20000);
        Loan loanResult = borrower.getQualification(180000);
        Loan expectedLoanResult = new Loan("partially qualified", 80000, "qualified");
        assertEquals(expectedLoanResult, loanResult);
    }

    @Test
    public void loanQualified_unqualified_borrower(){
        Borrower borrower = new Borrower(37,630,50000);
        Loan loanResult = borrower.getQualification(180000);
        Loan expectedLoanResult = new Loan("not qualified", 0, "denied");
        assertEquals(expectedLoanResult, loanResult);
    }

}
