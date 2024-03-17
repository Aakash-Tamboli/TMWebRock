package com.thinking.machines.webrock.annotations;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoWired
{
String name() default "";
}
