package utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import testCase.baseCase;

import java.util.Map;
import java.util.Set;

public class HttpClientUtils {

    private static Logger logger = Logger.getLogger(HttpClientUtils.class);

    public static String callApi(String url,String method,String Params,String ContentType,Map<String,String> header){

            String body=null;
            try {
                if ("post".equalsIgnoreCase(method)){
                    if ("json".equalsIgnoreCase(ContentType)){

                       body= HttpClientUtils.postApi(url, Params,header); }
                   else if("form".equalsIgnoreCase(ContentType)) {
                         String Params1=jsonToString(Params);

                       body= HttpClientUtils.postApiForm(url, Params1,header);

                    }}
                else if ("get".equalsIgnoreCase(method)){
                body=HttpClientUtils.getApi(url,header); }
                else if ("patch".equalsIgnoreCase(method)) {
                       body= HttpClientUtils.patchApi(url, Params,header);
                    }

            }
             catch (Exception e) {
                logger.info(e);
                e.printStackTrace();
            }


        return body;
    }

    /**
     * Post接口
     * @param url
     * @param param
     * @param headers  请求头
     * @return 返回body
     * @throws Exception
     */



    public static String postApi(String url, String param,Map<String,String> headers ) throws Exception {


        HttpPost post = new HttpPost(url);//1.打开postman，创建请求，填写url
      //  post.setHeader("X-Lemonban-Media-Type", "lemonban.v2");
      //  post.setHeader("Content-Type", "application/json");
        //参数添加，get不需要参数
        setHeaders(post, headers);
        //HttpClients为httpClient的客户端工具
        //点击发送，获取报文,获取response
        // String param = "  { \"member_id\":7810579 ,\"amount\": 100}";
        post.setEntity(new StringEntity(param, "utf-8"));
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(post);
        return returnResBody(response);

    }

    /**
     *
     * @param url
     * @param formParams form 表达形式的参数
     * @param headers
     * @return 返回body
     * @throws Exception
     */

    public static String postApiForm(String url, String formParams,Map<String,String> headers) throws Exception {


        HttpPost post = new HttpPost(url);//1.打开postman，创建请求，填写url
        // post.setHeader("X-Lemonban-Media-Type", "lemonban.v2");
       // post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        setHeaders(post,headers);
        post.setEntity(new StringEntity(formParams,"utf-8"));//form 表单
        HttpClient client = HttpClients.createDefault();//HttpClients为httpClient的客户端工具
        //点击发送，获取报文,获取response


        HttpResponse response = client.execute(post);
        return returnResBody(response);

    }

    /**
     * get接口
     * @param url
     * @param headers
     * @return
     * @throws Exception
     */

    public static String getApi(String url,Map<String,String> headers) throws Exception {
        HttpGet get = new HttpGet(url);//1.打开postman，创建请求，填写url
     //   get.setHeader("X-Lemonban-Media-Type", "lemonban.v2");
      //  get.setHeader("Content-Type", "application/json");
        //参数添加，get不需要参数
        setHeaders(get,headers);
       HttpClient client = HttpClients.createDefault();//HttpClients为httpClient的客户端工具
        //点击发送，获取报文,获取response
        // String json = " { \"member_id\": 7810579,\"amount\": 100}";
        //  response.setEntity(new StringEntity(json,"utf-8"));
        HttpResponse response = client.execute(get);

        return returnResBody(response);

    }



    public static String patchApi(String url, String jsonParam,Map<String,String> headers) throws Exception {


    HttpPatch patch = new HttpPatch(url);//1.打开postman，创建请求，填写url
       // patch.setHeader("X-Lemonban-Media-Type","lemonban.v2");
      //  patch.setHeader("Content-Type","application/json");
        setHeaders(patch,headers);
    //参数添加，get不需要参数
    CloseableHttpClient client = HttpClients.createDefault();//HttpClients为httpClient的客户端工具
    //点击发送，获取报文,获取response
  //  String json = " {\"member_id\":7810579 ,\"reg_name\": \"小五\"}";
      patch.setEntity(new StringEntity(jsonParam,"utf-8"));
    HttpResponse response = client.execute(patch);


    return returnResBody(response);
    }

    /**
     *
     * @param jsonStr
     * @return
     */

    public static String jsonToString(String jsonStr){

      Map<String,String> map= JSON.parseObject(jsonStr,Map.class);
      Set<String> setKey= map.keySet();
      String result="";
      for (String element:setKey) {
          result += element + "=" +map.get(element) + "&";
      }
      jsonStr=result.substring(0,result.length()-1);
    return jsonStr;

    }

    /**
     *
     * @param response 接口响应
     * @return 返回响应体
     *
     * @throws Exception
     */

    private static  String returnResBody(HttpResponse response) throws Exception {

        Header[] headers=response.getAllHeaders();//获取响应头HttpEntity entity = response.getEntity();

        HttpEntity entity = response.getEntity();
        //HttpEntity其实相当于一个消息实体，内容是http传送的报文（这里可以说是html，css等等文件）。
        // 这里只需要知道它是用来表征一个http报文的实体就行了，用来发送或接收
        String body = EntityUtils.toString(entity);//报文解析
        int statusCode = response.getStatusLine().getStatusCode();//获取状态码
        logger.info("响应头"+headers+"响应体"+body+"状态码"+statusCode);
        return body;

}

    /**
     *	给请求添加对应的请求头
     * @param request	请求对象（post、get、patch）
     * @param headers	请求头map
     */

  private static void setHeaders(HttpRequest request,Map<String,String> headers) {
      //1、获取所有请求头里面key
      Set<String> keySet = headers.keySet();
      //2、遍历所有键
      for (String key : keySet) {
          //3、把对应键和值设置到request的header中
          request.setHeader(key,headers.get(key));
      }
  }


}
