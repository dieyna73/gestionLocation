<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h1>Test de chemin</h1>
    <p>ContextPath: ${pageContext.request.contextPath}</p>
    <p>RealPath of /: <%= application.getRealPath("/") %></p>
    <p>RealPath of login.jsp: <%= application.getRealPath("/WEB-INF/views/login.jsp") %></p>
</body>
</html>
