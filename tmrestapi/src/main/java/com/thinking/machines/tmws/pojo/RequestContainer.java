package com.thinking.machines.tmws.pojo;
import java.util.*;
import javax.servlet.http.*;
public class RequestContainer implements java.io.Serializable 
{
private HttpServletRequest httpServletRequest;
RequestContainer(HttpServletRequest httpServletRequest) 
{
this.httpServletRequest=httpServletRequest; 
}
public static RequestContainer getRequestContainer(HttpServletRequest httpServletRequest)
{
return new RequestContainer(httpServletRequest);
}
public void setAttribute(String name,Object value) 
{
httpServletRequest.setAttribute(name,value); 
}
public Object getAttribute(String name) 
{
return httpServletRequest.getAttribute(name); 
}
public void removeAttribute(String name) 
{
httpServletRequest.removeAttribute(name);
}
public void removeAllAttributes() 
{
Enumeration<String> e=httpServletRequest.getAttributeNames();
while(e.hasMoreElements()) 
{
httpServletRequest.removeAttribute(e.nextElement()); 
}
}
}