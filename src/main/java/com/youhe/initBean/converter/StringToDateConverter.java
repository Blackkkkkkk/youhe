package com.youhe.initBean.converter;

import com.youhe.initBean.aop.TransactionAdviceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaoqiang on 2019/1/2.
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final Logger log = LoggerFactory.getLogger(TransactionAdviceConfig.class);

    public final static String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public final static String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    //string 转data
    @Override
    public Date convert(String source) {
        // Assert.hasText(source, "Null or empty date string");
        Date date = parseDate(source, DATE_FORMAT_SECOND);
        return date;
    }

    public Date parseDate(String dateString, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            log.info("时间格式错误");
        }

        return null;
    }



}