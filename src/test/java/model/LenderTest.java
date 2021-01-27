package model;

import exception.DeniedLoanException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LenderTest {
    Lender lender;

    @Before
    public void setup() {
        lender = new Lender();
    }

    @Test
    public void getAvailableFunds_returnsAvailableFundsForLender_zeroAmount() {
        assertEquals(0, lender.getAvailableFunds(),0);
    }

    @Test
    public void addDepositAmmount(){
        lender.addDepositAmount(100);
        assertEquals(100, lender.getAvailableFunds(),0);
    }

    @Test
    public void processLoan_successefullyProccessesQualifiedLoan() {
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

//    TODO:fix imports to allow use of assertThrows
    @Test(expected = DeniedLoanException.class)
    public void processLoan_throwsExceptionIfProcessingDeniedLoan() {
        Loan loan = new Loan("not qualified", 0, "denied");
        lender.processLoan(loan);
//        Exception exception = Assertions.assertThrows(DeniedLoanException.class, () -> {
//            lender.processLoan(loan);
//        });
//
//        String expectedMessage = "For input string";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
    }

}