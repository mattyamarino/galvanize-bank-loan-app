package model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}