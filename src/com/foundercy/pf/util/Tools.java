package com.foundercy.pf.util;

import java.awt.Color;

public class Tools {
	/**
	 * 将String类型转换成swing.Color类型
	 * @param paramValue 
	 */
	public static Color stringToColor(String paramValue)
	{
		try
		{
			int red;
			int green;
			int blue;
			if(paramValue.startsWith("#"))
			{
				red = (Integer.decode("0x" + paramValue.substring(1,3))).intValue();
				green = (Integer.decode("0x" + paramValue.substring(3,5))).intValue();
				blue = (Integer.decode("0x" + paramValue.substring(5,7))).intValue();
			}
			else
			{
				red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
				green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
				blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();
			}
			/* update by lixf 2006-12-16 end */
			if(red>255) red=255;
			if(red<0) red=0;
			if(green>255) green=255;
			if(green<0) green=0;
			if(blue>255) blue=255;
			if(blue<0) blue=0;
			return new Color(red,green,blue);
		}catch(Exception e)
		{
			
		}
		return new Color(0,0,0);
	}	
	
	
}
