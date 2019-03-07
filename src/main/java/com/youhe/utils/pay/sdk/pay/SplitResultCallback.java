package com.youhe.utils.pay.sdk.pay;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.youhe.utils.pay.sdk.utils.RsaUtils;

/**
 * @Author : Author
 * @Date : 2018/1/29 15:30
 * @Description : 分账结果查询异步响应
 */
public class SplitResultCallback {

    public String getResult( HttpServletRequest request, HttpServletResponse response) throws Exception {

        String publicPath = "";//公钥地址

        String sign = request.getHeader("x-efps-sign");
        BufferedReader br = request.getReader();
        String str = "", wholeStr = "";
        System.out.println("---------------------------------------------------");
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        System.out.println("body:" + wholeStr);
        System.out.println("sign:" + sign);
        System.out.println("---------------------------------------------------");

        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(wholeStr != null && !wholeStr.equals("") && sign != null && !sign.equals("")){
            boolean result = RsaUtils.vertify(RsaUtils.getPublicKey(publicPath), wholeStr , sign ) ;
            System.out.println("验签结果:" + result);
            if(result){

//                NotifyContent notify = JSONObject.parseObject(wholeStr, NotifyContent.class);

                jsonObject.put("returnCode", "0000");
                jsonObject.put("returnMsg", "");
                writer.print(jsonObject.toJSONString());
                writer.close();
            }else{
                jsonObject.put("returnCode", "-1");
                jsonObject.put("returnMsg", "inLegal data");
                writer.print(jsonObject.toJSONString());
                writer.close();
            }
        }else{
            jsonObject.put("returnCode", "-1");
            jsonObject.put("returnMsg", "inLegal data");
            writer.print(jsonObject.toJSONString());
            writer.close();
        }
        return null;
    }


}
