package com.itheima.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractFilter implements Filter {
	private FilterConfig filterConfig;
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		init();
	}
	public void init()throws ServletException{
		
	}
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		try{
			request = (HttpServletRequest)req;
			response = (HttpServletResponse)resp;
		}catch(Exception e){
			throw new RuntimeException("non-http request or response");
		}
		doFilter(request, response, chain);
	}
	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException;
	public void destroy() {

	}

}
