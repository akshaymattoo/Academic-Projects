import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CountWords {

	
	public String returnFrequency(String str)
	{
		List<String> stringLst = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		Map<String,Integer> mp = new HashMap<String,Integer>();
		String finalString="";
	 
		String arr[] = str.split(" ");
		
		for(String a:arr)
			stringLst.add(a.trim());
		
		set.addAll(stringLst);
		
		for(String s:set)
		{
			if(!s.equals("") && !s.equals(null))
			{
				//mp.put(s, Collections.frequency(stringLst, s));
				//finalString+=s+":"+Collections.frequency(stringLst, s)+" ";
				finalString+=s+"::::"+Collections.frequency(stringLst, s)+" ";
			}
		}
		
		return finalString.trim();
	}
	
	
	public Map<String,Integer> returnFrequency(List<String> lst)
	{
		List<String> stringLst = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		Map<String,Integer> mp = new HashMap<String,Integer>();
		
		for(String str:lst){
			String arr[] = str.split(" ");
			for(String a:arr)
			{
				if(!a.equals(""))
					stringLst.add(a.trim());
			}
		}
		
		set.addAll(stringLst);
		
		for(String s:set)
		{
			if(!s.equals("") && !s.equals(null))
			mp.put(s, Collections.frequency(stringLst, s));
		}
		
		return mp;
	}
	
	
	public Map<Integer,Map<String,Integer>> perDocumentFrequency(List<String> list)
	{
		int counter=1;
		String str="";
		
		Map<Integer,String> mp = new HashMap<Integer,String>();
		
		Map<Integer,Map<String,Integer>> finalMap = new HashMap<Integer,Map<String,Integer>>();
		List<String> tempList = new ArrayList<String>();
		for(String st:list)
		{
			mp.put(counter, st);
			counter++;
		}
		counter =1;
		Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        //System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        str=(String)pairs.getValue();
	        tempList.add(str.trim());
	        finalMap.put(counter,returnFrequency(tempList));
	        counter++;
	        tempList.clear();
	    }
	    
		return finalMap;
	}
	
	
	
}
