package model;

import exception.DeniedLoanException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        expectedEx.expectMessage("Denied Loan Do Not Proceed");

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

    @Test
    public void getLoansByStatus_returnsLoansOfSelectedStatus() {
        Loan loan1 = new Loan("qualified", 15000, "qualified");
        Loan loan2 = new Loan("qualified", 20000, "approved");
        Loan loan3 = new Loan("partially qualified", 15000, "qualified");
        Loan loan4 = new Loan("qualified", 20000, "on hold");
        lender.loans.add(loan1);
        lender.loans.add(loan2);
        lender.loans.add(loan3);
        lender.loans.add(loan4);

        List<Loan> expected = new ArrayList<>();
        expected.add(loan1);
        expected.add(loan3);

        List<Loan> result = lender.getLoansByStatus("qualified");

        assertEquals(expected, result);
    }

    @Test
    public void generateLoan_generatesLoanForBorrower() {
        Borrower borrower = new Borrower(20, 760, 50000);
        lender.generateLoan(borrower, 300000);
        Loan expected = new Loan("partially qualified", 200000, "qualified");
        assertEquals(expected, lender.loans.get(0));
    }

    @Test
    public void reviewLoanFromList_findsLoanInList_thenAdjustsStatus() {
        lender.setPendingFunds(100000);
        Loan loan1 = new Loan("qualified",20000, "approved");
        Loan loan2 = new Loan("not qualified",0, "denied");
        lender.loans.add(loan1);
        lender.loans.add(loan2);
        lender.reviewLoanFromList(loan1, true);
        Loan expectedResult = new Loan("qualified",20000, "accepted");
        assertEquals(expectedResult,lender.loans.get(0));
        assertEquals(80000,lender.getPendingFunds(),0);
    }


    @Test
    public void reviewLoan_changesStatusToAcceptedAndRemovesAmmountFromLenderPendingFunds(){
        lender.setPendingFunds(100000);
        Loan loan = new Loan("qualified",20000, "approved");
        Loan loanResult = lender.reviewLoan(loan,true);
        Loan expectedResult = new Loan("qualified",20000, "accepted");
        assertEquals(expectedResult,loanResult);
        assertEquals(80000,lender.getPendingFunds(),0);
    }

    @Test
    public void reviewLoan_changesStatusToRejectedAndSendAmountToLenderAvailableAmount(){
        lender.setPendingFunds(100000);
        Loan loan = new Loan("qualified",20000, "approved");
        Loan loanResult = lender.reviewLoan(loan,false);
        Loan expectedResult = new Loan("qualified",20000, "rejected");
        assertEquals(expectedResult,loanResult);
        assertEquals(80000,lender.getPendingFunds(),0);
        assertEquals(20000, lender.getAvailableFunds(), 0);
    }
}