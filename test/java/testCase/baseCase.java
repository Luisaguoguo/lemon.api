package testCase;

import Constant.constant;
import com.alibaba.fastjson.JSONPath;
import entityBase.API;
import entityBase.Case;
import entityBase.Member;
import entityBase.enviromentParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utils.ExcelUtils;
import utils.sqlUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static utils.ExcelUtils.easyApiReadExcel;


public class baseCase {

    private Logger logger = Logger.getLogger(baseCase.class);

    /**
     * 设置默认请求头
     * "X-Lemonban-Media-Type","lemonban.v2"
     * "Content-Type","application/json"
     * @param header
     */
    public void setDefaultHeaders(Map<String, String> header) {
        header.put("X-Lemonban-Media-Type", constant.Media_Type);
        header.put("Content-Type","application/json");
    }

    /**
     * 存储token值 在logon 回来的response 中从body里面通过jsonpath获取token值，并存储到map中，
     * @param body
     * @param envKey 环境变量key值
     * @param jsonPath 要读取的reponse的字段通过jsonPath 定位元素  ${}
     */
    public void storeEnvVar(String body,String envKey,String jsonPath) {
        Object value= JSONPath.read(body,jsonPath);//从body中取出token值
        if (value!=null) {

            enviromentParam.env.put(envKey, value.toString());
        }
        logger.info(envKey+"值为"+value);
    }

    public String getTokenAuth(Map<String, String> header,String authKey,String envKey) {
        String token= enviromentParam.env.get(envKey);
        if (StringUtils.isNoneBlank(token)) {


            header.put(authKey,"Bearer "+token);
        }
        return token;

    }

    /**获取token
     *
     * @param header  从之前的env map中读取token
     * @return  返回token值
     */

    public void getToken(Map<String, String> header) {
        String token= enviromentParam.env.get(constant.TOKEN);
        if (StringUtils.isNoneBlank(token)) {
            header.put("Authorization","Bearer "+token);
        }
       logger.info("token值为"+"Bearer "+token);

        }

    /**
     *响应断言
     * @param expectData 期望数据
     * @param body
     * @return
     */

    public boolean responseAssert(String expectData, String body) {
        //定义返回值
        boolean result = true;
        //切割期望值
        String[] expectArray = expectData.split("@@@");
        //循环切割之后的数组
        for (String expectArray1 : expectArray) {
           // 如果响应体包含期望值，则断言成功
            result = body.contains(expectArray1);
           logger.info("responseAssert test result is " + result);
            if (result == false) {
                break;
            }
        }
        return result;

    }

    /**
     * 	参数化替换方法
     * @param param	需要替换的字符串
     * @return			替换之后的字符串
     */

    public String paramReplace(String param) {
        if (StringUtils.isBlank(param)) {
            return param;
        }
        Set<String> keySet=enviromentParam.env.keySet();
        //遍历map集合
        for (String s : keySet) {
            //s是就是参数化的${}占位符，value就是要参数化的value；
            //把需要替换的字符串进行执行replace{key.value}.value=enviromentParam.env.get(s)
            param=param.replace(s,enviromentParam.env.get(s));
        }

      //  param.replace("mobile_phone",constant.REGISTER_MOBILE_PHONE);
        return param;
    }


    /**
     *项目初始化
     */
    @BeforeSuite
    public void initial() throws Exception {
        logger.info("+++++项目初始化++++++");
        //所有API 集合 读取
        ExcelUtils.apiList = easyApiReadExcel(0, 1, API.class);
        //读取所有case集合
        ExcelUtils.caseList=easyApiReadExcel(1, 1, Case.class);
        ExcelUtils.wbdlist=new ArrayList<>();

       /* enviromentParam.env.put(constant.LOGON_MOBILE_PHONE,enviromentParam.getRegisterPhone());
        enviromentParam.env.put(constant.LOGON_PASSWORD,"12345678");
        Member member=sqlUtils.getSQLRandomResult();
        enviromentParam.env.put(constant.REGISTER_MOBILE_PHONE,member.getMobile_phone());
       enviromentParam.env.put(constant.REGISTER_PASSWORD,member.getPwd());//密码必须可逆才能查到密码
     */
        logger.info("+++++参数初始化++++++");
        Properties prop = new Properties();
        //1、加载paras.properties文件
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        //2、获取prop所有的key
        Set<Object> keySet = prop.keySet();
        for (Object key : keySet) {
            String value = prop.get(key).toString();
            //3、把prop的key和value存入 env
            enviromentParam.env.put(key.toString(),value);
        }
        //关流
        fis.close();
    }


    /**
     * 在程序结束后写入运行结果到excel
     */
    @AfterSuite
    public void Finish(){
        ExcelUtils.writeBack();//直接回写不需要传参

        logger.info("+++++结果写入++++项目结束++++++");

    }

}
