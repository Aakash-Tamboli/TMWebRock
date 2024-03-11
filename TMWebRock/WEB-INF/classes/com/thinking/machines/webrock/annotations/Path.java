package com.thinking.machines.webrock.annotations;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Path
{
String value() default "";
}

/*
Resources I use to understand annotation
https://www.geeksforgeeks.org/annotations-in-java/
https://youtu.be/DkZr7_c9ry8
important methods for this projects

isAnnotationPresent(-/-) used in Class and Method type object both
	isAnnoationPresent(Annotation.class) it return true or false respectivly
getAnnotation(-/-) used for retrive a value pass by user in anotation field
	getAnnotation(Annotation.class) it return annotation type thing

*/
