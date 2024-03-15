# Injecting Client Data into Service Class Propert

I will writing after sometime, for self refference "It uses @InjectRequestParameter Annoation"


Testcase:
```java
package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/student")
public class Student
{
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

@POST
@Path("/add2")
public void add2(int rollNumber)
{
// nothing
}
}




```