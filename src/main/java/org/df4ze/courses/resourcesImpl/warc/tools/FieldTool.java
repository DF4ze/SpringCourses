package org.df4ze.courses.resourcesImpl.warc.tools;


public class FieldTool
{
	public static boolean isInteger(String value)
	{
		boolean isInteger = true;
		try
		{
			Integer.parseInt(value);
		}
		catch(Exception e)
		{
			isInteger = false;
		}
		
		return isInteger;
	}
	
	public static boolean containsLetterOrDigit(String value)
	{
		for (int index = 0; index < value.length(); index++)
		{
			char chr = value.charAt(index);
			
			if ((chr >= '0' && chr <= '9') || (chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z'))
			{
				return true;
			}
		}
		
		return false;
	}
}
