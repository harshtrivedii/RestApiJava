package com.thinking.machines.tmws.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FileConfiguration 
{
public String maximumFileSize() default "2MB";
public String [] extensions() default {"*"};
public String inputName() default "*"; 
}