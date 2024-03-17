package com.thinking.machines.webrock.annotations;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnStartup
{
int Priority() default -1;
}

// Remember Sir said "lower the value higher the priority"
