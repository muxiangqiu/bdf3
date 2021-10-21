package com.bstek.bdf3.security.ui.dummy;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class DummySession implements HttpSession {
  private Hashtable<String, Object> attributes = new Hashtable<>();

  private ServletContext servletContext;

  private String id = UUID.randomUUID().toString();

  public DummySession() {

  }

  public DummySession(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return servletContext;
  }

  @Override
  public void setMaxInactiveInterval(int interval) {

  }

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  @Override
  public Object getValue(String name) {
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return attributes.keys();
  }

  @Override
  public String[] getValueNames() {
    return new String[] {};
  }

  @Override
  public void setAttribute(String name, Object value) {
    attributes.put(name, value);
  }

  @Override
  public void putValue(String name, Object value) {
    attributes.put(name, value);
  }

  @Override
  public void removeAttribute(String name) {
    attributes.remove(name);
  }

  @Override
  public void removeValue(String name) {

  }

  @Override
  public void invalidate() {

  }

  @Override
  public boolean isNew() {
    return false;
  }

}
