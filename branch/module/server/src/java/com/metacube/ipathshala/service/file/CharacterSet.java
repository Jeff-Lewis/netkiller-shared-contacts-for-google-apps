package com.metacube.ipathshala.service.file;

public enum CharacterSet {
	UTF8, UTF16;
	
	@Override
	public String toString()	{
		String returnString = null;
		switch(this)	{
		case UTF8:
			returnString = "UTF8";
			break;
		case UTF16:
			returnString = "UTF16";
			
		}
		return returnString;
	}
	
	public static CharacterSet get(String charSetTypeString)	{
		for(CharacterSet characterSet:CharacterSet.values())	{
			if(characterSet!=null && characterSet.toString().equals(charSetTypeString))	{
				return characterSet;
			}
		}
		return null;
	}
}
