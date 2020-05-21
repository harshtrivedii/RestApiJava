package com.thinking.machines.tmws.pojo;
import java.util.*;
import java.io.*;
public class Model
{
public final static String id;
static
{
id=java.util.UUID.randomUUID().toString();
}
private ContextDirectory contextDirectory;
private Map<String,ServiceModule> serviceModules;
private Map<String,String> serviceModuleKeys;
public Model()
{
this.contextDirectory=null;
this.serviceModules=new HashMap<>();
this.serviceModuleKeys=new HashMap<>();
}
public void addServiceModule(ServiceModule serviceModule)
{
Map<String,Service> services=serviceModule.getServices();
String serviceModulePath=serviceModule.getPath();
String servicePath;
String serviceModuleKey=UUID.randomUUID().toString();
serviceModules.put(serviceModuleKey,serviceModule);
System.out.println("UUID :"+serviceModuleKey+"Path :" +serviceModule.getPath());
Set<String> serviceKeys=services.keySet();
for(String key:serviceKeys)
{
System.out.println("For loop me key :"+key);
servicePath=serviceModulePath+key;
serviceModuleKeys.put(servicePath,serviceModuleKey);
}
System.out.println("Add Service Module chali !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
}
public ServiceModule getServiceModule(String requestPath)
{
System.out.println("get Service Module starts........................");
Iterator hmIterator = serviceModuleKeys.entrySet().iterator(); 
while (hmIterator.hasNext()) 
{ 
Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
String path = (String)mapElement.getValue(); 
System.out.println(mapElement.getKey() + " : " + path); 
} 
String serviceModuleKey=serviceModuleKeys.get(requestPath);
if(serviceModuleKey==null) return null;
hmIterator = serviceModules.entrySet().iterator(); 
while (hmIterator.hasNext()) 
{ 
Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
ServiceModule sm= (ServiceModule)mapElement.getValue(); 
System.out.println(mapElement.getKey() + " : " + sm.getPath()); 
}


System.out.println("get Service Module ends ........................");
return serviceModules.get(serviceModuleKey);
}
public void setContextDirectory(ContextDirectory contextDirectory)
{
this.contextDirectory=contextDirectory;
}
public ContextDirectory getContextDirectory()
{
return this.contextDirectory;
}
}