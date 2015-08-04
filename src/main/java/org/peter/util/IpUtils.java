package org.peter.util;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public abstract class IpUtils {

	public static boolean isIP(String ip) {
		try {
			String[] arr = ip.split("\\.");
			if (arr.length != 4) {
				return false;
			}
			for (String s : arr) {
				int i = Integer.parseInt(s);
				if (i < 0 || i > 255) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static int compareIp(String ip1, String ip2) {
		if ((!isIP(ip1)) || (!isIP(ip2))) {
			return -1;
		}
		String[] arr1 = ip1.split("\\.");
		String[] arr2 = ip2.split("\\.");
		for (int i = 0; i < arr1.length; i++) {
			int a1 = Integer.parseInt(arr1[i]);
			int a2 = Integer.parseInt(arr2[i]);
			if (a1 > a2) {
				return 2;
			} else if (a1 < a2) {
				return 0;
			}
		}
		return 1;
	}

	public static boolean validIpRange(String sourceIp, String startIp, String endIp) {
		if (IpUtils.compareIp(sourceIp, startIp) >= 1 && IpUtils.compareIp(endIp, sourceIp) >= 1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validIpForm(String sourceIp, String strIp) {
		if (!isIP(sourceIp)) {
			return false;
		}
		String[] arr_Source = sourceIp.split("\\.");
		String[] arr_str = strIp.split("\\.");
		for (int i = 0; i < arr_Source.length; i++) {
			if ((!arr_str[i].equals("*")) && (!arr_Source[i].equals(arr_str[i]))) {
				return false;
			}
		}
		return true;
	}

	public static boolean validIp(String sourceIP, String[] ips) {
		return validIp(sourceIP, Arrays.asList(ips));
	}

	public static boolean validIp(String sourceIP, Collection<String> ips) {
		if (!isIP(sourceIP)) {
			return false;
		}
		for (String ip : ips) {
			String[] arr_ip = ip.split("-");
			if (arr_ip.length == 2) {
				String StartIP = arr_ip[0].trim();
				String EndIP = arr_ip[1].trim();
				if (validIpRange(sourceIP, StartIP, EndIP)) {
					return true;
				}
			} else {
				String strIP = arr_ip[0].trim();
				if (validIpForm(sourceIP, strIP)) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getIp(HttpServletRequest request) {

		// return request.getRemoteAddr();
		return getRealIp(request);
	}

	public static long getLongIp(HttpServletRequest request) {

		return IPToNumber.ipToLong(IpUtils.getIp(request));
	}

	public static long getRealLongIp(HttpServletRequest request) {

		return IPToNumber.ipToLong(IpUtils.getRealIp(request));
	}

	public static long getLongIp(String ip) {

		return IPToNumber.ipToLong(ip);
	}

	public static String getRealIp(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0)
			ip = "127.127.127.127";
		return ip;

	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = getRealIp(request);
		if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
			String[] ipArray = StringUtils.split(ip, ",");
			ip = ipArray[0];
		}
		return ip;
	}
}
