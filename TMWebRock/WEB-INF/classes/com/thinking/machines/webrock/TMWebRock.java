package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import com.google.gson.*;
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
private ApplicationDirectory applicationDirectory;
private ApplicationScope applicationScope;
private SessionScope sessionScope;
private RequestScope requestScope;

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
String path=servletContext.getRealPath("/"); //https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it
this.applicationDirectory=new ApplicationDirectory(new File(path));
this.applicationScope=new ApplicationScope(servletContext);
this.sessionScope=new SessionScope(httpSession);
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





private Object[] requestParameterFeature() throws Exception
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
Gson gson=null;


requestedParameterList=mainService.getRequestedParameterList();

System.out.println("request Parameter feature method invoked");

if(requestedParameterList!=null)
{
arguments=new Object[requestedParameterList.size()];
System.out.println("Method have Number of arguments: "+requestedParameterList.size());


for(int i=0;i<requestedParameterList.size();i++)
{
requestedParameter=requestedParameterList.get(i);
type=requestedParameter.getType();

if(type.equals(ApplicationDirectory.class)) arguments[i]=applicationDirectory;
else if(type.equals(ApplicationScope.class)) arguments[i]=applicationScope;
else if(type.equals(SessionScope.class)) arguments[i]=sessionScope;
else if(type.equals(RequestScope.class)) arguments[i]=requestScope;

if(arguments[i]!=null) continue; // it means any of above condition executed

// here I think I have to put JSON feature code starts

// if service tells JSON the only assignemtn in arguments[i] will be come up and rest will be ignored means continue key

if(mainService.getIsJSONRequired())
{
System.out.println("Parameter Type: "+type.getSimpleName());

try
{
BufferedReader bufferedReader=request.getReader();
StringBuffer stringBuffer=new StringBuffer();
String chunk;

while(true)
{
chunk=bufferedReader.readLine();
if(chunk==null) break;
stringBuffer.append(chunk);
} // loop ends

String json=stringBuffer.toString();
System.out.println("JSON TMWebRock recieve: "+json);

gson=new Gson();

Object object=gson.fromJson(json,type);
arguments[i]=object;


}catch(Exception exception)
{
throw exception;
}
continue;
}
// here I think I have to put JSON feature code ends

key=requestedParameter.getKey().trim();

val=request.getParameter(key);

if(val!=null && val.length()!=0) val=val.trim();

arguments[i]=getAppropriateValue(val,type,requestedParameter.getIsAnnotationApplied());

} // loop ends
} // prepare arguments for invoking

System.out.println(1010101);
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
/*
I specifically choose name thing because if I go with type then in that situation framework user needs to apply some logic in setter method , if user have two props with same type in their class.
So user take two props with same type then user needs to take two different varaible So, user wants to use this feature then user must write setter method with naming convection is according to variable name.
*/
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
this.requestScope=new RequestScope(request);
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
SecurityException securityException=null;

Object instantiationOfGuardClass=null;
Guard guard=null;
Method guardService=null;
Parameter []guardParameters=null;


// varaible Declarration ends


System.out.println("DEBUG: "+request.getContextPath());
System.out.println("DEBUG: "+request.getPathInfo());
System.out.println("DEBUG: model size: "+model.dataStructure.size());

key=request.getPathInfo();
System.out.println("value of key: "+key);
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

// checking security Guard starts

