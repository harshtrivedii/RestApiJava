package com.thinking.machines.tmws.pojo;
import java.io.*;
public class UploadedFile implements java.io.Serializable 
{
private File file;
private boolean removeLater;
public UploadedFile()
{
this.file=null;
this.removeLater=false;
}
public UploadedFile(File file,boolean removeLater)
{
this.file=file;
this.removeLater=removeLater;
}
public void setFile(File file)
{
this.file=file;
}
public File getFile()
{
return this.file;
}
public void setRemoveLater(boolean removeLater)
{
this.removeLater=removeLater;
}
public boolean getRemoveLater()
{
return this.removeLater;
}
}