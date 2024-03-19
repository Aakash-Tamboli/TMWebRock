package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;

public class Guard
{
private Class guardClass;
private Method guardService;

public Guard()
{
this.guardClass=null;
this.guardService=null;
}

public Guard(Class guardClass,Method guardService)
{
this.guardClass=guardClass;
this.guardService=guardService;
}

// setter starts
public void setGuardClass(Class guardClass)
{
this.guardClass=guardClass;
}
public void setGuardService(Method guardService)
{
this.guardService=guardService;
}
// setter ends
// getter starts
public Class getGuardClass()
{
return this.guardClass;
}
public Method getGuardService()
{
return this.guardService;
}
// getter ends
}
