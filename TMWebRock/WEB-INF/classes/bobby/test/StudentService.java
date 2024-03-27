package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import java.util.*;
import java.sql.*;

@SendPOJOServiceToClient
@Path("/studentService")
public class StudentService
{


@Path("/addStudent")
public void add(Student student)
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("insert into Student (name,gender) values(?,?)");
preparedStatement.setString(1,student.getName());
preparedStatement.setString(2,student.getGender());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("Bobby Service Class");
System.out.println(exception);
}
}


@Path("/updateStudent")
public void update(Student student)
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");
PreparedStatement preparedStatement=connection.prepareStatement("select * from Student where rollNumber=?");
preparedStatement.setInt(1,student.getRollNumber());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
System.out.println("RollNumber not Found");
preparedStatement.close();
connection.close();
return;
}
preparedStatement=connection.prepareStatement("update Student set name=?,gender=? where rollNumber=?");
preparedStatement.setString(1,student.getName());
preparedStatement.setString(2,student.getGender().trim());
preparedStatement.setInt(3,student.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("Bobby Side");
System.out.println(exception);
}
}


@GET
@Path("/deleteStudent")
public void delete(@RequestParameter("rollNumber") int rollNumber)
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");
// Ideally I have to check existenece of rollNumber
PreparedStatement preparedStatement=connection.prepareStatement("delete from Student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("Bobby Side");
System.out.println(exception);
}
}

@GET
@Path("/getByRollNumber")
public Student getByRollNumber(int rollNumber)
{
return new Student();
}

@GET
@Path("/getAllStudent")
public List<Student> getAll()
{

List<Student> list=new ArrayList<>();
try
{

Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from Student");
Student student;
int rollNumber;
String name;
String gender;
while(resultSet.next())
{
rollNumber=resultSet.getInt("rollNumber");
name=resultSet.getString("name").trim();
gender=resultSet.getString("gender").trim();
list.add(new Student(rollNumber,name,gender));
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
System.out.println("List of Student ready and send back to clien");

return list;
}
}
