<%@page import="java.util.List"%>
<%@page import="com.liferay.utility.CustomFieldUtil"%>
<%
		for (String item : CustomFieldUtil.getAvailValues("school-country", request.getParameter("q"))) {
      out.println(item);
    }
%>