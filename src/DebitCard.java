public interface DebitCard {

    public double WithdrawLimitPerDay();
    public double TransferLimitPerDay();
    public double TransferLimitPerDayOwnAccount();
    public double DepositLimitPerDay();

}
