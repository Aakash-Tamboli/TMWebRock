package com.thinking.machines.webrock.util;

public class StringUtility
{
public static String capitalize(String str)
{
return str.substring(0,1).toUpperCase()+str.substring(1);
}
public static String uncapitalize(String str)
{
// this method ensure that given string begining character should be lower case
return str.substring(0,1).toLowerCase()+str.substring(1);
}
}
