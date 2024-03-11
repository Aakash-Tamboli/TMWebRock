# RequestParameter
In order to get the arrived data at server from client. TMWebRock Framewrk Provides a following way to get the data into your class.

1) [RequestScope](/Documentation/Dependencies/RequestScope.md)
2) RequestParameter

Ex:
Consider There is class which have service named `testRequestedParmeter` here from client side name will be come to server,

```html
<form action='/TMWebRock/school/student/add'>
    Name: &emsp; <input type='text' name='name'>
    <button type='submit'>Send</button>
</form>

```

```java
public void testRequestedParmeter(@RequestParameter("name") String name)
{
System.out.println("[GET] TestSuccessful");
System.out.println("Name: "+name);
}
```

Now TMWebRock Server just need to add Annotation in their parameter as above e snipped and rest of the things will be taken by TMWebRock Server.

Important Note:
If you add RequestParameter Annotation into your service 's parameter and that specified value not arrived at Server Side then TMWebRock Not Process and you get Internal Error Server Page.

eg: 
```java

@GET
@Path("/add")
public void testRequestedParmeter(@RequestParameter("name1") String name,@RequestParameter int p) // here int p is never arrived at server side because above form only send name thing to server.
{
System.out.println("[GET] TestSuccessful");
System.out.println("Name: "+name);
}
```

Conside the following Class:

```java
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
```