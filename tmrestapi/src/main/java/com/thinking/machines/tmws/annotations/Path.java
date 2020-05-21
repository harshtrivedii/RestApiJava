package com.thinking.machines.tmws.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Path 
{
public String value(); 
public boolean isPublic() default true;
}