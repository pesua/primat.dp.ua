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

<%@ include file="/html/taglib/ui/captcha/init.jsp" %>

<%
String url = (String)request.getAttribute("liferay-ui:captcha:url");

boolean captchaEnabled = false;

if (portletRequest != null) {
	captchaEnabled = CaptchaUtil.isEnabled(portletRequest);
}
else {
	captchaEnabled = CaptchaUtil.isEnabled(request);
}
%>

<c:if test="<%= captchaEnabled %>">
	<div class="taglib-captcha">
		<table class="lfr-table">
		<tr>
			<td valign="top">
        <img alt="captcha" class="captcha" src="<%= url %>" />
			</td>
			<td valign="top">
        <strong><liferay-ui:message key="text-verification" /></strong><br/>
        <input name="<%= namespace %>captchaText" size="10" type="text" value="" />
			</td>
		</tr>
		</table>
	</div>
</c:if>