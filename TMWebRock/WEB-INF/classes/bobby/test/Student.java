package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/student")
public class Student
{

@GET
@Path("/add")
public void testRequestedParmeter(@RequestParameter("name") String name)
{
System.out.println("[GET] TestSuccessful");
System.out.println("Name: "+name);
}

@POST
@Path("/add2")
public void testRequestedParmeter2(@RequestParameter("name") String name)
{
System.out.println("[POST] TestSuccessful");
System.out.println("Name: "+name);
}

}
