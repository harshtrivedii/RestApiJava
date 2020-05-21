package com.thinking.machines.tmws.pojo;
import java.io.*;
public class ContextDirectory
{
private File file;
public ContextDirectory(File file)
{
this.file=file;
}
public static ContextDirectory getContextDirectory(File folderPath)
{
return new ContextDirectory(folderPath);
}
}