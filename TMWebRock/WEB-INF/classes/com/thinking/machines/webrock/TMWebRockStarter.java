package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.webrock.model.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;

public class TMWebRockStarter extends HttpServlet
{

private void dealingWithStartupServices(List<Service> startupList)
{
boolean notVoid=false;
boolean parametersArgs=false; // later on it will change
boolean invalidPriority=false; // I will clearly specify that priority should be start from >=0 otherwise framework will not execute startupFunction

Service service;
Class c;
Method m;
Class returnType;
Class[] parameters;
System.out.println("Size of Startup List: "+startupList.size());
Collections.sort(startupList,(Service s1,Service s2)->{
return s1.getPriority()-s2.getPriority();
});

try
{

for(int i=0;i<startupList.size();i++)
{
service=startupList.get(i);
if(service.getPriority()<0) invalidPriority=true;
c=service.getServiceClass();
m=service.getService();
returnType=m.getReturnType();
if(returnType.equals(Void.TYPE)==false) notVoid=true; // https://stackoverflow.com/questions/1924253/how-to-determine-by-reflection-if-a-method-returns-void
parameters=m.getParameterTypes();
if(parameters!=null && parameters.length!=0) parametersArgs=true;
if(notVoid || parametersArgs || invalidPriority)
{
if(notVoid)
{
System.out.println("METHOD TYPE SHOULD BE VOID Your class have :"+returnType.getSimpleName());
notVoid=false; // next cycle
}
if(parametersArgs)
{
System.out.println("YOUR METHOD SHOULD NOT TAKE ANY Args, Your method taking args");
parametersArgs=false; // next cycle
}
if(invalidPriority)
{
System.out.println("PRIORITY SHOULD BE >=0 AND REMEBER LOWER THE NUMBER HIGHER THE PRIORITY, you give priority: "+service.getPriority());
invalidPriority=false; // next cycle
}
continue;
}
m.invoke(c.newInstance());
} // loop ends
}catch(Exception exception)
{
System.out.println("LOCATION: TMWEBROCKSTARTUP -> METHOD dealingWithStartupServices");
System.out.println(exception);
}
}

private List<String> findAllClassesName(String knownFact,String prefix)
{
File file=new File(knownFact);
Stack<File> stack=new Stack<>();
List<String> list=new ArrayList<>();
stack.push(file);
String str="";
File[] f;
while(!stack.empty())
{
file=stack.pop();
if(file.isFile())
{
str=file.getName();
if(str.substring(str.length()-6).equals(".class"))
{
str=file.getAbsolutePath().replaceAll(File.separator,".");
str=str.substring(str.indexOf(prefix));
str=str.substring(0,str.length()-6);
list.add(str);
}
}
else if(file.isDirectory())
{
f=file.listFiles();
for(File f1: f) stack.push(f1);
}
}// loop ends
return list;
}

private List<Service> populateNecessaryDS(List<String> list,ServletConfig servletConfig)
{
Class c;
Path path;
OnStartup onStartup;
Forward forward;
String str=null;
String str2=null;
String key=null;
String name=null;
boolean isGetAllowed=false;
boolean isPostAllowed=false;
String forwardTo=null;
int priority;
Service service=null;
List<AutoWiredWrapper> autoWiredList=null;
Field []props=null;
Field property=null;
Class type=null;
Parameter []parameter=null;
List<RequestedParameter> requestedParameterList=null;
List<RequestedParameterProperty> requestedParameterPropertyList=null;


boolean injectApplicationDirectory=false;
boolean injectApplicationScope=false;
boolean injectSessionScope=false;
boolean injectRequestScope=false;


int objectCount=1; // DEBUGGING PUPOSE

List<Service> startupList=new ArrayList<>();
WebRockModel model=new WebRockModel();

try
{

for(String classes: list)
{
c=Class.forName(classes);

// analyzing property of framework user starts

props=c.getDeclaredFields();
autoWiredList=null;
requestedParameterPropertyList=null;


if(props!=null && props.length!=0)
{
autoWiredList=new ArrayList<>();
requestedParameterPropertyList=new ArrayList<>();

for(int i=0;i<props.length;i++)
{
if(props[i].isAnnotationPresent(AutoWired.class))
{
property=props[i];
name=props[i].getAnnotation(AutoWired.class).name();
type=props[i].getType();
autoWiredList.add(new AutoWiredWrapper(property,name,type));
}
else if(props[i].isAnnotationPresent(InjectRequestParameter.class))
{
key=props[i].getAnnotation(InjectRequestParameter.class).value();
if(key==null || key.length()==0) continue; // if user not giving key then why should I kept on my ds
type=props[i].getType();
name=props[i].getName();
requestedParameterPropertyList.add(new RequestedParameterProperty(type,key,name));
}
} // loop ends

// Why Should keep empty list in my Service Class object that why I null them 
if(autoWiredList.size()==0) autoWiredList=null;
if(requestedParameterPropertyList.size()==0) requestedParameterPropertyList=null;

} // scanning props of Class

// analyzing property of framework user ends

if(c.isAnnotationPresent(Path.class))
{
path=(Path)c.getAnnotation(Path.class);
str=path.value();
if(str.length()==0) continue;
if(str.charAt(0)!='/') str="/"+str; // for less error prone code
if(c.isAnnotationPresent(InjectApplicationDirectory.class)) injectApplicationDirectory=true;
if(c.isAnnotationPresent(InjectApplicationScope.class)) injectApplicationScope=true;
if(c.isAnnotationPresent(InjectSessionScope.class)) injectSessionScope=true;
if(c.isAnnotationPresent(InjectRequestScope.class)) injectRequestScope=true;


if(c.isAnnotationPresent(GET.class))
{
isGetAllowed=true;
isPostAllowed=false;
}
else if(c.isAnnotationPresent(POST.class))
{
isPostAllowed=true;
isGetAllowed=false;
}


// method loop starts
for(Method m: c.getDeclaredMethods())
{

// Analysis of Parameter in Method starts

parameter=m.getParameters();
requestedParameterList=null;
if(parameter!=null && parameter.length!=0)
{
requestedParameterList=new ArrayList<>();
for(int i=0;i<parameter.length;i++)
{
if(parameter[i].isAnnotationPresent(RequestParameter.class))
{
name=parameter[i].getAnnotation(RequestParameter.class).value();
type=parameter[i].getType();
requestedParameterList.add(new RequestedParameter(type,name,true));
}
else
{
name=""; // empty string
type=parameter[i].getType();
requestedParameterList.add(new RequestedParameter(type,name,false));
} // if-else ends
} // loop ends
} // if ends
// Analysis of Parmeter in Method ends



if(m.isAnnotationPresent(OnStartup.class)) priority=m.getAnnotation(OnStartup.class).Priority();
else priority=-1;
if(m.isAnnotationPresent(Path.class))
{
path=m.getAnnotation(Path.class);
str2=path.value();
if(str2.length()==0) continue;
if(c.isAnnotationPresent(GET.class)==false && c.isAnnotationPresent(POST.class)==false) // it means class level does not have niether @GET nor @POST so I have to check on method level
{
if(m.isAnnotationPresent(GET.class))
{
isGetAllowed=true;
isPostAllowed=false;
}
else if(m.isAnnotationPresent(POST.class))
{
isPostAllowed=true;
isGetAllowed=false;
}
else
{
isGetAllowed=true;
isPostAllowed=true;
}
}
if(m.isAnnotationPresent(Forward.class))
{
forward=m.getAnnotation(Forward.class);
forwardTo=forward.value();
if(forwardTo.length()==0) forwardTo=null;
}

System.out.println("---------------------"+objectCount+"---------------------");
System.out.println("Service Object created with ");
System.out.println("ClassName: "+c.getSimpleName());
System.out.println("Full Path of service: "+str+str2);
System.out.println("Serivce Forward To: "+forwardTo);
System.out.println("Method which considerd as service: "+m.getName());
System.out.println("For This Service GET ALLOWED: "+isGetAllowed);
System.out.println("For This Service POST ALLOwED: "+isPostAllowed);
System.out.println("Startup Service "+false);
System.out.println("prioity No: "+priority);
System.out.println("Class Required Inject Application Directory: "+injectApplicationDirectory);
System.out.println("Class Required Inject Application Scope: "+injectApplicationScope);
System.out.println("Class Required Inject Session Scope: "+injectSessionScope);
System.out.println("Class Required Inject Request Scope: "+injectRequestScope);
System.out.println("Does Class Have Property: "+autoWiredList);
System.out.println("Does Class property uses Inject Request Parameter annotation: "+requestedParameterPropertyList);
System.out.println("---------------------"+objectCount+"---------------------");
objectCount++;

service=new Service(c,str+str2,forwardTo,m,isGetAllowed,isPostAllowed,false,priority,injectApplicationDirectory,injectApplicationScope,injectSessionScope,injectRequestScope,autoWiredList,requestedParameterList,requestedParameterPropertyList);
model.dataStructure.put(str+str2,service);
forwardTo=null; // for next cycle
}
else
{
if(priority!=-1)
{
System.out.println("---------------------"+objectCount+"---------------------");
System.out.println("Service Object created with ");
System.out.println("ClassName: "+c.getSimpleName());
System.out.println("Full Path of service: "+"ONLY_FOR_STARTUP");
System.out.println("Serivce Forward To: "+"ONLY_FOR_STARTUP");
System.out.println("Method which considerd as Startup service: "+m.getName());
System.out.println("For This Service GET ALLOWED: "+false);
System.out.println("For This Service POST ALLOwED: "+false);
System.out.println("Startup Service "+true);
System.out.println("prioity No: "+priority);
System.out.println("---------------------"+objectCount+"---------------------");
objectCount++;
service=new Service(c,"ONLY_FOR_STARTUP","ONLY_FOR_STARTUP",m,false,false,true,priority,injectApplicationDirectory,injectApplicationScope,injectSessionScope,injectRequestScope,autoWiredList,requestedParameterList,requestedParameterPropertyList);
startupList.add(service); // think about min-heap Aakash If Sir give you instruction you can implement it
}
}
} // method loop ends
} // PATH if block ends
// for next cycle
injectApplicationDirectory=false;
injectApplicationScope=false;
injectSessionScope=false;
injectRequestScope=false;
} // outer loop ends


}catch(ClassNotFoundException cnfe)
{
System.out.println("Problem");
}


ServletContext servletContext=servletConfig.getServletContext(); //https://www.javatpoint.com/servletcontext
/*
self reference:
when we want applicationlevelContainer in servlet, we have request pointer/refference varaible to extract servletContext a.k.a application level container
when we want applicationlevelContainer at init method, here we just have servletConfig pointer, so we can extract servletContext a.k.a application level container through servletConfig object

*/
servletContext.setAttribute("dataStructure",model);
System.out.println("HashMap Size is: "+model.dataStructure.size());

return startupList;
} // method ends


public void init(ServletConfig servletConfig) throws ServletException
{
System.out.println("\n\n\n\t\t\t\t TMWebRockStarter is in action \n\n\n\n");
String prefix=servletConfig.getInitParameter("SERVICE_PACKAGE_PREFIX");
System.out.println("["+prefix+"]\n");

/*
System.getProperty("cataline.base"); // resource from https://stackoverflow.com/questions/48748318/how-to-get-the-path-upto-webapps-folder-of-tomcat-in-servlet#:~:text=1%20Answer&text=String%20webApp%20%3D%20String.,the%20path%20will%20always%20work.
*/

String knownFact=System.getProperty("catalina.base")+File.separator+"webapps"+File.separator+"TMWebRock"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+prefix;
List<String> list=findAllClassesName(knownFact,prefix);
List<Service> startupList=populateNecessaryDS(list,servletConfig);
dealingWithStartupServices(startupList);
}
}
