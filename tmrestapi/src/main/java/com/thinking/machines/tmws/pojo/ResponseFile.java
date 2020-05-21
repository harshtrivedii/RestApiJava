package com.thinking.machines.tmws.pojo;
import java.io.*;
public class ResponseFile implements java.io.Serializable
{
private File file;
private String mimeType;
private String name;
private boolean shouldSave;
ResponseFile() 
{
this.file=null;
this.mimeType=null;
this.name=null;
this.shouldSave=false; 
}
public void setFile(File file) 
{
this.file=file; 
}
public File getFile() 
{
return this.file; 
}
public void setMimeType(String mimeType) 
{
this.mimeType=mimeType; 
}
public String getMimeType() 
{
return this.mimeType; 
}
public void setName(String name) 
{
this.name=name; 
}
public String getName() 
{
return this.name; 
}
public void setShouldSave(boolean shouldSave) 
{
this.shouldSave=shouldSave; 
}
public boolean getShouldSave() 
{
return this.shouldSave; 
}
}
