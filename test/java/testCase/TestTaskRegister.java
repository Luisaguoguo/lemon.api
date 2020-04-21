package testCase;

import entityBase.API;
import entityBase.Case;
import entityBase.WriteBackExcel;
import entityBase.enviromentParam;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.mustache.Value;
import sun.font.TrueTypeFont;
import utils.ExcelUtils;
import utils.HttpClientUtils;
import utils.sqlUtils;
import Constant.constant;

import java.util.HashMap;
import java.util.Map;

import static utils.sqlUtils.registerAssertSql;

public class TestTaskRegister extends baseCase {

    /*1.参数化， method 为接口请求方法。


     */
    @Test(dataProvider = "datas")
    public void testApiRegister(API api, Case c) {
        //参数化

        String param = paramReplace(c.getParams());
        c.setParams(param);//set 回去，传到call 方法

        String params = paramReplace(c.getSql());
        c.setSql(params);




        Map<String, String> header = new HashMap<>();
        setDefaultHeaders(header);
        //注册前sql语句查询结果
        Object beforeTestResult = sqlUtils.getSQLResult(c.getSql());


        String body = HttpClientUtils.callApi(api.getUrl(), api.getApiMethod(), c.getParams(), api.getContentType(), header);
        //会写excel response
        WriteBackExcel wbd = new WriteBackExcel(1, c.getCaseID(), 5, body);
        ExcelUtils.wbdlist.add(wbd);
        //response 断言
        boolean responAssert=responseAssert(c.getExpectResult(), body);
        //注册后sql 查询后结果
        Object afterTestResult = sqlUtils.getSQLResult(c.getSql());
        //sql语句断言
        boolean assertSQLFlag = false;
        if (StringUtils.isNoneBlank(c.getSql())) {

            assertSQLFlag = registerAssertSql(beforeTestResult, afterTestResult);
            System.out.println(assertSQLFlag);

        }
        //回写response 断言结果
        WriteBackExcel wbd1 = new WriteBackExcel(1, c.getCaseID(), 6, responAssert ? "PASS" : "FAIL");//三元表达式
        ExcelUtils.wbdlist.add(wbd1);
        //回写sql 查询断言结果
        WriteBackExcel wbd2 = new WriteBackExcel(1, c.getCaseID(), 8, assertSQLFlag ? "PASS" : "FAIL");
        ExcelUtils.wbdlist.add(wbd2);


    }




    @DataProvider
    public Object[][] datas() {
        Object[][] datas = ExcelUtils.getApiIDAndCase("1");
        return datas;
    }

}
