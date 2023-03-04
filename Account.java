package com.song;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: song
 * @Date: 2023/02/24/12:52
 * @Description: 账户类Account，至少需要包含（卡号accountId、姓名accountName、性别accountSex、密码accountPassword、余额accountBalance、每次取现额度withdrawalAmount）
 */
public class Account {
    //卡号
    private String accountId;
    //账户姓名
    private String accountName;
    //账户性别
    private String accountSex;
    //账户密码
    private String accountPassword;
    //账户余额
    private double accountBalance;
    //账户每次取现额度
    private double withdrawalAmount;

    private boolean isLock;

    public Account() {
    }

    public Account(String accountId, String accountName, String accountSex, String accountPassword, double withdrawalAmount) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountSex = accountSex;
        this.accountPassword = accountPassword;
        this.withdrawalAmount = withdrawalAmount;
    }

    public Account(String accountId, String accountName, String accountSex, String accountPassword, double accountBalance, double withdrawalAmount) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountSex = accountSex;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountSex() {
        return accountSex;
    }

    public void setAccountSex(String accountSex) {
        this.accountSex = accountSex;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
