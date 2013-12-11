package com.sklay.core.util;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class PwdUtils {
	public static String MD256Pws(String pwd) {
		Sha256Hash hash = new Sha256Hash(pwd, null, 1024);

		return hash.toBase64();
	}
}
