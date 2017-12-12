package com.zhou.coin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Created by zhou on 2017/7/3.
 */

public class Utils {
	// 生成sign时，参与加密的参数按照a-z排序，签名MD5必须是小写字母。
	// 签名时的字符一律采用UTF-8编码。MD5散列后每个byte采用两个16进制小写字母高位在前低位在后表示。
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes("utf-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用户自定义订单号为数字(最多15位，唯一值) 此项不参与sign签名过程
	 * 
	 * @return
	 */
	public static String getTradeId() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-HH-mm-ss-SSS");
		return "37" + sdf.format(System.currentTimeMillis()).replaceAll("-", "");
	}

	public static double getFormatDoubleValue(double d,int number) {
		BigDecimal bg = new BigDecimal(d);
		return bg.setScale(number, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String getProperty(String key) {
		try {
			InputStream inStream = new FileInputStream(new File("config.properties"));
			Properties prop = new Properties();
			prop.load(inStream);
			return prop.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
