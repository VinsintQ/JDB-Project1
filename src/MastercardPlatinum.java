public class MastercardPlatinum extends DebitCard{


    public MastercardPlatinum(String accountId, String cardNumber, String expiryDate, String cvv, String mastercardTypeId, String issuedDate, String status) {
        super(accountId, cardNumber, expiryDate, cvv, mastercardTypeId, issuedDate, status);
    }

    @Override
    public double getWithdrawLimitPerDay() {
        return 20000;
    }

    @Override
    public double getTransferLimitPerDay() {
        return 40000;
    }

    @Override
    public double getTransferOwnAccountLimitPerDay() {
        return 80000;
    }

    @Override
    public double getDepositLimitPerDay() {
        return 100000;
    }

    @Override
    public double getDepositOwnAccountLimitPerDay() {
        return 200000;
    }
}
