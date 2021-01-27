package exception;

public class DeniedLoanException extends RuntimeException {
    public DeniedLoanException(String s) {
        super(s);
    }
}
