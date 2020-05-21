package com.thinking.machines.tmws.pojo;
import java.lang.reflect.*;
import java.util.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmws.interfaces.*;
import com.thinking.machines.tmws.pools.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ServiceModule implements java.io.Serializable
{
private String path;
private boolean isPublic;
private Class serviceClass;
private Method applicationInjectorMethods[];
private Method sessionInjectorMethods[];
private Method requestInjectorMethods[];
private Method cookieManagerInjectorMethods[];
private Method contextDirectoryInjectorMethods[];
private Field applicationContainerFields[];
private Field sessionContainerFields[];
private Field requestContainerFields[];
private Field cookieManagerFields[];
private Field contextDirectoryFields[];
private boolean allowGet;
private boolean allowPost;
private boolean isSecured;
private Guard guard;
private Map<String,Service> services;
private List<AutoWiredField> autoWiredFields;
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void isPublic(boolean isPublic)
{
this.isPublic=isPublic;
}
public boolean isPublic()
{
return this.isPublic;
}
public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setApplicationInjectorMethods(Method applicationInjectorMethods[])
{
this.applicationInjectorMethods=applicationInjectorMethods;
}
public Method [] getApplicationInjectorMethods()
{
return this.applicationInjectorMethods;
}
public void setSessionInjectorMethods(Method sessionInjectorMethods[])
{
this.sessionInjectorMethods=sessionInjectorMethods;
}
public Method[] getSessionInjectorMethods()
{
return this.sessionInjectorMethods;
}
public void setRequestInjectorMethods(Method requestInjectorMethods[])
{
this.requestInjectorMethods=requestInjectorMethods;
}
public Method [] getRequestInjectorMethods()
{
return this.requestInjectorMethods;
}
public void setCookieManagerInjectorMethods(Method cookieManagerInjectorMethods[])
{
this.cookieManagerInjectorMethods=cookieManagerInjectorMethods;
}
public Method [] getCookieManagerInjectorMethods()
{
return this.cookieManagerInjectorMethods;
}
public void setContextDirectoryInjectorMethods(Method contextDirectoryInjectorMethods[])
{
this.contextDirectoryInjectorMethods=contextDirectoryInjectorMethods;
}
public Method [] getContextDirectoryInjectorMethods()
{
return this.contextDirectoryInjectorMethods;
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
public void setServices(Map<String,Service> services)
{
this.services=services;
}
public Map<String,Service> getServices()
{
return this.services;
}
public void setAutoWiredFields(List<AutoWiredField> autoWiredFields)
{
this.autoWiredFields=autoWiredFields;
}
public List<AutoWiredField> getAutoWiredFields()
{
return this.autoWiredFields;
}
public Service getService(String path)
{
String servicePath=path.replace(this.path,"");
return this.services.get(servicePath);
}
public void setApplicationContainerFields(Field applicationContainerFields[])
{
System.out.println("setApplicationContainerFields CHALIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
this.applicationContainerFields=applicationContainerFields;
}
public Field [] getApplicationContainerFields()
{
return this.applicationContainerFields;
}
public void setSessionContainerFields(Field sessionContainerFields[])
{
this.sessionContainerFields=sessionContainerFields;
}
public Field[] getSessionContainerFields()
{
return this.sessionContainerFields;
}
public void setRequestContainerFields(Field requestContainerFields[])
{
this.requestContainerFields=requestContainerFields;
}
public Field[] getRequestContainerFields()
{
return this.requestContainerFields;
}
public void setCookieManagerFields(Field cookieManagerFields[])
{
this.cookieManagerFields=cookieManagerFields;
}
public Field [] getCookieManagerFields()
{
return this.cookieManagerFields;
}
public void setContextDirectoryFields(Field contextDirectoryFields[])
{
this.contextDirectoryFields=contextDirectoryFields;
}
public Field [] getContextDirectoryFields()
{
return this.contextDirectoryFields;
}
public Object getServiceObject(ServletContext servletContext,HttpServletRequest request,Service service) throws Throwable
{
System.out.println("GET SERVICE OBJECT KI PEHLE LINE");
Model model=(Model)servletContext.getAttribute(Model.id);
ServiceController serviceObject;
serviceObject=ServiceObjectPool.get(this.serviceClass);

if(applicationInjectorMethods!=null)
{
System.out.println("GET SERVICE NOT NULL IF ME GYA");
for(Method m:applicationInjectorMethods) m.invoke(serviceObject,new ApplicationContainer(servletContext));
}
else if(applicationContainerFields!=null)  
{
for(Field f:applicationContainerFields) f.set(serviceObject,new ApplicationContainer(servletContext));
System.out.println("GET SERVICE OBJECT FIELD NOT NULL !!!!!!!!!!!!!!!!111");
}
if(sessionInjectorMethods!=null) for(Method m:sessionInjectorMethods) m.invoke(serviceObject,new SessionContainer(request.getSession()));
else if(sessionContainerFields!=null) for(Field f:sessionContainerFields) f.set(serviceObject,new SessionContainer(request.getSession()));
if(requestInjectorMethods!=null) for(Method m:requestInjectorMethods) m.invoke(serviceObject,new RequestContainer(request));
else if(requestContainerFields!=null) for(Field f:requestContainerFields) f.set(serviceObject,new RequestContainer(request));
if(cookieManagerInjectorMethods!=null) for(Method m:cookieManagerInjectorMethods) m.invoke(serviceObject,new CookieManager(request));
else if(cookieManagerFields!=null) for(Field f:cookieManagerFields) f.set(serviceObject,new CookieManager(request));
if(contextDirectoryInjectorMethods!=null) for(Method m:contextDirectoryInjectorMethods) m.invoke(serviceObject,model.getContextDirectory());
else if(contextDirectoryFields!=null) for(Field f:contextDirectoryFields) f.set(serviceObject,model.getContextDirectory());
for(AutoWiredField awf:autoWiredFields)
{
String name=awf.getName();
Field field=awf.getField();
System.out.println(name+"******************");
Object object=null;
if(name.length()>0)
{
// extract parameter with name
// ???
String paramValues[]=request.getParameterValues(name);
if(paramValues!=null) object=parseParamValues(paramValues,field.getType());
if(object==null) object=request.getAttribute(name);
if(object==null) object=request.getSession().getAttribute(name);
if(object==null) object=servletContext.getAttribute(name);
}
else
{
System.out.println("elseeeeeeeeeeeeee me gyaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
if(field.getType().equals(Object[].class) && request.getParameterMap().size()>0)
{
System.out.println("Object[].class me gya&&&&&&&&&&&&&&&&&&&&&&&& ");
int totalStringsInQS=0;
Iterator<String []> iter=request.getParameterMap().values().iterator();
while(iter.hasNext()) totalStringsInQS+=iter.next().length;
Object arr[]=new Object[totalStringsInQS];
iter=request.getParameterMap().values().iterator();
int k=0;
while(iter.hasNext())
{
String j[]=iter.next();
for(String m:j)
{
arr[k]=m;
k++;
}
}
object=arr;
}
if(object==null && field.getType().equals(Object.class) && request.getParameterMap().size()>0)
{
System.out.println("Object.class me gya&&&&&&&&&&&&&&&&&&&&&&&& ");
Map<String,String []> mp=request.getParameterMap();
object=mp.values().iterator().next()[0];
}
if(field.getType().equals(String[].class) && request.getParameterMap().size()>0)
{
System.out.println("String[].class me gya&&&&&&&&&&&&&&&&&&&&&&&& ");
int totalStringsInQS=0;
Iterator<String []> iter=request.getParameterMap().values().iterator();
while(iter.hasNext()) totalStringsInQS+=iter.next().length;
String arr[]=new String[totalStringsInQS];
iter=request.getParameterMap().values().iterator();
int k=0;
while(iter.hasNext())
{
String j[]=iter.next();
for(String m:j)
{
arr[k]=m;
k++;
}
}
object=arr;
}
if(object==null && field.getType().equals(String.class) && request.getParameterMap().size()>0)
{
System.out.println("String.class me gya&&&&&&&&&&&&&&&&&&&&&&&& ");
Map<String,String []> mp=request.getParameterMap();
object=mp.values().iterator().next()[0];
System.out.println(object+"*********************************");
}
if(object==null)
{
Enumeration en=request.getAttributeNames();
while(en.hasMoreElements())
{
Object value=request.getAttribute((String)en.nextElement());
if(field.getType().isInstance(value))
{
object=value;
break;
}
}
}
if(object==null)
{
HttpSession session=request.getSession();
Enumeration en=session.getAttributeNames();
while(en.hasMoreElements())
{
Object value=session.getAttribute((String)en.nextElement());
if(field.getType().isInstance(value))
{
object=value;
break;
}
}
}
if(object==null)
{
Enumeration en=servletContext.getAttributeNames();
while(en.hasMoreElements())
{
Object value=servletContext.getAttribute((String)en.nextElement());
if(field.getType().isInstance(value))
{
object=value;
break;
}
}
}
}
if(object!=null) field.set(serviceObject,object);
} // for loop on AutoWiredField ends
// done done
return serviceObject;
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