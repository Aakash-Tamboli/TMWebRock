package com.thinking.machines.webrock.pojo;
import java.lang.*;

public class RequestedParameterProperty
{
private Class type;
private String key;
private String nameOfProperty;

public RequestedParameterProperty()
{
this.type=null;
this.key=null;
this.nameOfProperty=nameOfProperty;
}

public RequestedParameterProperty(Class type,String key,String nameOfProperty)
{
this.type=type;
this.key=key;
this.nameOfProperty=nameOfProperty;
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

public void setNameOfProperty(String nameOfProperty)
{
this.nameOfProperty=nameOfProperty;
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

public String getNameOfProperty()
{
return this.nameOfProperty;
}

// getter ends
}
