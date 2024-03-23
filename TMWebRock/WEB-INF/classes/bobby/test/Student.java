package bobby.test;

public class Student implements java.io.Serializable
{
private int rollNumber;
private String name;
private String gender;

public Student()
{
this.rollNumber=0;
this.name="";
this.gender="";
}
public Student(int rollNumber,String name,String gender)
{
this.rollNumber=rollNumber;
this.name=name;
this.gender=gender;
}
// setter starts
public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public void setGender(String gender)
{
this.gender=gender;
}
// setter ends
// getter starts

public int getRollNumber()
{
return this.rollNumber;
}

public String getName()
{
return this.name;
}

public String getGender()
{
return this.gender;
}
// getter ends
}
