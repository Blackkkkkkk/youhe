package com.youhe.utils.pay.sdk.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import com.youhe.utils.pay.sdk.domain.Response;

public class HttpUtil {
    private static final CloseableHttpClient HTTPCLIENT = HttpClients.createDefault();

    private static void debug(String msg, Object... args) {
        if ("debug".equalsIgnoreCase(System.getProperty("sdk.mode"))) {
            msg = String.format(msg, args);
            System.out.println(msg);
        }
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static <T extends Response> T post(String url, String params, Map<String, String> header, Class<T> clazz) throws Exception {
        final long start = System.currentTimeMillis();
        debug("\n---------------------------------------------------------------");
        debug("接口地址：%s", url);



        T responseResult = clazz.newInstance();

        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Content-Type", "application/json");
        for (String key : header.keySet()) {
            httpPost.setHeader(key, header.get(key));
        }
        debug("接口头参数(%d)：%s", System.currentTimeMillis() - start, header);
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        debug("接口报文(%d)：%s", System.currentTimeMillis() - start, params);
        responseResult.setInputJSON(params.toString());
        CloseableHttpResponse response = null;

        try {

            response = HTTPCLIENT.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {

                //验签
                String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
                debug("响应消息(%d)：%s", System.currentTimeMillis() - start, jsonString);
                responseResult.setOutputJSON(jsonString);
                if (response.getFirstHeader("x-efps-sign") == null || response.getFirstHeader("x-efps-sign-no") == null) {
                    responseResult.setReturnCode("-1");
                    responseResult.setReturnMsg(jsonString);
                    return responseResult;
                }

                String signResult = response.getFirstHeader("x-efps-sign").getValue();
                String signNo = response.getFirstHeader("x-efps-sign-no").getValue();
                debug("响应证书号：%s", signNo);
                debug("响应签名：%s", signResult);

                if (signResult != null && signResult != "" && verify(jsonString, signResult, com.youhe.utils.pay.sdk.utils.Config.getPublicKeyFile().getAbsolutePath())) {
                    responseResult = JSONObject.parseObject(jsonString, clazz);
                } else {
                    responseResult.setReturnCode("-1");
                    responseResult.setReturnMsg("验签失败");
                }



            } else {
                responseResult.setReturnCode(String.valueOf(state));
                debug("请求返回：" + state + "(" + url + ")");
            }
        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        if (responseResult != null) {
            debug("接口返回碼%s，詳細：%s", responseResult.getReturnCode(), JSONObject.toJSONString(responseResult));
        } else {
            debug("接口調用失敗！");
        }
        return responseResult;
    }


    /**
     * 验签
     *
     * @param content 原字符串
     * @param sign    已签名字符串
     * @return
     * @throws Exception
     */
    public static boolean verify(String content, String sign, String publicKey) throws Exception {

        boolean flag = false;
        if (content != null && !content.equals("") && sign != null && !sign.equals("")) {

            boolean result = com.youhe.utils.pay.sdk.utils.RsaUtils.vertify(com.youhe.utils.pay.sdk.utils.RsaUtils.getPublicKey(publicKey), content, sign);
            debug("验签结果:" + result);
            if (result) {
                flag = true;
            }
        }
        return flag;
    }
}
