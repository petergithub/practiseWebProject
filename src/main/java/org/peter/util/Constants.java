package org.peter.util;


public class Constants {
	
	/**
	 * 默认的每页表格显示条目数，也可由页面自己传过来
	 */
	public static final int DefaultPerPageItemsCount = 20;
	
	public static final String LanguageCode_zh = "zh";
	public static final String LanguageCode_en = "en";
	public static final String OrderDesc = "desc";
	public static final String OrderAsc = "asc";
	public static final int PageNoDefault = 1;
	public static final int PageSizeDefault = 20;
	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	//response code and msg
	public static final String ResponseCode_Success = "0";
	public static final String ResponseMsg_Success = "Success";
	public static final String ResponseCode_UnknownError = "101";
	public static final String ResponseMsg_UnknownError = "An unknown error has occurred";
	public static final String ResponseCode_InvalidParameter = "102";
	public static final String ResponseMsg_InvalidParameter = "Parameter value is invalid";
	public static final String ResponseCode_InvalidParameter_MerchantId = "106";
	public static final String ResponseMsg_InvalidParameter_MerchantId = "Invalid Merchant id";
	public static final String ResponseCode_InvalidParameter_ChannelId = "107";
	public static final String ResponseMsg_InvalidParameter_ChannelId = "Invalid Channel id";
	
}
