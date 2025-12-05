public class MastercardPlatinum implements DebitCard{




    @Override
    public double WithdrawLimitPerDay() {
        return 20000;
    }

    @Override
    public double TransferLimitPerDay() {
        return 40000;
    }

    @Override
    public double TransferLimitPerDayOwnAccount() {
        return 80000;
    }

    @Override
    public double DepositLimitPerDay() {
        return 100000;
    }



}
