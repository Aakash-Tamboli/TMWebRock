package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;

public class AutoWiredWrapper
{
private Field property;
private String name;
private Class type;

public AutoWiredWrapper()
{
this.property=null;
this.name=null;
this.type=null;
}
public AutoWiredWrapper(Field property,String name,Class type)
{
this.property=property;
this.name=name;
this.type=type;
}

// setter starts
public void setProperty(Field property)
{
this.property=property;
}
public void setName(String name)
{
this.name=name;
}
public void setType(Class type)
{
this.type=type;
}
// setter ends
// getter start
public Field getProperty()
{
return this.property;
}
public String getName()
{
return this.name;
}
public Class getType()
{
return this.type;
}
// getter ends
}
