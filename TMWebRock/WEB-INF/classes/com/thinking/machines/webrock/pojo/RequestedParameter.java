package com.thinking.machines.webrock.pojo;
import java.lang.*;

public class RequestedParameter
{
private Class type;
private String key;
private boolean isAnnotationApplied;

public RequestedParameter()
{
this.type=null;
this.key=null;
this.isAnnotationApplied=false;
}

public RequestedParameter(Class type,String key,boolean isAnnotationApplied)
{
this.type=type;
this.key=key;
this.isAnnotationApplied=isAnnotationApplied;
}
// setter starts
public void setType(Class type)
{
this.type=type;
}

public void setKey(String key)
{
this.key=key;
}

public void setIsAnnotationApplied(boolean isAnnotationApplied)
{
this.isAnnotationApplied=isAnnotationApplied;
}

// setter ends
// getter starts
public Class getType()
{
return this.type;
}
public String getKey()
{
return this.key;
}

public boolean getIsAnnotationApplied()
{
return this.isAnnotationApplied;
}

// getter ends
}
