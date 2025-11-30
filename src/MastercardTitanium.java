public class MastercardTitanium extends DebitCard{


    public MastercardTitanium(String accountId, String cardNumber, String expiryDate, String cvv, String mastercardTypeId, String issuedDate, String status) {

        super(accountId, cardNumber, expiryDate, cvv, mastercardTypeId, issuedDate, status);
    }

    @Override
    public double getWithdrawLimitPerDay() {
        return 10000;
    }

    @Override
    public double getTransferLimitPerDay() {
        return 20000;
    }

    @Override
    public double getTransferOwnAccountLimitPerDay() {
        return 40000;
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
