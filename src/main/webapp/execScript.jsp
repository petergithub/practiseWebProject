<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Deploy</title>
</head>

<body>
	<div>
		<%
			String commandReloadWebShop = "/data/service/deployEStore.sh";
			String commandReloadWebShopCMS = "/data/service/deployWebshopcms.sh";
			String command = request.getParameter("command");
			try {
				if ("webshop".equalsIgnoreCase(command)) {
					Runtime.getRuntime().exec(commandReloadWebShop);
					%>
						deploy webshop successfully!
					<%
				} else if ("webshopcms".equalsIgnoreCase(command)) {
						Runtime.getRuntime().exec(commandReloadWebShopCMS);
			%>
				deploy webshopCMS successfully!
			<%
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception " + e);
				%>
					Exception!
				<%
			}
		%>
		<ul>
			<li>
				<div>
					<a href="reload.jsp?command=webshop" target="_self">webshop</a>
				</div>
			</li>
			<li>
				<div>
					<a href="reload.jsp?command=webshopcms" target="_self">webshopcms</a>
				</div>
			</li>
		</ul>
	</div>
</body>
</html>
