package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/attendence")
@SecuredAccess(checkPost="bobby.test.SecurityChecker",guard="check")
public class Attendence
{

@GET
@Path("/doSomething")

public void doSomething(ApplicationDirectory applicationDirectory,ApplicationScope applicationScope,SessionScope sessionScope,RequestScope requestScope)
{
if(applicationDirectory!=null) System.out.println("TMWebRock Injected "+ApplicationDirectory.class.getSimpleName());
if(applicationScope!=null) System.out.println("TMWebRock Innjected "+ApplicationScope.class.getSimpleName());
if(sessionScope!=null) System.out.println("TMWebRock Innjected "+SessionScope.class.getSimpleName());
if(requestScope!=null) System.out.println("TMWebRock Innjected "+RequestScope.class.getSimpleName());
String authentication=(String)sessionScope.getAttribute("valid");
if(authentication!=null && authentication.equals("1234")) System.out.println("Yes user logged in");
else throw new SecurityException("You are not logged in");
}

}
// @SecuredAccess(checkPost="bobby.test.SecurtyChecker",gaurd="check") first test for class then method
