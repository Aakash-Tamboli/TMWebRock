package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ServingJSFile extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter out=response.getWriter();

response.setContentType("text/javascript");

String jsFileName=request.getParameter("name").trim();
if(jsFileName!=null && jsFileName.length()==0) throw new Exception("Problem while specify jsFileName inside script tag");

ServletContext servletContext=getServletContext();

String jsPath=servletContext.getRealPath(".")+File.separator+"WEB-INF"+File.separator+"js"+File.separator+jsFileName;

File file=new File(jsPath);

if(file.exists()==false)  throw new Exception("Problem while specify jsFileName inside script tag");

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");

while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
out.println(randomAccessFile.readLine());
}
out.flush();

}catch(Exception exception)
{
System.out.println("\n\n\n ServingJSFile Servlet Says: \n"+exception.getMessage());
}
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(response.SC_METHOD_NOT_ALLOWED);
}catch(Exception exception)
{
System.out.println("\n\n\n ServingJSFile Servlet Says: \n"+exception.getMessage());
}
}
}
