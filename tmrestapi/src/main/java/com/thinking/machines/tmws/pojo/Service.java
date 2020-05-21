package com.thinking.machines.tmws.pojo;
import java.lang.reflect.*;
import java.util.*;
import com.thinking.machines.tmws.annotations.*;
import javax.servlet.http.*;
import javax.servlet.*;
public class Service implements java.io.Serializable
{
private String path;
private Method method;
private boolean injectApplicationContainer;
private boolean injectSessionContainer;
private boolean injectRequestContainer;
private boolean injectCookieManager;
private boolean injectContextDirectory;
private boolean allowGet;
private boolean allowPost;
private boolean isSecured;
private Guard guard;
private Class [] parameters;
private int applicationParameterIndexes[];
private int sessionParameterIndexes[];
private int requestParameterIndexes[];
private int cookieManagerParameterIndexes[];
private int contextDirectoryParameterIndexes[];
private int propertyParameterIndexes[];
private Property[] propertyParameters;
private AutoWired[] autoWiredParameters;
private int autoWiredParameterIndexes[];
private Class returnType;
private ResponseType responseType;
private boolean isForwarding;
private String forwardTo;
private boolean isPublic;
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setMethod(Method method)
{
this.method=method;
}
public Method getMethod()
{
return this.method;
}
public void setInjectApplicationContainer(boolean injectApplicationContainer)
{
this.injectApplicationContainer=injectApplicationContainer;
}
public boolean getInjectApplicationContainer()
{
return this.injectApplicationContainer;
}
public void setInjectSessionContainer(boolean injectSessionContainer)
{
this.injectSessionContainer=injectSessionContainer;
}
public boolean getInjectSessionContainer()
{
return this.injectSessionContainer;
}
public void setInjectRequestContainer(boolean injectRequestContainer)
{
this.injectRequestContainer=injectRequestContainer;
}
public boolean getInjectRequestContainer()
{
return this.injectRequestContainer;
}
public void setInjectCookieManager(boolean injectCookieManager)
{
this.injectCookieManager=injectCookieManager;
}
public boolean getInjectCookieManager()
{
return this.injectCookieManager;
}
public void setInjectContextDirectory(boolean injectContextDirectory)
{
this.injectContextDirectory=injectContextDirectory;
}
public boolean getInjectContextDirectory()
{
return this.injectContextDirectory;
}
public void allowGet(boolean allowGet)
{
this.allowGet=allowGet;
}
public boolean allowGet()
{
return this.allowGet;
}
public void allowPost(boolean allowPost)
{
this.allowPost=allowPost;
}
public boolean allowPost()
{
return this.allowPost;
}
public void isSecured(boolean isSecured)
{
this.isSecured=isSecured;
}
public boolean isSecured()
{
return this.isSecured;
}
public void setGuard(Guard guard)
{
this.guard=guard;
}
public Guard getGuard()
{
return this.guard;
}
public void setParameters(Class[] parameters)
{
this.parameters=parameters;
}
public Class[] getParameters()
{
return this.parameters;
}
public void setApplicationParameterIndexes(int[] applicationParameterIndexes)
{
this.applicationParameterIndexes=applicationParameterIndexes;
}
public int[] getApplicationParameterIndexes()
{
return this.applicationParameterIndexes;
}
public void setSessionParameterIndexes(int[] sessionParameterIndexes)
{
this.sessionParameterIndexes=sessionParameterIndexes;
}
public int[] getSessionParameterIndexes()
{
return this.sessionParameterIndexes;
}
public void setRequestParameterIndexes(int[] requestParameterIndexes)
{
this.requestParameterIndexes=requestParameterIndexes;
}
public int[] getRequestParameterIndexes()
{
return this.requestParameterIndexes;
}
public void setCookieManagerParameterIndexes(int[] cookieManagerParameterIndexes)
{
this.cookieManagerParameterIndexes=cookieManagerParameterIndexes;
}
public int[] getCookieManagerParameterIndexes()
{
return this.cookieManagerParameterIndexes;
}
public void setContextDirectoryParameterIndexes(int[] contextDirectoryParameterIndexes)
{
this.contextDirectoryParameterIndexes=contextDirectoryParameterIndexes;
}
public int[] getContextDirectoryParameterIndexes()
{
return this.contextDirectoryParameterIndexes;
}
public void setPropertyParameterIndexes(int[] propertyParameterIndexes)
{
this.propertyParameterIndexes=propertyParameterIndexes;
}
public int[] getPropertyParameterIndexes()
{
return this.propertyParameterIndexes;
}
public void setPropertyParameters(Property[] propertyParameters)
{
this.propertyParameters=propertyParameters;
}
public Property[] getPropertyParameters()
{
return this.propertyParameters;
}
public void setAutoWiredParameters(AutoWired[] autoWiredParameters)
{
this.autoWiredParameters=autoWiredParameters;
}
public AutoWired[] getAutoWiredParameters()
{
return this.autoWiredParameters;
}
public void setAutoWiredParameterIndexes(int[] autoWiredParameterIndexes)
{
this.autoWiredParameterIndexes=autoWiredParameterIndexes;
}
public int[] getAutoWiredParameterIndexes()
{
return this.autoWiredParameterIndexes;
}
public void setReturnType(Class returnType)
{
this.returnType=returnType;
}
public Class getReturnType()
{
return this.returnType;
}
public void setResponseType(ResponseType responseType)
{
this.responseType=responseType;
}
public ResponseType getResponseType()
{
return this.responseType;
}
public void isForwarding(boolean isForwarding)
{
this.isForwarding=isForwarding;
}
public boolean isForwarding()
{
return this.isForwarding;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public String getForwardTo()
{
return this.forwardTo;
}
public void isPublic(boolean isPublic)
{
this.isPublic=isPublic;
}
public boolean isPublic()
{
return this.isPublic;
}
public Object[] getArguments(ServletContext servletContext,HttpServletRequest request)
{
System.out.println("getArguments 11111111111");
if(parameters==null) return new Object[0];
System.out.println("getArguments 2222222222222");
Object arguments[]=new Object[parameters.length];
if(injectApplicationContainer)
{
ApplicationContainer applicationContainer=new ApplicationContainer(servletContext);
for(int i:applicationParameterIndexes) arguments[i]=applicationContainer;
}
System.out.println("getArguments 3333333333");
if(injectSessionContainer)
{
HttpSession session=request.getSession();
SessionContainer sessionContainer=new SessionContainer(session);
for(int i:sessionParameterIndexes) arguments[i]=sessionContainer;
}
if(injectRequestContainer)
{
RequestContainer requestContainer=new RequestContainer(request);
for(int i:requestParameterIndexes) arguments[i]=requestContainer;
}
if(injectCookieManager)
{
CookieManager cookieManager=new CookieManager(request);
for(int i:cookieManagerParameterIndexes) arguments[i]=cookieManager;
}
if(injectContextDirectory)
{
Model model=(Model)servletContext.getAttribute(Model.id);
ContextDirectory contextDirectory=model.getContextDirectory();
for(int i:contextDirectoryParameterIndexes) arguments[i]=contextDirectory;
}
if(propertyParameterIndexes!=null)
{
System.out.println("Property parameter indexes wale if me fasa...............................");
int jj=0;
String propertyName;
String[] propertyValues;
for(int i:propertyParameterIndexes)
{
propertyName=propertyParameters[jj].value();
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+propertyName);
// change the logic to getParameterValues
propertyValues=request.getParameterValues(propertyName);
if(propertyValues!=null)
{
arguments[i]=parseParamValues(propertyValues,parameters[i]);
}
jj++;
}
}
if(autoWiredParameterIndexes!=null)
{
int jjjj=0;
for(int i:autoWiredParameterIndexes)
{
String name=autoWiredParameters[jjjj].name();
jjjj++;
Object object=null;
if(name.length()>0)
{
object=request.getAttribute(name);
if(object==null) object=request.getSession().getAttribute(name);
if(object==null) object=servletContext.getAttribute(name);
arguments[i]=object;
}
}
}
for(int jk=0;jk<arguments.length;jk++)
{
System.out.println("getArguments for loooooooooooooppppppppp"+arguments[jk]);
if(arguments[jk]!=null) continue;
if(parameters[jk].equals(long.class))
{
arguments[jk]=new Long(0);
continue;
}
if(parameters[jk].equals(int.class))
{
arguments[jk]=new Integer(0);
continue;
}
if(parameters[jk].equals(short.class))
{
arguments[jk]=new Short((short)0);
continue;
}
if(parameters[jk].equals(byte.class))
{
arguments[jk]=new Byte((byte)0);
continue;
}
if(parameters[jk].equals(double.class))
{
arguments[jk]=new Double(0.0);
continue;
}
if(parameters[jk].equals(float.class))
{
arguments[jk]=new Float(0.0f);
continue;
}
if(parameters[jk].equals(char.class))
{
arguments[jk]=new Character((char)0);
continue;
}
if(parameters[jk].equals(boolean.class))
{
arguments[jk]=new Boolean(false);
continue;
}
}
// done done
for(Object o:arguments)System.out.println("Arguments :"+o);
System.out.println("getArguments endsssssssssssssssssss");
return arguments;
}
private Object parseParamValues(String [] values,Class type)
{
try
{
if(values==null || values.length==0) return null;
if(type.isArray()==false)
{
String data=values[0];
return parseParamValue(data,type);
}
if(type.getComponentType().isArray()) return null;
if(type.equals(Object[].class))
{
Object ll[]=new Object[values.length];
int i=0;
for(String s:values)
{
ll[i]=s;
i++;
}
return ll;
}
if(type.equals(String[].class)) return values;
if(type.equals(Long[].class))
{
Long ll[]=new Long[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Long.parseLong(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Long lll[]=new Long[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(long[].class))
{
long ll[]=new long[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Long.parseLong(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
long lll[]=new long[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Integer[].class))
{
Integer ll[]=new Integer[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Integer.parseInt(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Integer lll[]=new Integer[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(int[].class))
{
int ll[]=new int[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Integer.parseInt(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
int lll[]=new int[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Short[].class))
{
Short ll[]=new Short[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Short.parseShort(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Short lll[]=new Short[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(short[].class))
{
short ll[]=new short[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Short.parseShort(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
short lll[]=new short[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Byte[].class))
{
Byte ll[]=new Byte[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Byte.parseByte(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Byte lll[]=new Byte[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(byte[].class))
{
byte ll[]=new byte[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Byte.parseByte(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
byte lll[]=new byte[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Double[].class))
{
Double ll[]=new Double[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Double.parseDouble(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Double lll[]=new Double[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(double[].class))
{
double ll[]=new double[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Double.parseDouble(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
double lll[]=new double[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Float[].class))
{
Float ll[]=new Float[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Float.parseFloat(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Float lll[]=new Float[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(float[].class))
{
float ll[]=new float[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Float.parseFloat(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
float lll[]=new float[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Boolean[].class))
{
Boolean ll[]=new Boolean[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Boolean.parseBoolean(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
Boolean lll[]=new Boolean[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(boolean[].class))
{
boolean ll[]=new boolean[values.length];
int correct=0;
int i=0;
for(String s:values)
{
try
{
ll[i]=Boolean.parseBoolean(s);
correct++;
i++;
}catch(NumberFormatException nfe){}
}
boolean lll[]=new boolean[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(Character[].class))
{
Character ll[]=new Character[values.length];
int correct=0;
int i=0;
for(String s:values)
{
if(s!=null && s.length()>0)
{
ll[i]=s.charAt(0);
correct++;
i++;
}
}
Character lll[]=new Character[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
if(type.equals(char[].class))
{
char ll[]=new char[values.length];
int correct=0;
int i=0;
for(String s:values)
{
if(s!=null && s.length()>0)
{
ll[i]=s.charAt(0);
correct++;
i++;
}
}
char lll[]=new char[correct];
for(i=0;i<correct;i++) lll[i]=ll[i];
return lll;
}
return null;
}catch(Exception e)
{
return null;
}
}
private Object parseParamValue(String data,Class type)
{
if(type.equals(Object.class)) return data;
if(type.equals(String.class)) return data;
if(type.equals(Long.class) || type.equals(long.class))
{
try
{
return Long.parseLong(data);
}catch(NumberFormatException nfe)
{
return new Long(0);
}
}
if(type.equals(Integer.class) || type.equals(int.class))
{
try
{
return Integer.parseInt(data);
}catch(NumberFormatException nfe)
{
return new Integer(0);
}
}
if(type.equals(Short.class) || type.equals(short.class))
{
try
{
return Short.parseShort(data);
}catch(NumberFormatException nfe)
{
return new Short((short)0);
}
}
if(type.equals(Byte.class) || type.equals(byte.class))
{
try
{
return Byte.parseByte(data);
}catch(NumberFormatException nfe)
{
return new Byte((byte)0);
}
}
if(type.equals(Double.class) || type.equals(double.class))
{
try
{
return Double.parseDouble(data);
}catch(NumberFormatException nfe)
{
return new Double(0.0);
}
}
if(type.equals(Float.class) || type.equals(float.class))
{
try
{
return Float.parseFloat(data);
}catch(NumberFormatException nfe)
{
return new Float(0.0f);
}
}
if(type.equals(Character.class) || type.equals(char.class)) 
{
try
{
if(data.length()>0) return data.charAt(0);
else return new Character((char)0);
}catch(NumberFormatException nfe)
{
return new Character((char)0);
}
}
if(type.equals(Boolean.class) || type.equals(boolean.class))
{
try
{
return Boolean.parseBoolean(data);
}catch(NumberFormatException nfe)
{
return new Boolean(false);
}
}
return null;
}
}