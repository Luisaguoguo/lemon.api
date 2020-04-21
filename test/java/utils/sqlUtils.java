package utils;

import com.alibaba.fastjson.JSONPath;
import entityBase.Case;
import entityBase.Member;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import Constant.constant;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class sqlUtils {

    //数据库查询

    /**
     * 单个数据库查询
     * @param SQL
     * @return
     */
    public static Object getSQLResult(String SQL){

        if (StringUtils.isBlank(SQL)){
            return null;
        }
        //通过dbutil 建立对象，准备查询数据库
        QueryRunner queryRunner=new QueryRunner();

        //通JDBC 连接数据库
        Connection conn=JDBCUtils.getConnection();
        Object result=null;
        try {
            //执行sql 语句查询
            result=queryRunner.query(conn,SQL,new ScalarHandler<Long>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
    return result;
    }

    /**
     *sql查询一个随机的Member 对象
     *
     * @return
     */

    public static Member getSQLRandomResult(){

        String SQL="select * from member order by rand() LIMIT 1;";
        //通过dbutil 建立对象，准备查询数据库
        QueryRunner queryRunner=new QueryRunner();

        //通JDBC 连接数据库
        Connection conn=JDBCUtils.getConnection();
        Member result=null;
        try {
            //执行sql 语句查询
            result=queryRunner.query(conn,SQL,new BeanHandler<Member>(Member.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**注册断言响应
     *
     * @param beforeTestResult 注册前数据库查询结果
     * @param afterTestResult  注册后数据库查询结果
     * @return
     */

    public static boolean registerAssertSql(Object beforeTestResult, Object afterTestResult) {
        Long before_Value= (Long) beforeTestResult;
        Long after_Value= (Long) afterTestResult;

        if (before_Value== 0 && after_Value == 1 ){

            return true;

        }
        return false;
    }

    /**
     * 充值sql查询断言
     * @param c
     * @param beforeTestResult 充值之前的数据
     * @param afterTestResult  充值之后的数据
     * @return
     */

    public static boolean assertRechargeAmount(Case c, BigDecimal beforeTestResult, BigDecimal afterTestResult) {
        //通jsonpath 读取充值金额
        if (beforeTestResult==null||afterTestResult==null){
            return false;

        }
        String param=c.getParams();
        String amount1= JSONPath.read(param,"$.amount").toString();
        BigDecimal amount=new BigDecimal(amount1);
        BigDecimal beforeRechargeAmount= beforeTestResult;
        BigDecimal afterRechargeAmount= afterTestResult;
        BigDecimal result=afterRechargeAmount.subtract(beforeRechargeAmount);
        if(amount.compareTo(result)==0) {
            return true;
        }
        return false;
    }
}
