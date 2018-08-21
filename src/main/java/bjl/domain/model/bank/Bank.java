package bjl.domain.model.bank;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;

import java.util.Date;

/**
 * 银行实体类
 * Created by zhangjin on 2017/9/6.
 */
public class Bank extends ConcurrencySafeEntity {

    private Account account;                  //用户
    private String bankName;            //银行名称
    private String bankAccountNo;       //银行卡号
    private String bankAccountName;     //持卡人姓名
    private String bankProvince;        //开户省份
    private String bankCity;            //开户市
    private String bankDeposit;         //开户支行
    private String bankCardType;        //银行卡类型 （01 借记卡,02 信用卡）
    private String bankAccountType;     //账户类型(01 对私 02 对公)
    private String bankNo;              //联行行号，对公时必须填写

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


}
