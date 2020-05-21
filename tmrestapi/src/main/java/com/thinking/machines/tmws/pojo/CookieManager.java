package com.thinking.machines.tmws.pojo;
import javax.servlet.http.*;
public class CookieManager implements java.io.Serializable
{
private HttpServletRequest request;
public CookieManager(HttpServletRequest request)
{
this.request=request;
}
}