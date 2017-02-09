package com.bstek.bdf3.export.utils;

import java.awt.Color;

public class ColorUtils {

	public static int[] parse2RGB(String c) {
		Color convertedColor = Color.ORANGE;
		if (c.startsWith("#")) {
			c = c.substring(1);
		} else if (c.startsWith("0x")) {
			c = c.substring(2);
		}
		try {
			convertedColor = new Color(Integer.parseInt(c, 16));
		} catch (NumberFormatException e) {
		}
		int[] rgb = new int[] { convertedColor.getRed(), convertedColor.getGreen(), convertedColor.getBlue() };
		return rgb;
	}

}
