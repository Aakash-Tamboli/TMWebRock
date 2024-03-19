package bobby.test;
import com.thinking.machines.webrock.scope.*;
import com.thinking.machines.webrock.annotations.*;


@Path("/login")
public class Login
{
@POST
@Path("/loginSessionActive")
@Forward("/Homepage.jsp")
public void add2(SessionScope sessionScope)
{
// Assume Info comes from client side and they do something generate token and put into sessionScope
sessionScope.setAttribute("valid","1234");
}
}
