<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.security.permission.ResourceActionsUtil" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoBridge" %>
<%@ page import="com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil" %>
<%@ page import="java.util.*" %>

<%
String className = (String)request.getAttribute("liferay-ui:custom-attribute-list:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:custom-attribute-list:classPK"));
boolean editable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute-list:editable"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute-list:label"));

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), className, classPK);

String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, className);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

for (String attributeName : attributeNames) {
%>

	<span class="aui-field">
		<span class="aui-field-content">
			<liferay-ui:custom-attribute
				className="<%= className %>"
				classPK="<%= classPK %>"
				editable="<%= editable %>"
				label="<%= label %>"
				name="<%= attributeName %>"
			/>
		</span>
	</span>

<%
}
%>

<c:if test="<%= attributeNames.isEmpty() %>">
	<span class="aui-field">
		<span class="aui-field-content">
			<label><%= LanguageUtil.format(pageContext, "no-custom-fields-are-defined-for-x", modelResourceName) %></label>
		</span>
	</span>
</c:if>