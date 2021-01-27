package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorrowerTest {

    @Test
    public void loanQualified(){
        Borrower borrower = new Borrower(30,630,50000);
        Loan loanResult = borrower.getQualification(180000);
        Loan expectedLoanResult = new Loan("qualified", 180000, "qualified");
        assertEquals(expectedLoanResult, loanResult);
    }

}
