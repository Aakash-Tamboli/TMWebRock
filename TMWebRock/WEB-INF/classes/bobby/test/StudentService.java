package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import java.util.*;
import java.sql.*;


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

}


@Path("/deleteStudent")
public void delete(int rollNumber)
{

}

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
