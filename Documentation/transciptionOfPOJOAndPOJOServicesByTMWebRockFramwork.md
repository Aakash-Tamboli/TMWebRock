# POJO and POJO Services
Consider You have following classes


Student.java: A POJO, which represting a Student Class
```java
public class Student
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

```
StudentService.java: A POJOService, which is kind of DAO classes which handles all the CRUD Operation with database.

## TMWebRock Offers
Now TMWebRock has capability to transctipt above classes into JS code, means you don't need to write this classes at front-end level, you just have to Demand which POJO Class or POJO Service class you want for Front-end(JS). but You have to follow the rules. and it will return for POJO for following ES6 Syntax class and for POJO Service it will return ES6 syntax class there all the method returns promise, so you have to do accordingly.

## Rules are:
1) framework user/ bobby Should Apply Appropriate Annotation on POJO Class or POJO Classes eg:
    For POJO: use `@SendPOJOToClient` Annotation at top of class
    For POJOService: use `@SendPOJOServiceToClient` Annotation at top of class
2) If Framework user does not applied  either GET or POST Annotation at all methods then all the methods will be available as POST type,


3) If Framework user does not applied path annotation at class level then transciption will be failed and JS not generated, similary if framework user does not applied path annotation at method level then that particular method will be ignored in JS File.

4) All the method should either have zero method like all the get related thing or only one parameter, 


## How to access Those Transcipted JS Data or JSFile
Inorder to access first you have to specify the name of js file in web.xml. it should be


```xml
<param-name>JSFILE</param-name>
<!-- <param-value>Here you have to put your jsFile name along with js extension</param-value> here -->
<param-value>abcd.js</param-value> here
```
then <br>
you have to demand in your html file like this: <br>

Here you have to be carefull because your src should be /TMWebRock/jsFile?name=your_file_name as example below


```javascript 
<script src='/TMWebRock/jsFile?name=abcd.js'></script>
```



## Testcase:
Student.java look like:
```java
package bobby.test;
import com.thinking.machines.webrock.annotations.*;


@SendPOJOToClient
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

```

StudentService.java
```java
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

```
index.html look like
```html
<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>TMWebRock GET POST Recieving Testing</title>
<script src='/jquery/jquery/jquery-3.7.1.min.js'></script>
<link rel="stylesheet" href="//cdn.datatables.net/2.0.3/css/dataTables.dataTables.min.css">
<script src="//cdn.datatables.net/2.0.3/js/dataTables.min.js"></script>
<script src='/TMWebRock/jsFile?name=abcd.js'></script> 
<script>
function getAllStudent()
{
let promise=new StudentService().getAll();
promise.then((data)=>{

$('#studentTable tbody').empty();

$.each(data, function(index, item) {
var row = '<tr>' +'<td>' + item.rollNumber + '</td>' +'<td>' + item.name + '</td>' +'<td>' + item.gender + '</td>' +'</tr>';
$('#studentTable tbody').append(row);
});

},(error)=>{
alert("fail");
alert(error);
});
}

function addStudent()
{
let name=$("#addName").val();
// alert(name);
let gender=$("input[name='addGender']:checked").val();
// alert(gender);
let student=new Student(0,name.trim(),gender.trim());
let promise=new StudentService().addStudent(student);
promise.then((done)=>{
alert("Student");
},(error)=>{
alert("error");
});
}

function updateStudent()
{
let rollNumber=$("#updateRollNumber").val();
let name=$("#updateName").val();
let gender=$("input[name='updateGender']:checked").val();
let student=new Student(rollNumber,name,gender);
let promise=new StudentService().update(student);
promise.then((data)=>{
alert('Student updated');
},(error)=>{
alert("Student not updated");
});
}

function deleteStudent()
{
let rollNumber=$("#deleteRollNumber").val();
alert(rollNumber);
let promise=new StudentService().delete(rollNumber);
promise.then((data)=>{
alert("Student Delete");
},(error)=>{
alert("Error");
});
}
</script>
</head>
<body>
<h1>Student Application</h1>
<h2>This is Demo appliation validation not applied here</h2>

<hr>
<table id="studentTable" class="display">
    <thead>
        <tr>
            <th>RollNumber</th>
            <th>Name</th>
            <th>Gender</th>
        </tr>
    </thead>
    <tbody>
        <tr>

        </tr>
    </tbody>
</table>
<button type='button' onclick='getAllStudent()'>GetAllStudents</button>
<hr>


<hr>
<h1>Student Add Module</h1>
name: &emsp; <input type='text' id='addName' > <br> <br>
gender: &emsp; 
<input type='radio' name='addGender' value='M' checked > Male &emsp;
<input type='radio' name='addGender' value='F'> Female <br>
<br>
<button type='button' onclick='addStudent()'>Next</button>
<hr>


<hr>
<h1>Student Update Module</h1>
RollNumber: &emsp; <input type='text' id='updateRollNumber'> <br> <br>
name: &emsp; <input type='text' id='updateName' > <br> <br>
gender: &emsp; 
<input type='radio' name='updateGender' value='M' checked > Male &emsp;
<input type='radio' name='updateGender' value='F'> Female <br>
<br>
<button onclick='updateStudent()'>Next</button>
<hr>

<hr>
<h1>Student Delete Module</h1>
RollNumber: &emsp; <input type='text' id='deleteRollNumber'> <br> <br>
<button onclick='deleteStudent()'>Next</button>
<hr>


</body>
</html>
```