if(mainService.getGuard()!=null)
{

System.out.println("Get Guard ackslmkcsamksacmkcsam");

guard=mainService.getGuard();

try
{
instantiationOfGuardClass=guard.getGuardClass().newInstance();
guardService=guard.getGuardService();

guardParameters=guardService.getParameters();
arguments=new Object[guardParameters.length]; // validation later on , what if bobby Method does not have anything but I think I checked in starter but you will see later on

for(int i=0;i<guardParameters.length;i++)
{
if(guardParameters[i].equals(ApplicationDirectory.class)) arguments[i]=applicationDirectory;
else if(guardParameters[i].equals(ApplicationScope.class)) arguments[i]=applicationScope;
else if(guardParameters[i].equals(SessionScope.class)) arguments[i]=sessionScope;
else if(guardParameters[i].equals(RequestScope.class)) arguments[i]=requestScope;
}

guardService.invoke(instantiationOfGuardClass,arguments);
}catch(InvocationTargetException invocationTargetException)
{
System.out.println("Thinking + Hardwork = Success");
if(invocationTargetException.getCause() instanceof SecurityException) securityException=(SecurityException)invocationTargetException.getCause();
response.sendError(response.SC_NOT_FOUND,securityException.getMessage());
return;
}

}
// checking security Guard ends

// JSON feature starts
try
{
arguments=requestParameterFeature();
}catch(Exception exception)
{
System.out.println("Unable to parse JSON data into your parameters, please reffer docs for rectify your mistake");
System.out.println("Problem: "+exception.getMessage());
response.sendError(response.SC_INTERNAL_SERVER_ERROR,"Unable to parse JSON data into your parameters, please reffer docs for rectify your mistake");
return;
}



// JSON feature ends



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
try
{
subService.invoke(instantiationOfClass,arguments);
}catch(InvocationTargetException invocationTargetException)
{
System.out.println("Method not invoked: "+invocationTargetException.getCause().getMessage());
response.sendError(response.SC_INTERNAL_SERVER_ERROR,invocationTargetException.getCause().getMessage());
return;
}


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
Guard guard=null;
SecurityException securityException=null;


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
System.out.println("[doPost] RequestType : "+mainService.getIsPostAllowed());


if(mainService.getIsPostAllowed() && serviceURL.equalsIgnoreCase(anotherServiceURL) )
{

targetClass=mainService.getServiceClass();
subService=mainService.getService();
instantiationOfClass=targetClass.newInstance();

// checking security Guard starts

if(mainService.getGuard()!=null)
{

System.out.println("Get Guard ackslmkcsamksacmkcsam");

guard=mainService.getGuard();

try
{
Object instantiationOfGuardClass=guard.getGuardClass().newInstance();
Method guardService=guard.getGuardService();

Parameter []guardParameters=guardService.getParameters();
arguments=new Object[guardParameters.length]; // validation later on , what if bobby Method does not have anything but I think I checked in starter but you will see later on

for(int i=0;i<guardParameters.length;i++)
{
if(guardParameters[i].equals(ApplicationDirectory.class)) arguments[i]=applicationDirectory;
else if(guardParameters[i].equals(ApplicationScope.class)) arguments[i]=applicationScope;
else if(guardParameters[i].equals(SessionScope.class)) arguments[i]=sessionScope;
else if(guardParameters[i].equals(RequestScope.class)) arguments[i]=requestScope;
}

guardService.invoke(instantiationOfGuardClass,arguments);
}catch(InvocationTargetException invocationTargetException)
{
System.out.println("Thinking + Hardwork = Success");
if(invocationTargetException.getCause() instanceof SecurityException) securityException=(SecurityException)invocationTargetException.getCause();
response.sendError(response.SC_NOT_FOUND,securityException.getMessage());
return;
}

}
// checking security Guard ends



// JSON feature starts
try
{
arguments=requestParameterFeature();
}catch(Exception exception)
{
System.out.println("Unable to parse JSON data into your parameters, please reffer docs for rectify your mistake");
System.out.println("Problem: "+exception.getMessage());
response.sendError(response.SC_INTERNAL_SERVER_ERROR,"Unable to parse JSON data into your parameters, please reffer docs for rectify your mistake");
return;
}



// JSON feature ends

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
try
{
subService.invoke(instantiationOfClass,arguments);
}catch(InvocationTargetException invocationTargetException)
{
response.sendError(response.SC_INTERNAL_SERVER_ERROR,invocationTargetException.getCause().getMessage());
return;
}

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
System.out.println("Exception got raised in DoGet");
System.out.println(exception);
}

} // doPost ends

} // class ends
