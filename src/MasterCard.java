public class MasterCard implements DebitCard{




    @Override
    public double WithdrawLimitPerDay() {
        return 5000;
    }

    @Override
    public double TransferLimitPerDay() {
        return 10000;
    }

    @Override
    public double TransferLimitPerDayOwnAccount() {
        return 20000;
    }

    @Override
    public double DepositLimitPerDay() {
        return 100000;
    }

    public String getCardName() { return "MASTERCARD"; }


}
