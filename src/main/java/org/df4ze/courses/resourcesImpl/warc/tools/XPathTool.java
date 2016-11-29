package org.df4ze.courses.resourcesImpl.warc.tools;

import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XPathTool 
{
	public XPathTool()
	{
		
	}
	
	public Hashtable<String, String> getJSoupSelector(String XPath)
	{
		String cssSelector = "";
		String attributeSelector = "";
		
		String[] parts = XPath.split("/");
		String nodeName;
		
		Hashtable<String, String> hashSelector = new Hashtable<String, String>(); 
		
		boolean allDescendant = false;
				
		for(String part : parts)
		{
			if (part.length() == 0)
			{
				allDescendant = true;
				continue;
			}
				
			int bracketLeftPos = part.indexOf("[");
			
			if (cssSelector.length() > 0 && part.indexOf("@") != 0 && part.length() > 0)
				cssSelector += !allDescendant ? " > " : " ";
			
			String cssSelectorPart = "";
			
			if (bracketLeftPos != -1)
			{
				nodeName = part.substring(0, bracketLeftPos);
				
				int bracketRightPos = part.indexOf("]", bracketLeftPos);
				
				if (bracketRightPos != -1)
				{
					String value = part.substring(bracketLeftPos+1, bracketRightPos).trim();
					
					if (FieldTool.isInteger(value))
					{
						int number = Integer.parseInt(value);
						
						cssSelectorPart += nodeName + ":eq(" + ((Integer)(number)).toString()  + ")";
					}
					else if (value.indexOf("@") == 0)
					{
						String attribute = value.substring(1);
						String[] attributeParts = attribute.split("=");
						
						cssSelectorPart += nodeName;
						
						if (attributeParts.length > 1)
						{
							String attributeName = attributeParts[0];
							String attributeValue = attributeParts[1];
							
							attributeValue = attributeValue.substring(1, attributeValue.length() - 1);
							
							if (attributeName.compareToIgnoreCase("class") == 0)
							{
								cssSelectorPart += ".";
								cssSelectorPart += attributeValue;
							}
							else if (attributeName.compareToIgnoreCase("id") == 0)
							{
								cssSelectorPart += "#";
								cssSelectorPart += attributeValue;
							}
							else 
							{
								cssSelectorPart += "[" + attributeName + "=" + attributeValue + "]";
							}
						}
					}
				}
			}
			else
			{
				if (part.indexOf("@") == 0)
				{
					attributeSelector = part.substring(1); 
				}
				else
					cssSelectorPart = part;
			}
			
			cssSelector += cssSelectorPart;
			
			allDescendant = false;
		}
		
		hashSelector.put("selector", cssSelector);
		
		if (attributeSelector != null && attributeSelector.length() > 0)
			hashSelector.put("attribute", attributeSelector);
		
		return hashSelector;
	}
	
	public Hashtable<String, Object> getElementSelection(Document doc, String XPath)
	{
		//String cssSelector = "";
		String attributeSelector = "";
		//Element element = null;
		
		String[] parts = XPath.split("/");
		String nodeName;
		
		Hashtable<String, Object> hashSelector = new Hashtable<String, Object>(); 
		
		boolean allDescendant = false;
				
		Element currentElement = doc;
		
		for(String part : parts)
		{
			if (part.length() == 0)
			{
				allDescendant = true;
				continue;
			}
				
			int bracketLeftPos = part.indexOf("[");
			int numberIndex = 0;
			
			String cssSelectorPart = "";
			
			if (part.indexOf("@") != 0 && part.length() > 0)
				cssSelectorPart += !allDescendant ? "> " : " ";
			
			if (bracketLeftPos == -1)
			{
				if (part.indexOf("@") == 0)
				{
					attributeSelector = part.substring(1); 
				}
				else
				{
					if (part.compareToIgnoreCase("<none>") == 0)
						cssSelectorPart = part;
					else
						cssSelectorPart += part;
				}
			}
			else
			{
				nodeName = part.substring(0, bracketLeftPos);
				
				int bracketRightPos = part.indexOf("]", bracketLeftPos);
				
				if (bracketRightPos != -1)
				{
					String value = part.substring(bracketLeftPos+1, bracketRightPos).trim();
					
					if (FieldTool.isInteger(value))
					{
						numberIndex = Integer.parseInt(value);
						
						cssSelectorPart += nodeName;
					}
					else if (value.indexOf("@") == 0)
					{
						String attribute = value.substring(1);
						String[] attributeParts = attribute.split("=");
						
						cssSelectorPart += nodeName;
						
						if (attributeParts.length > 1)
						{
							String attributeName = attributeParts[0];
							String attributeValue = attributeParts[1];
							
							attributeValue = attributeValue.substring(1, attributeValue.length() - 1);
							
							if (attributeName.compareToIgnoreCase("class") == 0)
							{
								cssSelectorPart += ".";
								cssSelectorPart += attributeValue;
							}
							else if (attributeName.compareToIgnoreCase("id") == 0)
							{
								cssSelectorPart += "#";
								cssSelectorPart += attributeValue;
							}
							else 
							{
								cssSelectorPart += "[" + attributeName + "=" + attributeValue + "]";
							}
						}
					}
				}
				
				bracketLeftPos = part.indexOf("[", bracketRightPos);
				
				if (bracketLeftPos != -1)
				{
					bracketRightPos = part.indexOf("]", bracketLeftPos);
					
					if (bracketRightPos != -1)
					{
						String value = part.substring(bracketLeftPos+1, bracketRightPos).trim();
						
						if (FieldTool.isInteger(value))
						{
							numberIndex = Integer.parseInt(value);
						}
					}
				}
			}
			
						
			if (cssSelectorPart != null && cssSelectorPart.length() > 0)
			{
				Elements elements = null;
				
				if (cssSelectorPart.compareToIgnoreCase("<none>") != 0)
					elements = currentElement.select(cssSelectorPart);
				
				if (elements != null)
				{
					if (numberIndex >= 0 && elements.size() > numberIndex)
					{
						currentElement = elements.get(numberIndex);
					}
					else if (numberIndex < 0 && elements.size() >= -numberIndex)
					{
						currentElement = elements.get(elements.size() + numberIndex);
					}
					else
					{
						currentElement = null;
						break;
					}
				}
				else
				{
					currentElement = null;
					break;
				}
			}
			
			allDescendant = false;
		}
		

		if (currentElement != null)
			hashSelector.put("element", currentElement);
		
		if (attributeSelector != null && attributeSelector.length() > 0)
			hashSelector.put("attribute", attributeSelector);
		
		return hashSelector;
	}
	
	public Elements getElements(Document document, String XPath)
	{
		//String selector = "";
		//Element element = null;
		
		String[] parts = XPath.split("/");
		String nodeName;
		
		//Hashtable<String, Object> hashSelector = new Hashtable<String, Object>(); 
		
		boolean allDescendant = false;
				
		Elements currentElements = new Elements();
		currentElements.add(document);
		
		for(String part : parts)
		{
			if (part.length() == 0)
			{
				allDescendant = true;
				continue;
			}
				
			int bracketLeftPos = part.indexOf("[");
			Integer numberIndex = null;
			
			String cssSelectorPart = "";
			
			if (part.indexOf("@") != 0 && part.length() > 0)
				cssSelectorPart += !allDescendant ? "> " : "";
			
			if (bracketLeftPos == -1)
			{
				cssSelectorPart = part;
			}
			else
			{
				nodeName = part.substring(0, bracketLeftPos);
				
				int bracketRightPos = part.indexOf("]", bracketLeftPos);
				
				if (bracketRightPos != -1)
				{
					String value = part.substring(bracketLeftPos+1, bracketRightPos).trim();
					
					if (FieldTool.isInteger(value))
					{
						numberIndex = Integer.parseInt(value);
						
						cssSelectorPart += nodeName;
					}
					else if (value.indexOf("@") == 0)
					{
						String attribute = value.substring(1);
						String[] attributeParts = attribute.split("=");
						
						cssSelectorPart += nodeName;
						
						if (attributeParts.length > 1)
						{
							String attributeName = attributeParts[0];
							String attributeValue = attributeParts[1];
							
							attributeValue = attributeValue.substring(1, attributeValue.length() - 1);
							
							if (attributeName.compareToIgnoreCase("class") == 0)
							{
								cssSelectorPart += ".";
								cssSelectorPart += attributeValue;
							}
							else if (attributeName.compareToIgnoreCase("id") == 0)
							{
								cssSelectorPart += "#";
								cssSelectorPart += attributeValue;
							}
							else 
							{
								cssSelectorPart += "[" + attributeName + "=" + attributeValue + "]";
							}
						}
					}
				}
				
				bracketLeftPos = part.indexOf("[", bracketRightPos);
				
				if (bracketLeftPos != -1)
				{
					bracketRightPos = part.indexOf("]", bracketLeftPos);
					
					if (bracketRightPos != -1)
					{
						String value = part.substring(bracketLeftPos+1, bracketRightPos).trim();
						
						if (FieldTool.isInteger(value))
						{
							numberIndex = Integer.parseInt(value);
						}
					}
				}
			}
			
			if (cssSelectorPart != null && cssSelectorPart.length() > 0)
			{
				Elements newElements = new Elements();
			
				for (int index = 0; index < currentElements.size(); index++)
				{
					Element currentElement = currentElements.get(index);
				
					Elements elements = currentElement.select(cssSelectorPart);
				
					if (elements != null)
					{
						if (numberIndex != null && numberIndex >= 0)
						{
							if (elements.size() > numberIndex)
							{
								Element indexElement = elements.get(numberIndex);
								
								elements.clear();
								elements.add(indexElement);
							}
							else
							{
								elements.clear();
							}
						}
						else if (numberIndex != null && numberIndex < 0 && elements.size() >= -numberIndex)
						{
							Element indexElement = elements.get(elements.size() + numberIndex);
							
							elements.clear();
							elements.add(indexElement);
						}
						else if (numberIndex == null)
						{
							// do nothing
						}
						
						for(int k = 0; k < elements.size(); k++)
						{
							if (elements.get(k) != currentElement)
								newElements.add(elements.get(k));
						}
					}
					else
					{
					}
				}
				
				currentElements = newElements;
			}
			
			allDescendant = false;
		}
		
		return currentElements;
	}
}
