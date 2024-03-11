package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
import java.util.*;

public class Service
{
private Class serviceClass;
private String path;
private String forwardTo;
private Method service;
private boolean isGetAllowed;
private boolean isPostAllowed;
private boolean runOnStartup;
private int priority;
private boolean injectApplicationDirectory;
private boolean injectApplicationScope;
private boolean injectSessionScope;
private boolean injectRequestScope;
private List<AutoWiredWrapper> autoWired;
private List<RequestedParameter> requestedParameterList;

// Sir say "It will be more in future so mentally prepared"
// constructor starts
public Service()
{
this.serviceClass=null;
this.path="";
this.forwardTo=null;
this.service=null;
this.isGetAllowed=false;
this.isPostAllowed=false;
this.runOnStartup=false;
this.priority=-1;
this.injectApplicationDirectory=false;
this.injectApplicationScope=false;
this.injectSessionScope=false;
this.injectRequestScope=false;
this.autoWired=null;
this.requestedParameterList=null;
}
public Service(Class serviceClass,String path,String forwardTo,Method service,boolean isGetAllowed,boolean isPostAllowed,boolean runOnStart,int priority,boolean injectApplicationDirectory,boolean injectApplicationScope,boolean injectSessionScope,boolean injectRequestScope,List<AutoWiredWrapper> autoWired,List<RequestedParameter> requestedParameterList)
{
this.serviceClass=serviceClass;
this.path=path;
this.forwardTo=forwardTo;
this.service=service;
this.isGetAllowed=isGetAllowed;
this.isPostAllowed=isPostAllowed;
this.runOnStartup=runOnStartup;
this.priority=priority;
this.injectApplicationDirectory=injectApplicationDirectory;
this.injectApplicationScope=injectApplicationScope;
this.injectSessionScope=injectSessionScope;
this.injectRequestScope=injectRequestScope;
this.autoWired=autoWired;
this.requestedParameterList=requestedParameterList;
}
// constructor ends
// setter starts
public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public void setPath(String path)
{
this.path=path;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public void setService(Method method)
{
this.service=service;
}
public void setIsGetAllowed(boolean isGetAllowed)
{
this.isGetAllowed=isGetAllowed;
}
public void setIsPostAllowed(boolean isPostAllowed)
{
this.isPostAllowed=isPostAllowed;
}
public void setRunOnStartup(boolean runOnStartup)
{
this.runOnStartup=runOnStartup;
}
public void setPriority(int priority)
{
this.priority=priority;
}
public void setInjectApplicationDirectory(boolean injectApplicationDirectory)
{
this.injectApplicationDirectory=injectApplicationDirectory;
}
public void setInjectApplicationScope(boolean injectApplicationScope)
{
this.injectApplicationScope=injectApplicationScope;
}
public void setInjectSessionScope(boolean injectSessionScope)
{
this.injectSessionScope=injectSessionScope;
}
public void setInjectRequestScope(boolean injectRequestScope)
{
this.injectRequestScope=injectRequestScope;
}
public void setAutoWired(List<AutoWiredWrapper> autoWired)
{
this.autoWired=autoWired;
}
public void setRequestedParameterList(List<RequestedParameter> requestedParameterList)
{
this.requestedParameterList=requestedParameterList;
}
// setter ends
// getter starts
public Class getServiceClass()
{
return this.serviceClass;
}
public String getPath()
{
return this.path;
}
public String getForwardTo()
{
return this.forwardTo;
}
public Method getService()
{
return this.service;
}
public boolean getIsGetAllowed()
{
return this.isGetAllowed;
}
public boolean getIsPostAllowed()
{
return this.isPostAllowed;
}
public boolean getRunOnStartup()
{
return this.runOnStartup;
}
public int getPriority()
{
return this.priority;
}
public boolean getInjectApplicationDirectory()
{
return this.injectApplicationDirectory;
}
public boolean getInjectApplicationScope()
{
return this.injectApplicationScope;
}
public boolean getInjectSessionScope()
{
return this.injectSessionScope;
}
public boolean getInjectRequestScope()
{
return this.injectRequestScope;
}
public List<AutoWiredWrapper> getAutoWired()
{
return this.autoWired;
}
public List<RequestedParameter> getRequestedParameterList()
{
return this.requestedParameterList;
}
// getter ends
}
