# Get Started
First you have to specify your package first name which should be reside in classes in web.xml

```xml
<servlet>
.
. Do not change anyting otherwise framework will not work
.
<init-param>
<param-name>SERVICE_PACKAGE_PREFIX</param-name>
<param-value>Here</param-value>
</init-param>
</servlet>
```
Example:
here under classes folder we have bobby then test then all those classes which boby wants to execute through our framework. so package be like <br>
bobby.test.Student <br>
bobby.test.Medical <br>
bobby.test.otherClasses<br>
So Give bobby against <br>
<b>SERVICE_PACKAGE_PREFIX</b>
in following manner
```xml
<servlet>
.
.
<init-param>
<param-name>SERVICE_PACKAGE_PREFIX</param-name>
<param-value>bobby</param-value>
</init-param>
</servlet>
```