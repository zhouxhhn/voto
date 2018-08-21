package bjl.core.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * 银行代号枚举
 * Created by zhangjin on 2017/9/7.
 */
public enum BankSegment {

    ICBC("工商银行", 1001),
    ABC("农业银行", 1002),
    BOC("中国银行", 1003),
    CCB("建设银行", 1004),
    BCM("交通银行", 1005),
    PSBC("邮政储蓄银行",1006),
    CNCB("中信银行", 1007),
    CEB("光大银行", 1008),
    HXB("华夏银行", 1009),
    CMSB("民生银行", 1010),
    PAB("平安银行", 1011),
    CMBC("招商银行", 1012),
    CIB("兴业银行", 1013),
    SPDB("浦发银行", 1014),
    GDB("广发银行", 1017);


    private String name;
    private int  value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    BankSegment(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static String getByValue(Integer value){
        for(BankSegment bankSegment : BankSegment.values()){
            if(value == bankSegment.value){
                return bankSegment.name();
            }
        }
        return null;
    }
}
