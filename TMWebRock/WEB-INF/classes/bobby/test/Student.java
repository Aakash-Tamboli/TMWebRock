package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/student")
public class Student
{
/*

@InjectRequestParameter("name")
private String studentName;
@InjectRequestParameter("roll")
private int rollNumber;
@InjectRequestParameter("")
private boolean checkingBug; // just for check


public void setStudentName(String studentName)
{
this.studentName=studentName;
}

public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}

@GET
@Path("/xxi")
public void doSomething()
{
System.out.println("It Worked: Student Name: "+studentName);
System.out.println("It Worked: Student Roll: "+rollNumber);
System.out.println("Checking bug value should be according to what you set generally it is false");
System.out.println("It Worked: checking bug: "+checkingBug);
}

*/


/*
@POST
@Path("/add2")
public void add2()
{
System.out.println("100% I know JSON will come from client side, but I don't want to do with json, I just want this service should be invoke by TMWebRock framework");
}
*/

/*
@POST
@Path("/add2")
public void add2(Person p1)
{
System.out.println("100% I know JSON will come from client side");
System.out.println("Because Every Human is Student");
System.out.println("So name is: "+p1.getName());
System.out.println("So age is: "+p1.getAge());
}
*/



@POST
@Path("/add2")
public void add2(Person p1,ApplicationDirectory applicationDirectory,ApplicationScope applicationScope,SessionScope sessionScope,RequestScope requestScope)
{
System.out.println("100% I know JSON will come from client side");
System.out.println("Because Every Human is Student");
System.out.println("So name is: "+p1.getName());
System.out.println("So age is: "+p1.getAge());
}
}
