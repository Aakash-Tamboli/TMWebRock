# RequestParameter
In order to get the arrived data at server from client. TMWebRock Framework Provides a following way to get the data into your class.

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

Now TMWebRock Server you just need to add Annotation in their parameter as above e snipped and rest of the things will be take care by TMWebRock Framework.

Important Note:
1) If you add RequestParameter Annotation into your service 's parameter and that specified value not arrived at Server Side then your specified variable will contains following default values

2) Similary If you call some service in your class and those service have parameter and you not added RequestParameter Annotation then always remember that your variable contains default values as following picture.

![Unable to show images clone this repo.](/Documentation/images/1.png) <br>

![Unable to show images clone this repo.](/Documentation/images/2.png) <br>




eg: 
```java

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

```