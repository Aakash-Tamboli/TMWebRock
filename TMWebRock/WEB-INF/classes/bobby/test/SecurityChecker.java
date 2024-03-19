package bobby.test;
import com.thinking.machines.webrock.scope.*;

public class SecurityChecker
{
public void check(ApplicationDirectory applicationDirectory,ApplicationScope applicationScope,SessionScope sessionScope,RequestScope requestScope)
{
// here is a code to check where the bobby place credentials on their scopes 
throw new SecurityException("Don't Allow");
}
}
