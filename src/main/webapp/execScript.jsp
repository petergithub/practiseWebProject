<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.charset.Charset"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Deploy</title>
</head>
<body>
	<div>
		<ul>
			<li>
				<div>
					<a href="execScript.jsp?command=webshop" target="_self">webshop</a>
				</div>
			</li>
			<li>
				<div>
					<a href="execScript.jsp?command=webshopcms" target="_self">webshopcms</a>
				</div>
			</li>
			<li>
				<div>
					<a href="execScript.jsp?command=test" target="_self">test</a>
				</div>
			</li>
		</ul>
		<%
			String commandReloadWebShop = "/data/service/deployEStore.sh";
			String commandReloadWebShopCMS = "/data/service/deployWebshopcms.sh";
			String commandLocal = "D:\\sp\\work\\script\\testBat.bat";
			String command = request.getParameter("command");
			try {
				if ("webshop".equalsIgnoreCase(command)) {
					%>
						deploy webshop: <%=exeCmd(commandReloadWebShop) %>
					<%
				} else if ("webshopcms".equalsIgnoreCase(command)) {
					%>
						deploy webshopCMS: <%=exeCmd(commandReloadWebShopCMS) %>
					<%
				} else if ("test".equalsIgnoreCase(command)) {
					%>
						test bat: <%=exeCmd(commandLocal) %>
					<%
				}
			} catch (Exception e) {
				e.printStackTrace();
				%>
					Exception:<%=e%>
				<%
			}
		%>
	</div>
</body>
</html>

<%!
public String exeCmd(final String cmd) {
	Runtime runtime = Runtime.getRuntime();
	Process proc = null;
	String retStr = "";
	InputStreamReader insReader = null;
	char[] tmpBuffer = new char[1024];
	int nRet = 0;
	
	try {
		proc = runtime.exec(cmd);
		insReader = new InputStreamReader(proc.getInputStream(), Charset.forName("UTF-8"));
		
		while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1) {
			retStr += new String(tmpBuffer, 0, nRet);
		}
		
		insReader.close();
		retStr = HTMLEncode(retStr);
	} catch (Exception e) {
		retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";
	}
	return retStr;
}

public String HTMLEncode(String str) {
	str = str.replaceAll(" ", "&nbsp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\r\n", "<br>");
	
	return str;
}

%>
