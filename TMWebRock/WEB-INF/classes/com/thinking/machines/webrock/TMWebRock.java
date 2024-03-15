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
private HttpSession httpSession;
private HttpServletRequest request;
private WebRockModel model;
private Service mainService;
private Class targetClass;
private Object instantiationOfClass;

public TMWebRock()
{
this.servletContext=null;
this.httpSession=null;
this.request=null;
this.model=null;
this.mainService=null;
this.targetClass=null;
this.instantiationOfClass=null;
}

public void init(ServletConfig servletConfig) throws ServletException
{
this.servletContext=servletConfig.getServletContext();
this.model=(WebRockModel)this.servletContext.getAttribute("dataStructure");
}

private Object getAppropriateValue(String val,Class type,boolean isAnnotationApplied)
{
// parsing logic
if(type.equals(long.class) || type.equals(Long.class))
{

if(isAnnotationApplied)
{
try
{
return Long.parseLong(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Long: "+exception.getMessage());
return new Long(-1);
}
}
else
{
return new Long(-1);
}

}
else if(type.equals(int.class) || type.equals(Integer.class))
{

if(isAnnotationApplied)
{
try
{
return Integer.parseInt(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Integer/int: "+exception.getMessage());
return new Integer(-1);
}
}
else
{
return new Integer(-1);
}
}
else if(type.equals(short.class) || type.equals(Short.class))
{

if(isAnnotationApplied)
{
try
{
return Short.parseShort(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Short/short: "+exception.getMessage());
return new Short((short)-1);
}
}
else
{
return new Short((short)-1);
}

}
else if(type.equals(byte.class) || type.equals(Byte.class))
{

if(isAnnotationApplied)
{
try
{
return Byte.parseByte(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Byte/byte: "+exception.getMessage());
return new Byte((byte)-1);
}
}
else
{
return new Byte((byte)-1);
}

}
else if(type.equals(double.class) || type.equals(Double.class))
{

if(isAnnotationApplied)
{
try
{
return Double.parseDouble(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Double/double: "+exception.getMessage());
return new Double((double)-1);
}
}
else
{
return new Double((double)-1);
}

}
else if(type.equals(float.class) || type.equals(Float.class))
{

if(isAnnotationApplied)
{

try
{
return Float.parseFloat(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Float/float: "+exception.getMessage());
return new Float((float)-1);
}
}
else
{
return new Float((float)-1);
}

}
else if(type.equals(char.class))
{
if(isAnnotationApplied)
{

try
{
return val.charAt(0); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at char: "+exception.getMessage());
return " ".charAt(0);
}

}
else
{
return " ".charAt(0);
}

}
else if(type.equals(boolean.class) || type.equals(Boolean.class))
{

if(isAnnotationApplied)
{

try
{
return Boolean.parseBoolean(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Boolean/boolean: "+exception.getMessage());
return new Boolean(false);
}
}
else
{
return new Boolean(false);
}

}
else if(type.equals(String.class))
{

if(isAnnotationApplied)
{

try
{
return val; // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at String/String: "+exception.getMessage());
return "";
}
}
else
{
return "";
}

}
else
{
// It means TMWebRock user gives custom class
return null;
}
}

private Object[] requestParameterFeature()
{
// RequestParameter implementation starts
Object[] arguments=null;
RequestedParameter requestedParameter=null;
List<RequestedParameter> requestedParameterList=null;
ApplicationDirectory applicationDirectory=null;
ApplicationScope applicationScope=null;
SessionScope sessionScope=null;
RequestScope requestScope=null;
String path=null;
String val=null;
String key=null;
Class type=null;

requestedParameterList=mainService.getRequestedParameterList();
if(requestedParameterList!=null)
{
arguments=new Object[requestedParameterList.size()];

path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
applicationScope=new ApplicationScope(servletContext);
sessionScope=new SessionScope(httpSession);
requestScope=new RequestScope(request);

for(int i=0;i<requestedParameterList.size();i++)
{
requestedParameter=requestedParameterList.get(i);
type=requestedParameter.getType();

if(type.equals(ApplicationDirectory.class)) arguments[i]=applicationDirectory;
else if(type.equals(ApplicationScope.class)) arguments[i]=applicationScope;
else if(type.equals(SessionScope.class)) arguments[i]=sessionScope;
else if(type.equals(RequestScope.class)) arguments[i]=requestScope;

if(arguments[i]!=null) continue; // it means any of above condition executed

key=requestedParameter.getKey().trim();

val=request.getParameter(key);

if(val!=null && val.length()!=0) val=val.trim();

arguments[i]=getAppropriateValue(val,type,requestedParameter.getIsAnnotationApplied());

} // loop ends
} // prepare arguments for invoking
return arguments;
}

private void injectRequestParameterFeature()
{
// Implementing InjectRequestParameter or Requested Parameter Property Feature starts
List<RequestedParameterProperty> requestedParameterPropertyList=null;
String key=null;
String val=null;
String nameOfProperty=null;
String setterName=null;
Object oneArgument=null;
Method setterMethod=null;
Class type=null;

requestedParameterPropertyList=mainService.getRequestedParameterPropertyList();
if(requestedParameterPropertyList!=null)
{
for(RequestedParameterProperty requestedParameterProperty: requestedParameterPropertyList)
{
key=requestedParameterProperty.getKey().trim();
type=requestedParameterProperty.getType();
nameOfProperty=requestedParameterProperty.getNameOfProperty();
setterName="set"+nameOfProperty.substring(0,1).toUpperCase()+nameOfProperty.substring(1); // I will clearly specify in docs that framework uses @InjectRequestParameter annotation then user must write setMethod like set+PropertyName()
// debugging purpose only System.out.println("Setter name for requestedParameterProperty: "+setterName);
try
{

val=request.getParameter(key);
if(val!=null && val.length()!=0) val=val.trim();
oneArgument=getAppropriateValue(val,type,true); // here I give true to isAnnotationApplied just for reuse my parsing logic, and it returns default value if some problem
setterMethod=targetClass.getMethod(setterName,type);
setterMethod.invoke(instantiationOfClass,oneArgument);

}catch(NoSuchMethodException noSuchMethodException)
{
System.out.println("TMWebRock Says: "+setterName+" not found in your class that'why unable to put data inside your props");
}catch(Exception exception)
{
System.out.println("TMWebRock Says: I found exception/problem while executing your service, Sorry I cannot execute your service");
System.out.println("Problem is: "+exception.getMessage());
}
} // loop ends
}
// Implementing InjectRequestParameter or Requested Parameter Property Feature ends
}


public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
// Container fetching starts
// servletContext=request.getServletContext(); no need
this.httpSession=request.getSession();
this.request=request;
// Container fetching ends

// variable Declaration starts
String key=null;
String val=null;
String serviceURL=null;
String anotherServiceURL=null;
String path=null;
String name=null;
Method subService=null;
Method setterMethod=null;
ApplicationDirectory applicationDirectory=null;
ApplicationScope applicationScope=null;
SessionScope sessionScope=null;
RequestScope requestScope=null;
List<AutoWiredWrapper> autoWiredList=null;
Object value=null;
Class type=null;
Field property=null;
List<RequestedParameter> requestedParameterList=null;
List<RequestedParameterProperty> requestedParameterPropertyList=null;
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

arguments=requestParameterFeature();

// implementing AutoWire Feature starts
autoWiredList=mainService.getAutoWired();

if(autoWiredList!=null)
{
for(AutoWiredWrapper autoWired: autoWiredList)
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


// Implementing InjectRequestParameter or Requested Parameter Property Feature starts
injectRequestParameterFeature();
// Implementing InjectRequestParameter or Requested Parameter Property Feature ends



// here code of injecting things or IOC starts


if(mainService.getInjectApplicationDirectory())
{
try
{
if(applicationDirectory==null)
{
path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
}
setterMethod=targetClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setterMethod.invoke(instantiationOfClass,applicationDirectory);
}catch(Exception exception)
{
System.out.println("TMWebRock Found Exception either you are not write setter method or doing something wrong");
System.out.println("Exception : "+exception.getMessage());
}
}
if(mainService.getInjectApplicationScope())
{
try
{
if(applicationScope==null)
{
applicationScope=new ApplicationScope(servletContext);
}
// validation pending that setter method is present or not
setterMethod=targetClass.getMethod("setApplicationScope",ApplicationScope.class);
setterMethod.invoke(instantiationOfClass,applicationScope);
}catch(Exception exception)
{
System.out.println("TMWebRock Found Exception either you are not write setter method or doing something wrong");
System.out.println("Exception : "+exception.getMessage());
}
}
if(mainService.getInjectSessionScope())
{
try
{
if(sessionScope==null)
{
sessionScope=new SessionScope(httpSession);
}
setterMethod=targetClass.getMethod("setSessionScope",SessionScope.class);
setterMethod.invoke(instantiationOfClass,sessionScope);
}catch(Exception exception)
{
System.out.println("TMWebRock Found Exception either you are not write setter method or doing something wrong");
System.out.println("Exception : "+exception.getMessage());
}
}
if(mainService.getInjectRequestScope())
{
try
{
if(requestScope==null)
{
requestScope=new RequestScope(request);
}
setterMethod=targetClass.getMethod("setRequestScope",RequestScope.class);
setterMethod.invoke(instantiationOfClass,requestScope);
}catch(Exception exception)
{
System.out.println("TMWebRock Found Exception either you are not write setter method or doing something wrong");
System.out.println("Exception : "+exception.getMessage());
}
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
System.out.println("Exception got raised in Do Get");
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
String val=null;
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

path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
applicationScope=new ApplicationScope(servletContext);
sessionScope=new SessionScope(httpSession);
requestScope=new RequestScope(request);

for(int i=0;i<requestedParameterList.size();i++)
{
requestedParameter=requestedParameterList.get(i);
type=requestedParameter.getType();

if(type.equals(ApplicationDirectory.class))
{
arguments[i]=applicationDirectory;
}
else if(type.equals(ApplicationScope.class))
{
arguments[i]=applicationScope;
}
else if(type.equals(SessionScope.class))
{
arguments[i]=sessionScope;
}
else if(type.equals(RequestScope.class))
{
arguments[i]=requestScope;
}
else if(type.equals(long.class) || type.equals(Long.class))
{
if(requestedParameter.getIsAnnotationApplied())
{
val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Long Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Long.parseLong(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Long: "+exception.getMessage());
arguments[i]=new Long(-1);
}
}
else
{
arguments[i]=new Long(-1);
}
}
else if(type.equals(int.class) || type.equals(Integer.class))
{
if(requestedParameter.getIsAnnotationApplied())
{
val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Integer Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Integer.parseInt(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Integer: "+exception.getMessage());
arguments[i]=new Integer(-1);
}
}
else
{
arguments[i]=new Integer(-1);
}
}
else if(type.equals(short.class) || type.equals(Short.class))
{
if(requestedParameter.getIsAnnotationApplied())
{
val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Short Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Short.parseShort(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Short: "+exception.getMessage());
arguments[i]=new Short((short)-1);
}
}
else
{
arguments[i]=new Short((short)-1);
}
}
else if(type.equals(byte.class) || type.equals(Byte.class))
{

if(requestedParameter.getIsAnnotationApplied())
{
val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Byte Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Byte.parseByte(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Byte: "+exception.getMessage());
arguments[i]=new Byte((byte)-1);
}
}
else
{
arguments[i]=new Byte((byte)-1);
}

}
else if(type.equals(double.class) || type.equals(Double.class))
{
if(requestedParameter.getIsAnnotationApplied())
{

val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Double Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Double.parseDouble(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Double: "+exception.getMessage());
arguments[i]=new Double((double)-1);
}
}
else
{
arguments[i]=new Double((double)-1);
}

}
else if(type.equals(float.class) || type.equals(Float.class))
{

if(requestedParameter.getIsAnnotationApplied())
{

val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Float Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Float.parseFloat(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Float: "+exception.getMessage());
arguments[i]=new Float((float)-1);
}
}
else
{
arguments[i]=new Float((float)-1);
}


}
else if(type.equals(char.class))
{

if(requestedParameter.getIsAnnotationApplied())
{

val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Char Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=val.charAt(0); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Char: "+exception.getMessage());
arguments[i]="  ".charAt(0);
}
}
else
{
arguments[i]="  ".charAt(0);
}

}
else if(type.equals(boolean.class) || type.equals(Boolean.class))
{

if(requestedParameter.getIsAnnotationApplied())
{

val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (Boolean Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=Boolean.parseBoolean(val); // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at Boolean: "+exception.getMessage());
arguments[i]=new Boolean(false);
}
}
else
{
arguments[i]=new Boolean(false);
}

}
else if(type.equals(String.class))
{

if(requestedParameter.getIsAnnotationApplied())
{

val=request.getParameter(requestedParameter.getKey().trim());
try
{
if(val==null) throw new Exception(requestedParameter.getKey()+" (String Type) is not arrived at server side ~TMWebRock");
val=val.trim();
arguments[i]=val; // I will specify in docs if they method has multple parameter and only few of them applied annoation or in at server arrived data not found in your given RequestParameter(here) then default value of your parameter reffer to github.
}catch(Exception exception)
{
System.out.println("TMWebRock Says Parsing Problem at String: "+exception.getMessage());
arguments[i]="";
}
}
else
{
arguments[i]="";
}
}
else
{
// It means TMWebRock user gives custom class
arguments[i]=null;
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
if(applicationDirectory==null)
{
path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
applicationDirectory=new ApplicationDirectory(new File(path));
}
setterMethod=targetClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setterMethod.invoke(instantiationOfClass,applicationDirectory);
}
if(mainService.getInjectApplicationScope())
{
if(applicationScope==null)
{
applicationScope=new ApplicationScope(servletContext);
}
setterMethod=targetClass.getMethod("setApplicationScope",ApplicationScope.class);
setterMethod.invoke(instantiationOfClass,applicationScope);
}
if(mainService.getInjectSessionScope())
{
if(sessionScope==null)
{
sessionScope=new SessionScope(httpSession);
}
setterMethod=targetClass.getMethod("setSessionScope",SessionScope.class);
setterMethod.invoke(instantiationOfClass,sessionScope);
}
if(mainService.getInjectRequestScope())
{
if(requestScope==null)
{
requestScope=new RequestScope(request);
}
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
