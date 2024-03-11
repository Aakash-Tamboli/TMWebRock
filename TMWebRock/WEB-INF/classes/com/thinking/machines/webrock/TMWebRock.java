package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.model.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.scope.*;



public class TMWebRock extends HttpServlet
{
private ServletContext servletContext;
private WebRockModel model;

public void init(ServletConfig servletConfig) throws ServletException
{
this.servletContext=servletConfig.getServletContext();
this.model=(WebRockModel)this.servletContext.getAttribute("dataStructure");
}

public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
// Container fetching starts
ServletContext servletContext=request.getServletContext();
HttpSession httpSession=request.getSession();
// Container fetching ends

// variable Declaration starts
String key=null;
String serviceURL=null;
String anotherServiceURL=null;
String path=null;
String name=null;
Service mainService=null;
Class targetClass=null;
Method subService=null;
Method setterMethod=null;
Object instantiationOfClass=null;
ApplicationDirectory applicationDirectory=null;
ApplicationScope applicationScope=null;
SessionScope sessionScope=null;
RequestScope requestScope=null;
List<AutoWiredWrapper> list=null;
Object value=null;
Class type=null;
Field property=null;
List<RequestedParameter> requestedParameterList=null;
Object []arguments=null;
RequestedParameter requestedParameter=null;

// varaible Declarration ends


System.out.println("DEBUG: "+request.getContextPath());
System.out.println("DEBUG: "+request.getPathInfo());
System.out.println("DEBUG: model size: "+model.dataStructure.size());

key=request.getPathInfo();
serviceURL=key.substring(key.indexOf("/",1));
System.out.println("Service URL: "+serviceURL);


if(model.dataStructure.containsKey(key))
{
System.out.println("God is Great key is there");
mainService=model.dataStructure.get(key);
anotherServiceURL=mainService.getPath().substring(key.indexOf("/",1));

System.out.println("Another Service URL: "+anotherServiceURL);
System.out.println("[doGet] RequestType : "+mainService.getIsGetAllowed());

if(mainService.getIsGetAllowed() && serviceURL.equalsIgnoreCase(anotherServiceURL) )
{
targetClass=mainService.getServiceClass();
subService=mainService.getService();
instantiationOfClass=targetClass.newInstance();


// RequestParameter implementation starts

requestedParameterList=mainService.getRequestedParameterList();
if(requestedParameterList!=null)
{
arguments=new Object[requestedParameterList.size()];

for(int i=0;i<requestedParameterList.size();i++)
{
requestedParameter=requestedParameterList.get(i);
type=requestedParameter.getType();
if(type.equals(long.class) || type.equals(Long.class))
{
arguments[i]=Long.parseLong(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(int.class) || type.equals(Integer.class))
{
arguments[i]=Integer.parseInt(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(short.class) || type.equals(Short.class))
{
arguments[i]=Short.parseShort(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(byte.class) || type.equals(Byte.class))
{
arguments[i]=Byte.parseByte(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(double.class) || type.equals(Double.class))
{
arguments[i]=Double.parseDouble(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(float.class) || type.equals(Float.class))
{
arguments[i]=Float.parseFloat(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(char.class))
{
arguments[i]=request.getParameter(requestedParameter.getKey().trim()).charAt(0);
}
else if(type.equals(boolean.class) || type.equals(Boolean.class))
{
arguments[i]=Boolean.parseBoolean(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(String.class))
{
arguments[i]=request.getParameter(requestedParameter.getKey()).trim();
}
} // loop ends
} // prepare arguments for invoking

// RequestParamter implementation ends

// implementing AutoWire Feature starts
list=mainService.getAutoWired();

if(list!=null)
{
for(AutoWiredWrapper autoWired: list)
{
value=null;
property=autoWired.getProperty();
name=autoWired.getName();
type=autoWired.getType();

value=request.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
value=httpSession.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
value=servletContext.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
}
}
// implementing AutoWire Feature ends

// here code of injecting things or IOC starts

if(mainService.getInjectApplicationDirectory())
{
path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
setterMethod=targetClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setterMethod.invoke(instantiationOfClass,applicationDirectory);
}
if(mainService.getInjectApplicationScope())
{
applicationScope=new ApplicationScope(servletContext);
setterMethod=targetClass.getMethod("setApplicationScope",ApplicationScope.class);
setterMethod.invoke(instantiationOfClass,applicationScope);
}
if(mainService.getInjectSessionScope())
{
sessionScope=new SessionScope(httpSession);
setterMethod=targetClass.getMethod("setSessionScope",SessionScope.class);
setterMethod.invoke(instantiationOfClass,sessionScope);
}
if(mainService.getInjectRequestScope())
{
requestScope=new RequestScope(request);
setterMethod=targetClass.getMethod("setRequestScope",RequestScope.class);
setterMethod.invoke(instantiationOfClass,requestScope);
}
// above code of injecting things or IOC ends
subService.invoke(instantiationOfClass,arguments);
// here forward related code
if(mainService.getForwardTo()!=null && mainService.getForwardTo().length()>0)
{
RequestDispatcher requestDispatcher;
System.out.println("Forwarding: "+request.getServletPath()+key.substring(0,key.indexOf("/",1))+mainService.getForwardTo());
requestDispatcher=request.getRequestDispatcher(request.getServletPath()+key.substring(0,key.indexOf("/",1))+mainService.getForwardTo());
requestDispatcher.forward(request,response);
}
}
else
{
if(serviceURL.equalsIgnoreCase(anotherServiceURL)==false) response.sendError(HttpServletResponse.SC_FOUND);
else response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}
}
else
{
RequestDispatcher requestDispatcher;
requestDispatcher=request.getRequestDispatcher(serviceURL);
requestDispatcher.forward(request,response);
}
}catch(Exception exception)
{
System.out.println("Exception got raised in DoPost");
System.out.println(exception);
}
} // doGet ends

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
// Container fetching starts
ServletContext servletContext=request.getServletContext();
HttpSession httpSession=request.getSession();
// Container fetching ends

// variable Declaration starts
String key=null;
String serviceURL=null;
String anotherServiceURL=null;
String path=null;
String name=null;
Service mainService=null;
Class targetClass=null;
Method subService=null;
Method setterMethod=null;
Object instantiationOfClass=null;
ApplicationDirectory applicationDirectory=null;
ApplicationScope applicationScope=null;
SessionScope sessionScope=null;
RequestScope requestScope=null;
List<AutoWiredWrapper> list=null;
Object value=null;
Class type=null;
Field property=null;
RequestedParameter requestedParameter=null;
List<RequestedParameter> requestedParameterList=null;
Object []arguments=null;

// varaible Declarration ends

System.out.println("DEBUG: "+request.getContextPath());
System.out.println("DEBUG: "+request.getPathInfo());
System.out.println("DEBUG: model size: "+model.dataStructure.size());

key=request.getPathInfo();
serviceURL=key.substring(key.indexOf("/",1));
System.out.println("Service URL: "+serviceURL);

if(model.dataStructure.containsKey(key))
{
System.out.println("God is Great key is there");
mainService=model.dataStructure.get(key);
anotherServiceURL=mainService.getPath().substring(key.indexOf("/",1));
System.out.println("Another Service URL: "+anotherServiceURL);
System.out.println("[doPost]RequestType : "+mainService.getIsPostAllowed());
if(mainService.getIsPostAllowed() && serviceURL.equalsIgnoreCase(anotherServiceURL) )
{
subService=mainService.getService();
targetClass=mainService.getServiceClass();
instantiationOfClass=targetClass.newInstance();

// RequestParameter implementation starts

requestedParameterList=mainService.getRequestedParameterList();
if(requestedParameterList!=null)
{
arguments=new Object[requestedParameterList.size()];

for(int i=0;i<requestedParameterList.size();i++)
{
requestedParameter=requestedParameterList.get(i);
type=requestedParameter.getType();
if(type.equals(long.class) || type.equals(Long.class))
{
arguments[i]=Long.parseLong(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(int.class) || type.equals(Integer.class))
{
arguments[i]=Integer.parseInt(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(short.class) || type.equals(Short.class))
{
arguments[i]=Short.parseShort(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(byte.class) || type.equals(Byte.class))
{
arguments[i]=Byte.parseByte(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(double.class) || type.equals(Double.class))
{
arguments[i]=Double.parseDouble(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(float.class) || type.equals(Float.class))
{
arguments[i]=Float.parseFloat(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(char.class))
{
arguments[i]=request.getParameter(requestedParameter.getKey().trim()).charAt(0);
}
else if(type.equals(boolean.class) || type.equals(Boolean.class))
{
arguments[i]=Boolean.parseBoolean(request.getParameter(requestedParameter.getKey().trim()));
}
else if(type.equals(String.class))
{
arguments[i]=request.getParameter(requestedParameter.getKey()).trim();
}
} // loop ends
} // prepare arguments for invoking

// RequestParamter implementation ends


// implementing AutoWire Feature starts
list=mainService.getAutoWired();

if(list!=null)
{
for(AutoWiredWrapper autoWired: list)
{
value=null;
property=autoWired.getProperty();
name=autoWired.getName();
type=autoWired.getType();

value=request.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
value=httpSession.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
value=servletContext.getAttribute(name);
if(value!=null && type.isInstance(value))
{
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
continue;
}
property.setAccessible(true);
property.set(instantiationOfClass,value);
property.setAccessible(false);
}
}
// implementing AutoWire Feature ends


// here code of injecting things or IOC starts
if(mainService.getInjectApplicationDirectory())
{
path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
setterMethod=targetClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setterMethod.invoke(instantiationOfClass,applicationDirectory);
}
if(mainService.getInjectApplicationScope())
{
applicationScope=new ApplicationScope(servletContext);
setterMethod=targetClass.getMethod("setApplicationScope",ApplicationScope.class);
setterMethod.invoke(instantiationOfClass,applicationScope);
}
if(mainService.getInjectSessionScope())
{
sessionScope=new SessionScope(httpSession);
setterMethod=targetClass.getMethod("setSessionScope",SessionScope.class);
setterMethod.invoke(instantiationOfClass,sessionScope);
}
if(mainService.getInjectRequestScope())
{
requestScope=new RequestScope(request);
setterMethod=targetClass.getMethod("setRequestScope",RequestScope.class);
setterMethod.invoke(instantiationOfClass,requestScope);
}
// above code of injecting things or IOC ends
subService.invoke(instantiationOfClass,arguments);
// here forward related code
if(mainService.getForwardTo()!=null && mainService.getForwardTo().length()>0)
{
RequestDispatcher requestDispatcher;
System.out.println("Forwarding: "+request.getServletPath()+key.substring(0,key.indexOf("/",1))+mainService.getForwardTo());
requestDispatcher=request.getRequestDispatcher(request.getServletPath()+key.substring(0,key.indexOf("/",1))+mainService.getForwardTo());
requestDispatcher.forward(request,response);
}

}
else
{
if(serviceURL.equalsIgnoreCase(anotherServiceURL)==false) response.sendError(HttpServletResponse.SC_FOUND);
else response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}
}
else
{
RequestDispatcher requestDispatcher;
requestDispatcher=request.getRequestDispatcher(serviceURL);
requestDispatcher.forward(request,response);
}
}catch(Exception exception)
{
System.out.println("Exception got raised");
System.out.println(exception);
}
} // doPost ends
} // class ends
