# Send TO Client
I will update later on...

For self refference So, Aakash if service has non-void return type then whatever the service returned it will send to requested client.
here list will be send to client, yes I checked that if non-primitive is return then it also send to client using GSON and if primitve then it also send to client
here in our ex: list will return to client  

ex:

```java
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
```