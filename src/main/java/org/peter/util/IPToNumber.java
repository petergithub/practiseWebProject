package org.peter.util;

public class IPToNumber {

	public static long ipToLong(String strIp) {
		if (null == strIp || strIp.indexOf(".") == -1) {
			return 0;
		}
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	public static String longToIP(long longIp) {
		if (longIp == 0) {
			return "Hidden";
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}

	public static int bswap(int i) {
		int b0 = i & 0xff;
		int b1 = (i >> 8) & 0xff;
		int b2 = (i >> 16) & 0xff;
		int b3 = (i >> 24) & 0xff;
		return 0xff000000 & ((b0) << 24) | 0xff0000 & ((b1) << 16) | 0xff00 & ((b2) << 8) | 0xff & b3;
	}

	public static String getIP(int ip) {
		int _ip = bswap(ip);
		try {
			String r = "" + ((_ip >> 24) & 0xFF) + "." + ((_ip >> 16) & 0xFF) + "."
					+ ((_ip >> 8) & 0xFF) + "." + (_ip & 0xFF);
			return r;
		} catch (Exception ex) {
		}
		return "";
	}

}
