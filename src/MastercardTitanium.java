public class MastercardTitanium implements DebitCard{



    @Override
    public double WithdrawLimitPerDay() {
        return 10000;
    }

    @Override
    public double TransferLimitPerDay() {
        return 20000;
    }

    @Override
    public double TransferLimitPerDayOwnAccount() {
        return 40000;
    }

    @Override
    public double DepositLimitPerDay() {
        return 100000;
    }


}
