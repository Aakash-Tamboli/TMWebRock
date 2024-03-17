# Get JSON data into your service
TMWebRock Framework provide facility to get JSON object into your object, only you have follow following rules

## Rules
TMWebRock Framework sense your service, that your service need a JSON object or you want to tell to TMWebRock that "hey JSON is comming, I want to do something"

So Rules are

1) Your service means method should only apply Path annotation and either GET or POST annotation, if anything you applied then your service is not considered that you want that JSON object.
2) Your service means method should have any no. of parameter but 1-parameter should belongs to custom class (which you want JSON data into that) and rest of the parameters should be ApplicationDirectory, ApplicationScope, SessionScope, RequestScope . Any No. of times but NO PRIMITIVE and NO WRAPPER CLASS.


testcase, I will organize later on

```java
package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/student")
public class Student
{
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
```