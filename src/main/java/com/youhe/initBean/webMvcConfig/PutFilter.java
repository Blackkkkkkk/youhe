package com.youhe.initBean.webMvcConfig;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * 开启HttpPutFormContentFilter
 */
@Component
public class PutFilter extends HttpPutFormContentFilter {
}
