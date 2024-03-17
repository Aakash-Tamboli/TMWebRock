package com.thinking.machines.webrock.scope;
import javax.servlet.*;

public class ApplicationScope
{
private ServletContext servletContext;
public ApplicationScope()
{
this.servletContext=null;
}
public ApplicationScope(ServletContext servletContext)
{
this.servletContext=servletContext;
}

// setter starts

public void setAttribute(String key,Object value)
{
this.servletContext.setAttribute(key,value);
}

// setter ends
// getter starts
public Object getAttribute(String key)
{
return this.servletContext.getAttribute(key);
}
// getter ends
}
