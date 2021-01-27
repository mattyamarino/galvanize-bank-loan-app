package model;

public class Lender {

    double lenderFund;

    public double getAvailableFunds() {
        return this.lenderFund;
    }

    public void addDepositAmount(double depositAmount) {
        this.lenderFund += depositAmount;
    }
}
