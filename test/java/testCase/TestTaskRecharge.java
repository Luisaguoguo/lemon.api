package testCase;

import com.alibaba.fastjson.JSONPath;
import entityBase.API;
import entityBase.WriteBackExcel;
import entityBase.Case;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;
import utils.HttpClientUtils;
import utils.sqlUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TestTaskRecharge extends baseCase {

    /*1.参数化， method 为接口请求方法。

     */
    @Test(dataProvider = "datas")
    public void testApiRegister(API api,Case c) {

        String param = paramReplace(c.getParams());
        c.setParams(param);//set 回去，传到call 方法

        String params = paramReplace(c.getSql());
        c.setSql(params);

        Map<String,String> header=new HashMap<>();
        setDefaultHeaders(header);
        getToken(header);

        Object beforeTestResult= sqlUtils.getSQLResult(c.getSql());

      String body= HttpClientUtils.callApi(api.getUrl(), api.getApiMethod(), c.getParams(), api.getContentType(),header);

        WriteBackExcel wbd = new WriteBackExcel(1,c.getCaseID(), 5,body);
        ExcelUtils.wbdlist.add(wbd);
        //响应断言
      boolean responAssert=responseAssert(c.getExpectResult(), body);
        //数据库断言
        Object afterTestResult= sqlUtils.getSQLResult(c.getSql());


        boolean assertSQLFlag =sqlUtils.assertRechargeAmount(c, (BigDecimal) beforeTestResult, (BigDecimal) afterTestResult);
        System.out.println(assertSQLFlag);

        WriteBackExcel wbd1 = new WriteBackExcel(1,c.getCaseID(), 6,responAssert?"PASS":"FAIL");//三元表达式
        ExcelUtils.wbdlist.add(wbd1);

        WriteBackExcel wbd2 = new WriteBackExcel(1,c.getCaseID(), 8,assertSQLFlag?"PASS":"FAIL");
        ExcelUtils.wbdlist.add(wbd2);
    }



    @DataProvider
    public Object[][] datas() {
        Object[][] datas = ExcelUtils.getApiIDAndCase("3");
        return datas;
    }


}
