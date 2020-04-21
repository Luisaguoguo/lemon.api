package Constant;

public class constant {

    /**
     * 常量=恒定不变
     *final修饰类，类不能被技能，修饰方法方法不能被重写，修饰变量变成常量
     *常量 只能赋值一次,基本数据类型值不能改变，引用数据地址值不能改变，但是可以调用方法
     *常量所有英文大写，单词用下划线分割
     *
     */

    public static final String EXCEL_PATH="/Users/44104909_edgar/com.lemon.APIV7/src/main/resources/cases_v5.xlsx";
    //鉴权请求头
    public static final String Media_Type="lemonban.v2";

    public static String JDBC_URL="jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";

    public static String JDBC_USERNAME="future";
    public static String JDBC_PASSWORD="123456";

    public static String LOGON_MOBILE_PHONE="${mobile_phone}";//参数化的key值
    public static String LOGON_PASSWORD="${password}";
    public static String REGISTER_MOBILE_PHONE="${mobile_phone}";
    public static String REGISTER_PASSWORD="${password}";
    public static String MEMBER_ID="${member_id}";
    public static String TOKEN="${token}";


}
