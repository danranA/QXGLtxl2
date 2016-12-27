package com.cz.txl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncodingFilter implements Filter{
	Log log = LogFactory.getLog(EncodingFilter.class);
	String charset = null;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (charset != null && !"".equals(charset)){
			req.setCharacterEncoding(charset);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String charset = config.getInitParameter("charset");
		this.charset = charset;
		log.info("编码过滤器初始参数:"+charset);
	}

}
