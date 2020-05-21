package com.thinking.machines.tmws;
import java.util.*;
import java.lang.reflect.*;
import java.util.stream.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmws.pojo.*;
import com.thinking.machines.tmws.interfaces.*;
public class TMWSInitializer extends HttpServlet
{
public void init()
{
System.out.println("Init of TMWSInitializer........................");
String packageNamePrefix=getServletConfig().getInitParameter("package-name-prefix");
if(packageNamePrefix!=null && packageNamePrefix.length()>0)
{
if(packageNamePrefix.endsWith(".")==false) packageNamePrefix+=".";
}
else packageNamePrefix=null;
ServletContext servletContext=getServletContext();
String contextFolderPath=servletContext.getRealPath(".");
String classesFolderPath=contextFolderPath+File.separator+"WEB-INF"+File.separator+"classes";
List<String> classesList=null;
try
{
Stream<java.nio.file.Path> walk=java.nio.file.Files.walk(java.nio.file.Paths.get(classesFolderPath));
classesList=walk.map((x)->{return x.toString();}).filter((f)->{return f.endsWith(".class");}).collect(Collectors.toList());
}catch(Throwable t)
{
// report error
t.printStackTrace(); // remove after testing
}
//contextDirectory wala kaam
String libFolderPath=contextFolderPath+File.separator+"WEB-INF"+File.separator+"lib";
Model model=new Model();
ContextDirectory contextDirectory=ContextDirectory.getContextDirectory(new File(contextFolderPath));
model.setContextDirectory(contextDirectory);
servletContext.setAttribute(Model.id,model);
Class sc=ServiceController.class;
Path pathAnnotation;
Class pathAnnotationClass=Path.class;
AutoWired autoWiredAnnotation=null;
Class autoWiredAnnotationClass=AutoWired.class;
InjectApplicationContainer injectApplicationContainerAnnotation;
Class injectApplicationContainerAnnotationClass=InjectApplicationContainer.class;
InjectSessionContainer injectSessionContainerAnnotation;
Class injectSessionContainerAnnotationClass=InjectSessionContainer.class;
InjectRequestContainer injectRequestContainerAnnotation;
Class injectRequestContainerAnnotationClass=InjectRequestContainer.class;
InjectCookieManager injectCookieManagerAnnotation;
Class injectCookieManagerAnnotationClass=InjectCookieManager.class;
InjectContextDirectory injectContextDirectoryAnnotation;
Class injectContextDirectoryAnnotationClass=InjectContextDirectory.class;
Secured securedAnnotation;
Class securedAnnotationClass=Secured.class;
Template templateAnnotation;
Class templateAnnotationClass=Template.class;
Property propertyAnnotation=null;
Class propertyAnnotationClass=Property.class;
Get getAnnotation;
Class getAnnotationClass=Get.class;
Post postAnnotation;
Class postAnnotationClass=Post.class;
Forward forwardAnnotation;
Class forwardAnnotationClass=Forward.class;
ExceptionHandler exceptionHandlerAnnotation;
Class exceptionHandlerAnnotationClass=ExceptionHandler.class;
FileConfiguration fileConfigurationAnnotation;
Class fileConfigurationAnnotationClass=FileConfiguration.class;
Produces producesAnnotation;
Class producesAnnotationClass=Produces.class;
OnStartup onStartupAnnotation;
Class onStartupAnnotationClass=OnStartup.class;
OnException onExceptionAnnotation;
Class onExceptionAnnotationClass=OnException.class;
ServiceModule serviceModule=null;
Map<String,Service> services=null;
ErrorMap errorMap=new ErrorMap();
if(classesList!=null)
{
String className;
Method methods[];
Method method;
List<Method> applicationContainerMethods=new LinkedList<>();
List<Method> sessionContainerMethods=new LinkedList<>();
List<Method> requestContainerMethods=new LinkedList<>();
List<Method> cookieManagerMethods=new LinkedList<>();
List<Method> contextDirectoryMethods=new LinkedList<>();
List<Field> applicationContainerFields=new LinkedList<>();
List<Field> sessionContainerFields=new LinkedList<>();
List<Field> requestContainerFields=new LinkedList<>();
List<Field> cookieManagerFields=new LinkedList<>();
List<Field> contextDirectoryFields=new LinkedList<>();
Parameter [] parameters;
Field field;
Field fields[];
for(String classFileName:classesList)
{
classFileName=classFileName.substring(classesFolderPath.length()+1,classFileName.length()-6).replace(File.separator,".");
if(packageNamePrefix!=null)
{
if(classFileName.startsWith(packageNamePrefix)==false) continue;
}
// analysis of class starts here
Class c=null;
try
{
c=Class.forName(classFileName);
System.out.println("Class name :"+c.getName());
}catch(Throwable t)
{
// may add something for creating some kind of error report
continue;
}
if(sc.isAssignableFrom(c)==false)
{
// scan the class for TMWS specific annotations and put the details in error report
continue;
}
className=c.getName();
pathAnnotation=(Path)c.getAnnotation(pathAnnotationClass);
getAnnotation=(Get)c.getAnnotation(getAnnotationClass);
postAnnotation=(Post)c.getAnnotation(postAnnotationClass);
securedAnnotation=(Secured)c.getAnnotation(securedAnnotationClass);
injectApplicationContainerAnnotation=(InjectApplicationContainer)c.getAnnotation(injectApplicationContainerAnnotationClass);
injectSessionContainerAnnotation=(InjectSessionContainer)c.getAnnotation(injectSessionContainerAnnotationClass);
injectRequestContainerAnnotation=(InjectRequestContainer)c.getAnnotation(injectRequestContainerAnnotationClass);
injectCookieManagerAnnotation=(InjectCookieManager)c.getAnnotation(injectCookieManagerAnnotationClass);
injectContextDirectoryAnnotation=(InjectContextDirectory)c.getAnnotation(injectContextDirectoryAnnotationClass);
if(pathAnnotation==null)
{
errorMap.setError(className,"Path annotation missing");
}
serviceModule=new ServiceModule();
serviceModule.setPath(pathAnnotation.value());
serviceModule.isPublic(pathAnnotation.isPublic());
serviceModule.setServiceClass(c);
if(getAnnotation==null && postAnnotation==null)
{
serviceModule.allowGet(true);
serviceModule.allowPost(true);
}
else
{
serviceModule.allowGet(getAnnotation!=null);
serviceModule.allowPost(postAnnotation!=null);
}
if(securedAnnotation!=null)
{
serviceModule.isSecured(true);
try
{
serviceModule.setGuard((Guard)securedAnnotation.guard().newInstance());
}catch(Throwable t)
{
errorMap.addError(className,"Unable to create an instance of guard : "+t.getMessage());
t.printStackTrace(); // remove after testing
}
}
else
{
serviceModule.isSecured(false);
}
methods=c.getDeclaredMethods();
for(Method m:methods)
{
parameters=m.getParameters();
if(parameters.length!=1) continue;
if(parameters[0].getType().equals(ApplicationContainer.class))
{
applicationContainerMethods.add(m);
continue;
}
if(parameters[0].getType().equals(SessionContainer.class))
{
sessionContainerMethods.add(m);
continue;
}
if(parameters[0].getType().equals(RequestContainer.class))
{
requestContainerMethods.add(m);
continue;
}
if(parameters[0].getType().equals(CookieManager.class))
{
cookieManagerMethods.add(m);
continue;
}
if(parameters[0].getType().equals(ContextDirectory.class))
{
contextDirectoryMethods.add(m);
continue;
}
} // for loop ends
fields=c.getDeclaredFields();
for(Field f:fields)
{
if(applicationContainerMethods.size()==0 && f.getType().equals(ApplicationContainer.class))
{
System.out.println("SIZE==0  FIELD WALA FOR ");
applicationContainerFields.add(f);
continue;
}
if(sessionContainerMethods.size()==0 && f.getType().equals(SessionContainer.class))
{
sessionContainerFields.add(f);
continue;
}
if(requestContainerMethods.size()==0 && f.getType().equals(RequestContainer.class))
{
requestContainerFields.add(f);
continue;
}
if(cookieManagerMethods.size()==0 && f.getType().equals(CookieManager.class))
{
cookieManagerFields.add(f);
continue;
}
if(contextDirectoryMethods.size()==0 && f.getType().equals(ContextDirectory.class))
{
contextDirectoryFields.add(f);
continue;
}
} // for loop ends
if(applicationContainerMethods.size()>0)
{
serviceModule.setApplicationInjectorMethods(applicationContainerMethods.toArray(new Method[0]));
}
if(sessionContainerMethods.size()>0)
{
serviceModule.setSessionInjectorMethods(sessionContainerMethods.toArray(new Method[0]));
}
if(requestContainerMethods.size()>0)
{
serviceModule.setRequestInjectorMethods(requestContainerMethods.toArray(new Method[0]));
}
if(cookieManagerMethods.size()>0)
{
serviceModule.setCookieManagerInjectorMethods(cookieManagerMethods.toArray(new Method[0]));
}
if(contextDirectoryMethods.size()>0)
{
serviceModule.setContextDirectoryInjectorMethods(contextDirectoryMethods.toArray(new Method[0]));
}
if(applicationContainerFields.size()>0)
{
Field[] pp=applicationContainerFields.toArray(new Field[0]);
for(Field j:pp) System.out.println(j.getName());
serviceModule.setApplicationContainerFields(applicationContainerFields.toArray(new Field[0]));
System.out.println("SERVICE  MODULE ME FIELD SET HOGYI !!!!!!!!!!!!!!!!111");
}
if(sessionContainerFields.size()>0)
{
serviceModule.setSessionContainerFields(sessionContainerFields.toArray(new Field[0]));
}
if(requestContainerFields.size()>0)
{
serviceModule.setRequestContainerFields(requestContainerFields.toArray(new Field[0]));
}
if(cookieManagerFields.size()>0)
{
serviceModule.setCookieManagerFields(cookieManagerFields.toArray(new Field[0]));
}
if(contextDirectoryFields.size()>0)
{
serviceModule.setContextDirectoryFields(contextDirectoryFields.toArray(new Field[0]));
}
if(injectApplicationContainerAnnotation!=null && applicationContainerMethods.size()==0 && applicationContainerFields.size()==0)
{
errorMap.addError(className,"No suitable method/field to inject application");
}
if(injectSessionContainerAnnotation!=null && sessionContainerMethods.size()==0 && sessionContainerFields.size()==0)
{
errorMap.addError(className,"No suitable method/field to inject session");
}
if(injectRequestContainerAnnotation!=null && requestContainerMethods.size()==0 && requestContainerFields.size()==0)
{
errorMap.addError(className,"No suitable method/field to inject request");
}
if(injectCookieManagerAnnotation!=null && cookieManagerMethods.size()==0 && cookieManagerFields.size()==0)
{
errorMap.addError(className,"No suitable method/field to inject cookie manager");
}
if(injectContextDirectoryAnnotation!=null && contextDirectoryMethods.size()==0 && contextDirectoryFields.size()==0)
{
errorMap.addError(className,"No suitable method/field to inject context directory");
}
List<AutoWiredField> autoWiredFields=new LinkedList<>();
AutoWiredField autoWiredField;
for(Field f:fields)
{
autoWiredAnnotation=(AutoWired)f.getDeclaredAnnotation(autoWiredAnnotationClass);
if(autoWiredAnnotation==null) continue;
System.out.println("field wale autowired me fasaaaaaaaaa");
autoWiredField=new AutoWiredField();
autoWiredField.setField(f);
autoWiredField.setName(autoWiredAnnotation.name());
autoWiredFields.add(autoWiredField);
}
for(AutoWiredField g:autoWiredFields) System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$"+g.getName()+g.getField().getType());
serviceModule.setAutoWiredFields(autoWiredFields);
Service service;
services=new HashMap< >();
for(Method m:methods)
{
String methodName=m.getName();
pathAnnotation=(Path)m.getDeclaredAnnotation(pathAnnotationClass);
if(pathAnnotation==null) continue;
getAnnotation=(Get)m.getDeclaredAnnotation(getAnnotationClass);
postAnnotation=(Post)m.getDeclaredAnnotation(postAnnotationClass);
securedAnnotation=(Secured)m.getAnnotation(securedAnnotationClass);
injectApplicationContainerAnnotation=(InjectApplicationContainer)m.getAnnotation(injectApplicationContainerAnnotationClass);
injectSessionContainerAnnotation=(InjectSessionContainer)m.getAnnotation(injectSessionContainerAnnotationClass);
injectRequestContainerAnnotation=(InjectRequestContainer)m.getAnnotation(injectRequestContainerAnnotationClass);
injectCookieManagerAnnotation=(InjectCookieManager)m.getAnnotation(injectCookieManagerAnnotationClass);
injectContextDirectoryAnnotation=(InjectContextDirectory)m.getAnnotation(injectContextDirectoryAnnotationClass);
producesAnnotation=(Produces)m.getAnnotation(producesAnnotationClass);
forwardAnnotation=(Forward)m.getAnnotation(forwardAnnotationClass);
service=new Service();
if(pathAnnotation.value()==null) continue;
service.setPath(pathAnnotation.value());
service.setMethod(m);
service.setReturnType(m.getReturnType());
service.isPublic(pathAnnotation.isPublic());
if(getAnnotation!=null) service.allowGet(true);
if(postAnnotation!=null) service.allowPost(true);
if(securedAnnotation!=null)
{
service.isSecured(true);
try
{
service.setGuard((Guard)securedAnnotation.guard().newInstance());
}catch(Throwable t)
{
errorMap.addError(className+" : "+methodName,"Unable to create an instance of guard : "+t.getMessage());
t.printStackTrace();// remove after testing
}
}//securedAnnotation
if(producesAnnotation==null) service.setResponseType(ResponseType.NONE);
else service.setResponseType(producesAnnotation.value());
if(forwardAnnotation!=null) 
{
service.isForwarding(true);
service.setForwardTo(forwardAnnotation.resource());
}
System.out.println("*3****************************************");
parameters=m.getParameters();
int i1=0;
int i2=0;
int i3=0;
int i4=0;
int i5=0;
int j=0;
int i6=0;
int i7=0;
int l=parameters.length;
int applicationParameterIndexes[]=null;
int sessionParameterIndexes[]=null;
int requestParameterIndexes[]=null;
int cookieManagerParameterIndexes[]=null;
int contextDirectoryParameterIndexes[]=null;
int propertyParameterIndexes[]=null;
Property propertyParameters[]=null;
int autoWiredParameterIndexes[]=null;
AutoWired autoWiredParameters[]=null;
Class serviceParameters[]=new Class[l];
if(injectApplicationContainerAnnotation!=null) applicationParameterIndexes=new int[l];
if(injectSessionContainerAnnotation!=null) sessionParameterIndexes=new int[l];
if(injectRequestContainerAnnotation!=null) requestParameterIndexes=new int[l];
if(injectCookieManagerAnnotation!=null) cookieManagerParameterIndexes=new int[l];
if(injectContextDirectoryAnnotation!=null) contextDirectoryParameterIndexes=new int[l];
for(Parameter p:parameters)
{
propertyAnnotation=(Property)p.getDeclaredAnnotation(propertyAnnotationClass);
autoWiredAnnotation=(AutoWired)p.getDeclaredAnnotation(autoWiredAnnotationClass);
if(propertyAnnotation!=null && autoWiredAnnotation!=null) break;
}
if(propertyAnnotation!=null)
{
System.out.println("Property annotation !=null me gyaaaaaaaaaaaa");
propertyParameterIndexes=new int[l];
propertyParameters=new Property[l];
}
if(autoWiredAnnotation!=null)
{
System.out.println("Auto wired annotation !=null me gyaaaaaaaaaaaa");
autoWiredParameterIndexes=new int[l];
autoWiredParameters=new AutoWired[l];
}
for(Parameter p:parameters)
{
System.out.println("##################################");
serviceParameters[j]=p.getType();
System.out.println("##1################################");
propertyAnnotation=(Property)p.getDeclaredAnnotation(propertyAnnotationClass);
System.out.println(propertyAnnotation.value());
autoWiredAnnotation=(AutoWired)p.getDeclaredAnnotation(autoWiredAnnotationClass);
System.out.println("2#################################");
if(p.getDeclaredAnnotation(injectApplicationContainerAnnotationClass)!=null)
{
service.setInjectApplicationContainer(true);
applicationParameterIndexes[i1++]=j++;
}
if(p.getDeclaredAnnotation(injectSessionContainerAnnotationClass)!=null)
{
service.setInjectSessionContainer(true);
sessionParameterIndexes[i2++]=j++;
}
if(p.getDeclaredAnnotation(injectRequestContainerAnnotationClass)!=null)
{
service.setInjectRequestContainer(true);
requestParameterIndexes[i3++]=j++;
}
if(p.getDeclaredAnnotation(injectCookieManagerAnnotationClass)!=null)
{
service.setInjectCookieManager(true);
cookieManagerParameterIndexes[i4++]=j++;
}
if(p.getDeclaredAnnotation(injectContextDirectoryAnnotationClass)!=null)
{
service.setInjectContextDirectory(true);
contextDirectoryParameterIndexes[i5++]=j++;
}
if(p.getDeclaredAnnotation(propertyAnnotationClass)!=null)
{
propertyParameterIndexes[i6]=j++;
propertyParameters[i6++]=propertyAnnotation;
System.out.println("!!!!!!!!!!!! "+propertyParameters[i6-1].value()+"Indexxxxxxxxxxxxxx : "+i6);
}
if(p.getDeclaredAnnotation(autoWiredAnnotationClass)!=null) 
{
autoWiredParameterIndexes[i7]=j++;
autoWiredParameters[i7++]=autoWiredAnnotation;
}
}
service.setApplicationParameterIndexes(applicationParameterIndexes);
service.setSessionParameterIndexes(sessionParameterIndexes);
service.setRequestParameterIndexes(requestParameterIndexes);
service.setCookieManagerParameterIndexes(cookieManagerParameterIndexes);
service.setContextDirectoryParameterIndexes(contextDirectoryParameterIndexes);
service.setPropertyParameterIndexes(propertyParameterIndexes);
service.setPropertyParameters(propertyParameters);
service.setAutoWiredParameterIndexes(autoWiredParameterIndexes);
service.setAutoWiredParameters(autoWiredParameters);
service.setParameters(serviceParameters);
services.put(service.getPath(),service);
for(Property k:propertyParameters) System.out.println("Property Parameter  :::::::::::::"+k);
/*
Template
ExceptionHandler
OnException
*/
serviceModule.setServices(services);
}//methods loop ends here
System.out.println("Add Service Module ........................"+(serviceModule!=null));
model.addServiceModule(serviceModule);
} // for loop for classesList ends here
} // if ends
servletContext.setAttribute(Model.id,model);
System.out.println("Init of TMWSInitializer ends .......................");
} // init ends
}
