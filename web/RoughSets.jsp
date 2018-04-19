<%-- 
    Document   : RoughSets
    Created on : Apr 17, 2018, 8:46:50 AM
    Author     : David Klecker
--%>

<%@page import="java.util.List"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import = "Project4.RoughSetsCls" %>
<%@ page import = "Project4.TwoPolynomialEquation" %>
<%@ page import = "Project4.DatabaseCls" %>
<%@ page import = "Project4.LListsCls" %>
<jsp:useBean id = "RoughSets" class = "Project4.RoughSetsCls" scope = "session" ></jsp:useBean>
<jsp:setProperty name = "RoughSets" property = "*" /> 

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            RoughSets.ProcessRequest(request);
        %>
        <h1> Condensed Database </h1>
        <table>
            <%
                for (int i = 0; i < RoughSets.Data.size(); i++) {
                    DatabaseCls db = RoughSets.Data.get(i);
                    List<String> attributes = db.getAttributes();
                    List<Integer> ARecords = db.getARecords();
            %>
            <tr><td>z<%=i%><td><%=ARecords.toString()%></td>
                <%  for (int j = 0; j < attributes.size(); j++) {
                %>
                <td><%=attributes.get(j)%></td>
                <%
                    }
                %>
            </tr>
            <%}%>
        </table>
        <h1> L Lists </h1>
        <table>
            <%
                for (int i = 0; i < RoughSets.LList.size(); i++) {
                    LListsCls L = RoughSets.LList.get(i);
                    List<DatabaseCls> dbList = L.getDataDB();
            %>
            <tr><td>L<%=i + 1%></td><td>{</td>
                    <%
                        for (int j = 0; j < dbList.size(); j++) {
                    %>
                <td>Z<%=dbList.get(j).getRecordNumber()%>, </td>
                <%
                    }
                %>
                <td>}</td></tr>
            <%}%>
        </table>

        <h1> R Lists </h1>
        <table>
            <%
                for (int i = 0; i < RoughSets.RList.size(); i++) {
                    LListsCls R = RoughSets.RList.get(i);
                    List<DatabaseCls> dbList = R.getDataDB();
            %>
            <tr><td>R<%=i + 1%></td><td>{</td>
                    <%
                        for (int j = 0; j < dbList.size(); j++) {
                    %>
                <td>Z<%=dbList.get(j).getRecordNumber()%>, </td>
                <%
                    }
                %>
                <td>}</td></tr>
            <%}%>
        </table>    
        
        <h1> Lower Lists </h1>
        <table>
            <%
                for (int i = 0; i < RoughSets.LowerLists.size(); i++) {
                    LListsCls L = RoughSets.LowerLists.get(i);
                    List<DatabaseCls> dbList = L.getDataDB();
            %>
            <tr><td>Lower L<%=i + 1%></td><td>{</td>
                    <%
                        for (int j = 0; j < dbList.size(); j++) {
                    %>
                <td>Z<%=dbList.get(j).getRecordNumber()%>, </td>
                <%
                    }
                %>
                <td>}</td></tr>
            <%}%>
        </table>

        <h1> Local Certain Rules </h1>
        <table>
            <%
                for (int i = 0; i < RoughSets.LocalCertainRules.size(); i++) {
                    String str = RoughSets.LocalCertainRules.get(i);
            %>
            <tr><td><%=str%></td></tr>
            <%}%>
        </table>
        
    </body>
</html>
