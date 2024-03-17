package com.thinking.machines.webrock.scope;
import javax.servlet.*;
import javax.servlet.http.*;

public class RequestScope
{
private HttpServletRequest httpServletRequest;
public RequestScope()
{
this.httpServletRequest=null;
}
public RequestScope(HttpServletRequest httpServletRequest)
{
this.httpServletRequest=httpServletRequest;
}
// setter starts
public void setAttribute(String key,Object value)
{
this.httpServletRequest.setAttribute(key,value);
}
// setter ends
// getter starts
public Object getAttribute(String key)
{
return this.httpServletRequest.getAttribute(key);
}
// getter ends
}
