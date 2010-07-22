<%@page import="java.util.List"%>
<%@page import="com.liferay.utility.CustomFieldUtil"%>
<%
		for (String item : CustomFieldUtil.getAvailValues("school-city", request.getParameter("q"))) {
      out.println(item);
    }
%>