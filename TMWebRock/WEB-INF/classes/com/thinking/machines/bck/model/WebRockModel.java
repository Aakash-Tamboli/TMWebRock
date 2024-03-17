package com.thinking.machines.webrock.model;
import javax.servlet.*;
import java.util.*;
import com.thinking.machines.webrock.pojo.*;

public class WebRockModel
{
public Map<String,Service> dataStructure;
public WebRockModel()
{
System.out.println("WebRockModel Constructor in action");
this.dataStructure=new HashMap<>();
}
}
