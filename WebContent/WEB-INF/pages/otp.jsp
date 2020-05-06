<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<b><Center><h1>This OTP is One time Password And it will be valid for only 5 minutes!!!</h1></Center></b>
<form action="checkOTP">

<label>Enter Email_Id:-</label>
<input type="text" name="email" ><b>${error }</b>
<input type="text" name="otp"><b>${error1 }</b>
<button type="submit" class="btn btn-danger">Check OTP</button>
</form>
<b>${msg }</b>
</body>
</html>