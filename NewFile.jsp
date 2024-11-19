<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sum of Two Numbers</title>
</head>
<body>
    <h2>Enter Two Numbers to Calculate Their Sum</h2>
    
    <!-- Form to take two numbers as input -->
    <form action="" method="post">
        <label for="num1">Enter 1st Number:</label>
        <input type="text" name="num1" id="num1"><br><br>
        
        <label for="num2">Enter 2nd Number:</label>
        <input type="text" name="num2" id="num2"><br><br>
        
        <input type="submit" value="Calculate Sum">
    </form>
    
    <hr>
    
    <%-- Check if the form has been submitted and calculate the sum --%>
    <%
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        
        if (num1Str != null && num2Str != null && !num1Str.isEmpty() && !num2Str.isEmpty()) {
            try {
                // Parse the input values as integers
                int num1 = Integer.parseInt(num1Str);
                int num2 = Integer.parseInt(num2Str);
                int sum = num1 + num2;
    %>
            <h3>Sum: <%= sum %></h3>
    <%
            } catch (NumberFormatException e) {
    %>
            <p style="color:red;">Please enter valid numbers.</p>
    <%
            }
        }
    %>
</body>
</html>
