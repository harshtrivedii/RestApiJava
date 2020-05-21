package com.thinking.machines.tmws.pojo;
import java.util.*;
import javax.servlet.*;
public class ApplicationContainer implements java.io.Serializable 
{
private ServletContext servletContext;
ApplicationContainer(ServletContext servletContext) 
{
this.servletContext=servletContext; 
}
public static ApplicationContainer getApplicationContainer(ServletContext servletContext)
{
return new ApplicationContainer(servletContext);
}
public void setAttribute(String name,Object value) 
{
this.servletContext.setAttribute(name,value); 
}
public Object getAttribute(String name) 
{
return this.servletContext.getAttribute(name); 
}
public void removeAttribute(String name) 
{
this.servletContext.removeAttribute(name); 
}
public void removeAllAttributes() 
{
Enumeration<String> e=this.servletContext.getAttributeNames();
while(e.hasMoreElements()) 
{
this.servletContext.removeAttribute(e.nextElement()); 
}
}
}