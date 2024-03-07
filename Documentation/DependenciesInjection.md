# Dependencies injection
It means Framework user wants may be [ApplicationDirectory](/Documentation/Dependencies/ApplicationDirectory.md), [ApplicationScope](/Documentation/Dependencies/ApplicationScope.md), [SessionScope](/Documentation/Dependencies/SessionScope.md), [RequestScope](/Documentation/Dependencies/RequestScope.md).

### Rules related to Inject
<b>If you break any rule then you won't able to inject dependencies</b>

1) Dependencies Injection Annotation only Applied on Classes not on Method
2) Framework user/You Must take property of your desired Dependencies.
3) Framework user/You must write Appropriate setter method along with approprite parameter for Dependenies you taken as property.
4) Setter Method Name should be Stricly follow as mentioned below.
    ```java
    public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
    {
        this.yourTakenProps=applicationScope;
    }
    public void setApplicationScope(ApplicationScope applicationScope)
    {
        this.yourTakenProps=applicationDirectory;
    }
    public void setSessionScope(SessionScope sessionScope)
    {
        this.yourTakenProps=sessionScope;
    }
    public void setRequestScope(RequestScope requestScope)
    {
        this.yourTakenProps=requestScope
    }
    ```



### Annotations
[@ApplicationScope](#) <br>
[@ApplicationScope](#) <br>
[@SessionScope](#) <br>
[@RequestScope](#) <br>

consider following code to inject dependencies in your class

```java

package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.scope.*;


@InjectApplicationDirectory
@InjectApplicationScope
@InjectSessionScope
@InjectRequestScope
@Path("/hospital")
public class Medical
{
private ApplicationDirectory applicationDirectory; 
private ApplicationScope applicationScope;
private SessionScope sessionScope;
private RequestScope requestScope;

public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
}

public void setApplicationScope(ApplicationScope applicationScop)
{
this.applicationScope=applicationScope;
}

public void setSessionScope(SessionScope sessionScope)
{
this.sessionScope=sessionScope;
}

public void setRequestScope(RequestContainer requestScope)
{
this.requestScope=requestScope;
}

// lot of services those are using dependencies
}
```