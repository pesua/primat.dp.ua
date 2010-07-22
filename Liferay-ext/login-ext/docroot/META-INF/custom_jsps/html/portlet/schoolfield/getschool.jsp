<%@page import="java.util.List"%>
<%@page import="com.liferay.utility.CustomFieldUtil"%>
<%
		for (String item : CustomFieldUtil.getAvailValues("school-school", request.getParameter("q"))) {
      out.println(item);
    }
%>