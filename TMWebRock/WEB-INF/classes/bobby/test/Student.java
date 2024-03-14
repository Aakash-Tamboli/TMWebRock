package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/student")
public class Student
{

@GET
@Path("/add")
public void testRequestedParmeter(long l,int i,short s,byte b,double d,float f,char ch,boolean bool,@RequestParameter("name") String str)
{
System.out.println("[GET] TestSuccessful");
System.out.println("Name: "+str);
System.out.println("long default value is: "+l);
System.out.println("int default value is: "+i);
System.out.println("short default value is: "+s);
System.out.println("byte default value is: "+b);
System.out.println("double default value is: "+d);
System.out.println("float default value is: "+f);
System.out.println("char default value is: "+ch);
System.out.println("boolean default value is: "+bool);
}

@POST
@Path("/add2")
public void testRequestedParmeter2(long l,int i,short s,byte b,double d,float f,char ch,boolean bool,String str)
{
System.out.println("[POST] TestSuccessful");
System.out.println("Name: "+str);
System.out.println("long default value is: "+l);
System.out.println("int default value is: "+i);
System.out.println("short default value is: "+s);
System.out.println("byte default value is: "+b);
System.out.println("double default value is: "+d);
System.out.println("float default value is: "+f);
System.out.println("char default value is: "+ch);
System.out.println("boolean default value is: "+bool);
System.out.println("String default value is: "+str);
}
}
