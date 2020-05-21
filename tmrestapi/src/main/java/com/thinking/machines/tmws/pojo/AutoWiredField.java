package com.thinking.machines.tmws.pojo;
import java.lang.reflect.*;
public class AutoWiredField implements java.io.Serializable
{
private Field field;
private String name;
public AutoWiredField()
{
this.field=null;
this.name="";
}
public void setField(Field field)
{
this.field=field;
}
public Field getField()
{
return this.field;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
}