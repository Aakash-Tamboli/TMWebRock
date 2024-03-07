# On Startup 
TMWebRock Framework provides feature to execute Initialization Methods at Server Startup time. 

## Rules
<b>If You break any rule then method will not be executed by TMWebRock</b>

1) `@OnStartup` Annotation is only applicable on methods
2) Method return type should be void and Method should not take any Parameter (I will update this feature later on)
3) Priority Rule is `Lower the Number Higher the priority` but number should greater than or equals to >= 0

Consider the following code as an example:

```java

package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.scope.*;


@Path("/hospital")
public class Medical
{
// According to Requirement Props
@OnStartup(Priority=1)
public void startupFunction1()
{
System.out.println("TMWebRock Successfully executed startupfunction1 at server startup");
}
@OnStartup(Priority=2)
public void startupFunction2()
{
System.out.println("TMWebRock Successfully executed startupfunction2 at server startup");
}

// lots of Services
@Path("/mediceneTime")
@Forward("/Aakash.jsp")
public void giveMeds()
{
// Code to give Meds
}
}
```
