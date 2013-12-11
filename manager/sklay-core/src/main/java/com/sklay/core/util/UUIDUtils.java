package com.sklay.core.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 生成固定长度的ID
 * 
 * @author Sun
 * 
 */
public abstract class UUIDUtils {

	/**
	 * 随机种子
	 */
	private static char[] x36s = "0123456789abcdefghijklmnopqrstuvwxyz"
			.toCharArray();

	private static char[] x62s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private static char[] NUMS = "0123456789".toCharArray();

	public static String random(int len) {
		return random(x36s, len);
	}

	public static String randomNum(int len) {
		return random(NUMS, len);
	}

	public static String random(char[] seeds, int len) {
		char chs[] = new char[len];

		int split = len / 2;

		UUID u = UUID.randomUUID();

		long v = Math.abs(u.getMostSignificantBits());
		for (int i = 0; i < split; i++) {
			chs[i] = seeds[(int) (v % seeds.length)];
			v = v / seeds.length;
		}

		v = Math.abs(u.getLeastSignificantBits());
		for (int i = split; i < len; i++) {
			chs[i] = seeds[(int) (v % seeds.length)];
			v = v / seeds.length;
		}

		return new String(chs);
	}

	public static String randomUUID() {

		UUID uuid = UUID.randomUUID();

		return uuid.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(randomNum(16));
		}

		System.out.println();

		for (int i = 0; i < 100; i++) {
			Set<String> set = new HashSet<String>();

			for (long c = 0; c < 100000000L; c++) {
				String value = randomNum(16);
				if (set.contains(value)) {
					System.out.println("Duplicate value : " + value + " [" + c
							+ "]");
					break;
				} else {
					set.add(value);
				}
			}
			System.out.println("[" + i + "] pass.");
			set.clear();
		}
	}
}
