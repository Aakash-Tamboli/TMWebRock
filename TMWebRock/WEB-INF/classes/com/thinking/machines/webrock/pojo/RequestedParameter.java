package com.thinking.machines.webrock.pojo;
import java.lang.*;

public class RequestedParameter
{
private Class type;
private String key;

public RequestedParameter()
{
this.type=null;
this.key=null;
}

public RequestedParameter(Class type,String key)
{
this.type=type;
this.key=key;
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
// getter ends
}
