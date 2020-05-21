package com.thinking.machines.tmws.pojo;
import java.util.*;
import javax.servlet.http.*;
public class SessionContainer implements java.io.Serializable 
{
private HttpSession httpSession;
SessionContainer(HttpSession httpSession) 
{
this.httpSession=httpSession; 
}
public static SessionContainer getSessionContainer(HttpSession httpSession)
{
return new SessionContainer(httpSession);
}
public void setAttribute(String name,Object value) 
{
httpSession.setAttribute(name,value); 
}
public Object getAttribute(String name) 
{
return httpSession.getAttribute(name); 
}
public void removeAttribute(String name) 
{
httpSession.removeAttribute(name); 
}
public void removeAllAttributes() 
{
Enumeration<String> e=httpSession.getAttributeNames();
while(e.hasMoreElements()) 
{
httpSession.removeAttribute(e.nextElement()); 
}
}
public void end() 
{
httpSession.invalidate(); 
}
public String getId() 
{
return httpSession.getId(); 
}
public void setSessionTimeout(int time,SessionTimeout sessionTimeout) 
{
if(sessionTimeout==SessionTimeout.SECONDS) 
{
httpSession.setMaxInactiveInterval(time); 
}
if(sessionTimeout==SessionTimeout.MINUTES) 
{
httpSession.setMaxInactiveInterval(time*60); 
}
if(sessionTimeout==SessionTimeout.HOURS) 
{
httpSession.setMaxInactiveInterval(time*60*60); 
}
}
public long getLastAccessedTime()
{
return httpSession.getLastAccessedTime();
}
}