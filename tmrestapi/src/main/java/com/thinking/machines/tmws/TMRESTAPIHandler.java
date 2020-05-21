package com.thinking.machines.tmws;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import java.io.*;
import com.google.gson.*;
import com.thinking.machines.tmws.pojo.*;
import com.thinking.machines.tmws.annotations.*;
public class TMRESTAPIHandler extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
process(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
process(request,response);
}
// for another methods
public void process(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("Process starts........................");
try
{
String path=request.getPathInfo();
System.out.println("Process starts........................"+path);
ServletContext servletContext=getServletContext();
System.out.println("Process starts......................1..");
Model dataModel=(Model)servletContext.getAttribute(Model.id);
System.out.println("Process starts....................2....");
ServiceModule serviceModule=dataModel.getServiceModule(path);
System.out.println("Process starts....................3....");
if(serviceModule==null)
{
System.out.println("Service Module==null me gyaaaaaaaaaaaaaaaaaaaaaaa");
response.sendError(HttpServletResponse.SC_NOT_FOUND);
return;
}
Service service=serviceModule.getService(path);
System.out.println("Path is : "+service.getPath());
String requestMethodType=request.getMethod();
if(requestMethodType.equalsIgnoreCase("GET"))
{
if(!service.allowGet())
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
return;
}
}else if(requestMethodType.equalsIgnoreCase("POST"))
{
if(!service.allowPost())
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
return;
}
}
Object serviceObject=serviceModule.getServiceObject(servletContext,request,service);
if(serviceObject==null)
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
if(serviceModule.isSecured() || service.isSecured())
{
// some code
}
System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
Object arguments[]=service.getArguments(servletContext,request);
for(Object o:arguments) System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+o);
try
{
Method method=service.getMethod();
Object result=method.invoke(serviceObject,arguments);
System.out.println("Result : "+result);
/*if(service.isForwarding()) // forwarding logic starts here
{
String forwardTo=service.getForwardTo();
if(forwardTo==null)
{
if(service.getReturnType().equals(void.class))
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
if(result==null)
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
forwardTo=result.toString();
if(forwardTo.trim().length()==0)
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
}
if(dataModel.containsPath(forwardTo)) // can we change this to pick info from service object and decide
{
String prefix=request.getServletPath();
RequestDispatcher requestDispatcher=request.getRequestDispatcher(prefix+forwardTo);
requestDispatcher.forward(request,response);
return;
}
else
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher(forwardTo);
requestDispatcher.forward(request,response);
return;
}
}
*/
  // forwarding logic ends here
// if not forwarding part starts here
PrintWriter pw=response.getWriter();
if(service.getResponseType()==ResponseType.JSON)
{
response.setContentType("application/json");
pw.print(new Gson().toJson(result));
return;
}
if(service.getResponseType()==ResponseType.XML)
{
return;
}
if(service.getResponseType()==ResponseType.FILE)
{
/*
return type -> ResponseFile (No Issues)
return type -> File or File[]
File : Determine mime type and set content type and
write
File [] : zip all and send the zip file (set content type as zip)

what if returned value is null in case of (ResponseFile, File or File[])
in that case SC_404

return type is not either of the above
convert it to JSON/XML/CSV, set file name as something.json/xml/csv
set it for download

what i return type is void (we should not consider it as a service
or ignore the response type, we will consider the response type as NONE)

@Res
return_type method

*/
return;
}
if(service.getResponseType()==ResponseType.HTML)
{
/*
if instance of result is of String/primitive type, just write it as text/html
if result is null, then write empty string as text/html
if result is of file [] type (read (all) and write (all)), if does not exist _SC_INTERNAL_ERROR

Note : Consider the discussion we went through during the classroom session regarding the following scenarios

a file xyz.data
sam <cool> tim

a file xyz.html or xyz.htm
sam <cool> tim
Now what if the return type represents a Class (other than wrapper/String)

toString()

*/
pw.println(result);
return;
}
if(service.getResponseType()==ResponseType.NONE)
{
/*
what if return type is not void
we will ignore the returned material
send back 200 without any content, is it possible ????
if it is possible, then why not invoke the service on separate 
thread
*/
return;
}
// if not forwarding part ends here
}catch(Throwable t)
{
t.printStackTrace();
/*Throwable t=invocationTargetException.getCause();
if(service.hasExceptionHandler())
{
if(service.isExceptionHandlerAService())
{
String prefix=request.getServletPath(); 
request.getRequestDispatcher(prefix+service.getExceptionHandler()).forward(request,response);
}
else
{
request.getRequestDispatcher(service.getExceptionHandler()).forward(request,response);
}
return;
}
else
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
}catch(IllegalAccessException illegalAccessException)
{
System.out.println("IllegalAccessException shouldn't have happened : "+illegalAccessException);
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
*/



}//processUec ends


}catch(Throwable t)
{
t.printStackTrace();
}
System.out.println("Process ends........................");
}
}
