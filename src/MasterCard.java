public class MasterCard extends DebitCard{




    public MasterCard(String accountId, String cardNumber, String expiryDate, String cvv, String mastercardTypeId, String issuedDate, String status) {
        super(accountId, cardNumber, expiryDate, cvv, mastercardTypeId, issuedDate, status);
    }

    @Override
    public double getWithdrawLimitPerDay() {
        return 5000;
    }

    @Override
    public double getTransferLimitPerDay() {
        return 10000;
    }

    @Override
    public double getTransferOwnAccountLimitPerDay() {
        return 20000;
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
