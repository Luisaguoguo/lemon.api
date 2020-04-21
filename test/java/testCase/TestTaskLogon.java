package testCase;

import com.alibaba.fastjson.JSONPath;
import entityBase.API;
import entityBase.WriteBackExcel;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;
import utils.HttpClientUtils;
import entityBase.Case;
import Constant.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static utils.ExcelUtils.easyApiReadExcel;

public class TestTaskLogon extends baseCase {

    /*1.参数化， method 为接口请求方法。
     */
    @Test(dataProvider = "datas")
    public void testApiRegister(API api, Case c) {
        //参数化
        String param = paramReplace(c.getParams());
        c.setParams(param);//set 回去，传到call 方法

        String params = paramReplace(c.getSql());
        c.setSql(params);

        Map<String,String> header=new HashMap<>();
        setDefaultHeaders(header);

      String body= HttpClientUtils.callApi(api.getUrl(), api.getApiMethod(), c.getParams(), api.getContentType(),header);
       //${token} 将要通过jsonPath 定位body中的token字段的key值
        storeEnvVar(body, constant.TOKEN,"$.data.token_info.token");

        storeEnvVar(body,constant.MEMBER_ID,"$.data.id");

        WriteBackExcel wbd = new WriteBackExcel(1,c.getCaseID(), 5,body);
        ExcelUtils.wbdlist.add(wbd);

        boolean responAssert=responseAssert(c.getExpectResult(), body);
        WriteBackExcel wbd1 = new WriteBackExcel(1,c.getCaseID(), 6,responAssert?"PASS":"FAIL");//三元表达式
        ExcelUtils.wbdlist.add(wbd1);

    }


    @DataProvider
    public Object[][] datas() {
        Object[][] datas = ExcelUtils.getApiIDAndCase("2");
        return datas;
    }





}
