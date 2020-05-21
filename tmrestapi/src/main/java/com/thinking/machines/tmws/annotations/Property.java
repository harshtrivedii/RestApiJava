package com.thinking.machines.tmws.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Property 
{
public String value(); 
}