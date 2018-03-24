package com.kaisheng;

import org.apache.commons.codec.digest.DigestUtils;

import com.kaisheng.util.Config;

public class Test {

	public static void main(String[] args) {
		String a = "123123";
		String sqlPassword = DigestUtils.md5Hex(a + Config.get("user.password.salt"));
		System.out.println(sqlPassword);
		System.out.println('a' + 4);
		
		long b = 7;
		System.out.println(b % 2);
	}
	public static String exR1(int n){
		if (n <= 0) {
		return "";
		}
		return exR1(n-3) + n + exR1(n-2) + n;
		}
}
