package com.itheima.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
//���������
public class SetAllCharacterEncoding extends AbstractFilter {

	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterConfig cfg = getFilterConfig();
		String encoding = cfg.getInitParameter("encoding");
		if(encoding==null){
			//�û�û�����øò���
			encoding = "UTF-8";
		}
		
		//post����Ӧ����
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset="+encoding);
		
		MyHttpServletRequest mrequest = new MyHttpServletRequest(request);
		
		chain.doFilter(mrequest, response);
	}
	
}
class MyHttpServletRequest extends HttpServletRequestWrapper{
	public MyHttpServletRequest(HttpServletRequest request){
		super(request);
	}

	public String getParameter(String name) {
		String value = super.getParameter(name);
		if(value==null)
			return null;
		if("get".equalsIgnoreCase(super.getMethod())){
			//get��ʽ
			try {
				return new String(value.getBytes("ISO-8859-1"),super.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
}
