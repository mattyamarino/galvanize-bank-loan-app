package model;

import exception.DeniedLoanException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LenderTest {
    Lender lender;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        lender = new Lender();
    }

    @Test
    public void getAvailableFunds_returnsAvailableFundsForLender_zeroAmount() {
        assertEquals(0, lender.getAvailableFunds(),0);
    }

    @Test
    public void addDepositAmount(){
        lender.addDepositAmount(100);
        assertEquals(100, lender.getAvailableFunds(),0);
    }

    @Test
    public void processLoan_successfullyProcessesQualifiedLoan() {
        Loan loan = new Loan("qualified", 15000, "qualified");
        Loan expectedLoan = new Loan("qualified", 15000, "approved");
        lender.addDepositAmount(100000);
        Loan result = lender.processLoan(loan);
        assertEquals(expectedLoan, result);
        assertEquals(85000 ,lender.getAvailableFunds(),0);
        assertEquals(15000,lender.getPendingFunds(), 0);
    }

    @Test
    public void processLoan_putsQualifiedLoanOnHold() {
        Loan loan = new Loan("qualified", 15000, "qualified");
        Loan expectedLoan = new Loan("qualified", 15000, "on hold");
        lender.addDepositAmount(10000);
        Loan result = lender.processLoan(loan);
        assertEquals(expectedLoan, result);
    }

    @Test
    public void processLoan_throwsExceptionIfProcessingDeniedLoan() {
        expectedEx.expect(DeniedLoanException.class);
        expectedEx.expectMessage("Denied Load Do Not Proceed");

        Loan loan = new Loan("not qualified", 0, "denied");
        lender.processLoan(loan);
    }

    @Test
    public void checkForExpiredLoans_removesLoanFromList_andSendsAmountFromPendingBackToAvailable() {
        Loan loan1 = new Loan("qualified", 15000, "qualified");
        loan1.setCreationDate(LocalDate.now().minusDays(4));
        Loan loan2 = new Loan("qualified", 20000, "approved");
        Loan expiredLoan = new Loan("qualified", 15000, "expired");
        expiredLoan.setCreationDate(LocalDate.now().minusDays(4));
        lender.loans.add(loan1);
        lender.loans.add(loan2);
        lender.setPendingFunds(50000);

        lender.checkForExpiredLoans();

        assertEquals(expiredLoan, lender.loans.get(0));
        assertEquals(35000, lender.getPendingFunds(), 0);
        assertEquals(15000, lender.getAvailableFunds(), 0);
    }


}