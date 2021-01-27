package exception;

public class DeniedLoanException extends RuntimeException {
    public DeniedLoanException(String message) {
        super(message);
    }

    public DeniedLoanException() {
    }
}
