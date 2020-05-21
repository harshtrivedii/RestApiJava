package com.thinking.machines.tmws.pojo;
import java.util.*;
public class ErrorMap implements java.io.Serializable
{
private List<Pair<String,List<String>>> errors;
public ErrorMap()
{
this.errors=new LinkedList<>();
}
public void setError(String className,String message)
{
List<String> list=new LinkedList<>();
list.add(message);
errors.add(new Pair(className,list));
}
public void addError(String className,String message)
{
for(Pair p:errors)
{
if(p.getFirst().equals(className))
{
((LinkedList)p.getSecond()).add(message);
}
}
}
}