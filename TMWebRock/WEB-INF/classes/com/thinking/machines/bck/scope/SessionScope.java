package com.thinking.machines.webrock.scope;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionScope
{
private HttpSession httpSession;
public SessionScope()
{
this.httpSession=null;
}
public SessionScope(HttpSession httpSession)
{
this.httpSession=httpSession;
}
// setter starts
public void setAttribute(String key,Object value)
{
this.httpSession.setAttribute(key,value);
}
// setter ends
// getter starts
public Object getAttribute(String key)
{
return this.httpSession.getAttribute(key);
}
// getter ends
}
