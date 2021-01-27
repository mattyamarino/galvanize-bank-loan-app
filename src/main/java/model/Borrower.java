package model;

public class Borrower {

    private double dti;
    private double creditScore;
    private double savings;

    public Borrower(double dti, double creditScore, double savings) {
        this.dti = dti;
        this.creditScore = creditScore;
        this.savings = savings;
    }

    public Loan getQualification(double requestedAmount) {
        boolean hasEnoughsavings = this.savings >= (requestedAmount/4 );
        if(this.dti < 36.0 && this.creditScore > 620.0  && hasEnoughsavings){
            return new Loan("qualified", requestedAmount, "qualified");
        }
        return null;
    }
}
