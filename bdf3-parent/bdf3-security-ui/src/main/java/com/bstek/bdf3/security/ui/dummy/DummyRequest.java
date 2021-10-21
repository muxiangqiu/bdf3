package com.bstek.bdf3.security.ui.dummy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class DummyRequest extends HttpServletRequestWrapper {
  private static final HttpServletRequest UNSUPPORTED_REQUEST = (HttpServletRequest) Proxy
      .newProxyInstance(DummyRequest.class.getClassLoader(),
          new Class[] {HttpServletRequest.class},
          new UnsupportedOperationExceptionInvocationHandler());

  private String requestURI;
  private String contextPath = "";
  private String servletPath = "/";
  private String pathInfo;
  private String queryString;
  private String method;
  private HttpSession session = new DummySession();

  private Hashtable<String, Object> attributes = new Hashtable<>();

  public DummyRequest() {
    super(UNSUPPORTED_REQUEST);
  }

  public String getCharacterEncoding() {
    return "UTF-8";
  }

  public Object getAttribute(String attributeName) {
    return attributes.get(attributeName);
  }

  public void setAttribute(String attributeName, Object value) {
    attributes.put(attributeName, value);
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  public Enumeration getAttributeNames() {
    return attributes.keys();
  }

  public void setRequestURI(String requestURI) {
    this.requestURI = requestURI;
  }

  public void setPathInfo(String pathInfo) {
    this.pathInfo = pathInfo;
  }

  @Override
  public String getRequestURI() {
    return this.requestURI;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  @Override
  public String getContextPath() {
    return this.contextPath;
  }

  public void setServletPath(String servletPath) {
    this.servletPath = servletPath;
  }

  @Override
  public String getServletPath() {
    return this.servletPath;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  @Override
  public String getMethod() {
    return this.method;
  }

  @Override
  public String getPathInfo() {
    return this.pathInfo;
  }

  @Override
  public String getQueryString() {
    return this.queryString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  @Override
  public String getServerName() {
    return null;
  }

  @Override
  public HttpSession getSession(boolean create) {
    if (session == null && create) {
      session = new DummySession();
    }
    return session;
  }

  @Override
  public HttpSession getSession() {
    return session;
  }


}


final class UnsupportedOperationExceptionInvocationHandler implements InvocationHandler {
  public Object invoke(Object proxy, Method method, Object[] args) {
    System.out.println(method + " is not supported");
    return null;
    // throw new UnsupportedOperationException(method + " is not supported");
  }
}
