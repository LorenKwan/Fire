<%--
  Created by IntelliJ IDEA.
  User: Bob Guan
  Date: 2015/12/11
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>欢迎你！！！</h1>

<form action="/uploadImg" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/> <input type="submit" value="Submit"/></form>
</form>
</body>
</html>