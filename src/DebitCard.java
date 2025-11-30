public abstract class DebitCard {
    private String accountId;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String mastercardTypeId;
    private String issuedDate;
    private String status;
    private String type;

    public DebitCard( String accountId, String cardNumber,
                     String expiryDate, String cvv, String mastercardTypeId,
                     String issuedDate, String status) {


        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.mastercardTypeId = mastercardTypeId;
        this.issuedDate = issuedDate;
        this.status = status;
    }


    public abstract double getWithdrawLimitPerDay();
    public abstract double getTransferLimitPerDay();
    public abstract double getTransferOwnAccountLimitPerDay();
    public abstract double getDepositLimitPerDay();
    public abstract double getDepositOwnAccountLimitPerDay();


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getMastercardTypeId() {
        return mastercardTypeId;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getStatus() {
        return status;
    }



    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setMastercardTypeId(String mastercardTypeId) {
        this.mastercardTypeId = mastercardTypeId;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
