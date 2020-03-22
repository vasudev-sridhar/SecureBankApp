package com.asu.secureBankApp.Config;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInterceptor extends HandlerInterceptorAdapter implements Filter {

	String body;
	/*private static class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

		private byte[] rawData;
		private HttpServletRequest request;
		private ResettableServletInputStream servletStream;

		public ResettableStreamHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
			this.servletStream = new ResettableServletInputStream();
		}

		public void resetInputStream() {
			servletStream.stream = new ByteArrayInputStream(rawData);
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			if (rawData == null) {
				rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return servletStream;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			if (rawData == null) {
				rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return new BufferedReader(new InputStreamReader(servletStream));
		}

		private class ResettableServletInputStream extends ServletInputStream {

			private InputStream stream;

			@Override
			public int read() throws IOException {
				return stream.read();
			}

			@Override
		    public boolean isFinished() {
		        try {
		            int available = stream.available();
		            return available == 0;
		        } catch (IOException e) {
		            return true;
		        }
		    }

		    @Override
		    public boolean isReady() {
		        return false;
		    }

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub
				
			}
		}
	}*/

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object handler) throws Exception {

		System.out.println("Called before handler method request completion," + " before rendering the view");
		String test = "";

//		RequestWrapper request2 = new RequestWrapper((HttpServletRequest) request);
//		resettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(request);
//		if ("POST".equalsIgnoreCase(request2.getMethod())) {
//			//test = IOUtils.toString(request2.getReader());
//			 //Scanner s = new Scanner(request2.getInputStream(), "UTF-8").useDelimiter("\\A");
//	         //test = s.hasNext() ? s.next() : "";
//			test = request2.getBody();
//		}
//		System.out.println(test);
		// wrappedRequest.resetInputStream();
		//request = request2;
//	      Enumeration<String> params = request.getAttributeNames(); 
//	      while(params.hasMoreElements()){
//	       String paramName = params.nextElement();
//	       System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
//	      }
		// Integer id = Integer.parseInt(request.getParameter("id"));
		// System.out.println("prehandle id: " + id);
		return true;
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//		System.out.println("doFilter");
//		RequestWrapper requestWrapper = null;
//        if(request instanceof HttpServletRequest) {
//            requestWrapper = new RequestWrapper((HttpServletRequest) request);
//        }
//        //Get the stream in the request, convert the fetched string into a stream, and put it into the new request object.
//                 // Pass the new request object in the chain.doFiler method
//        String test = "";
//        if ("POST".equalsIgnoreCase(requestWrapper.getMethod())) {
//			//test = IOUtils.toString(request2.getReader());
//			 Scanner s = new Scanner(requestWrapper.getInputStream(), "UTF-8").useDelimiter("\\A");
//	         test = s.hasNext() ? s.next() : "";
//			test = requestWrapper.getBody();
//			body = test;
//		}
//        System.out.println(test);
//        System.out.println(requestWrapper.getRequestURL());
//        if(requestWrapper == null) {
              chain.doFilter(request, response);
//        } else {
//            chain.doFilter(requestWrapper, response);
//        }
    }

}