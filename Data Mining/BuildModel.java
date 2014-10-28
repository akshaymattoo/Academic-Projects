import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class BuildModel {
	
	public static Set<String> getTotaltags(Map<Integer,String> mp){
		
		Set<String>  setOfTags = new HashSet<String>();
		
		for(Entry<Integer,String> entry:mp.entrySet()) 
		 {
			 
				String arr[] = entry.getValue().split(",");
				String termArray[] =arr[1].split(" ");
				for(String tr:termArray){
					if(!tr.equals(""))
						setOfTags.add(tr.trim());
				}
		 }
		return setOfTags;
	}
	
	
	public static void probabilityMap(Map<String,Double> finalMap,int totalWords,String tag,Map<Integer,String> tagDocMap)
	{
		Map<String,Integer> termCountMap = null;
		int totalTerms=0;
		int numerator=0;
		int denominator=0;
		double probability=1;
		//System.out.println(tagDocMap.toString());
		totalTerms = getTotalTerms(tagDocMap);
		termCountMap=getIndividualTotal(tagDocMap);
		for(Entry<String,Integer> entry:termCountMap.entrySet())
		{
			 
			//System.out.println(entry.getKey()+"----"+entry.getValue());
			numerator=entry.getValue()+1;
			denominator = totalTerms+totalWords;
			probability = Math.log10(((double)numerator/(double)denominator));
			
			//probability =Math.round(probability * 100.0) / 100.0;
			
			finalMap.put(entry.getKey()+","+tag, probability) ;
			
		}
	}
	
	
	public static Map<String,Integer> getIndividualTotal(Map<Integer,String> tagDocMap)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		int count=0;
		String tempValue="";
		String term="";
		try{
			for(Entry<Integer,String> entry:tagDocMap.entrySet())
			{
				String arr[] = entry.getValue().split(" ");
				for(String st:arr)
				{
				 //String str[] = st.split(":");
					String str[] = st.split("::::");
				 term=str[0];
				 if(map.containsKey(term))
				 {
					 //count=map.get(term)+Integer.parseInt(str[1]);
					 count=map.get(term)+parseWithDefault(str[1],0);
					 map.put(term,count);
				 }
				 else
				 {
					 count  =parseWithDefault(str[1],0);
					 //count  =Integer.parseInt(str[1]);
				 }
				 
				 map.put(term,count);
				 tempValue =term;
				}
			}
		}catch(Exception e)
		{
			map.put(term,count);
			 tempValue =term;
		}
		return map;
	}
	
	
	public static int getTotalTerms(Map<Integer,String> tagDocMap)
	{
		int totalTerms=0;
		try{
			
			for(Entry<Integer,String> entry:tagDocMap.entrySet())
			{
				if((entry.getValue()!=null) ||(!entry.getValue().equals(""))){
					String arr[]=entry.getValue().split(" ");
					for(String tempValue:arr)
					{
						String arr1[]=tempValue.split("::::");
						//String arr1[]=tempValue.split(":");
						//totalTerms+=Integer.parseInt(arr1[1]);
						totalTerms+=parseWithDefault(arr1[1], 0);
					}
				}
			}
		}catch(Exception e)
		{
			e.getMessage();
			totalTerms+=0;
		}
		return totalTerms;
	}
	
	
	public static int parseWithDefault(String number, int defaultVal) {
		  try {
		    return Integer.parseInt(number);
		  } catch (Exception e) {
		    return defaultVal;
		  }
		}
	
	
	
	public static int getTotalWords(Map<Integer,String> trainMap)
	{
		int totalTerms=0;
		Set<String> setOfWords =new HashSet<String>();
		for(Entry<Integer,String> entry:trainMap.entrySet())
		{
			String arr[]=entry.getValue().split(",");
			String arr1[] = arr[0].split(" ");
			for(String term:arr1)
			{
				if(!term.equals(""))
					setOfWords.add(term.trim());
			}
		}
		
		totalTerms= setOfWords.size();
		return totalTerms;
	}
	
	
	
	
	public static Map<Integer,String> getKey(String value, Map<Integer, String> map,Map<String,Integer> tagTermCount) {
		  
		  List<Integer> keys = new ArrayList<Integer>();
		  Map<Integer,String> freqMap = new HashMap<Integer,String>();
		  CountWords cw = new CountWords();
		  
		  
		  for(Entry<Integer, String> entry:map.entrySet()) {
			  String str=entry.getValue();
			  String arr[] = str.split(",");
			  String titleTags[] = arr[1].split(" ");
			  for(String s: titleTags){
				  if(s.equals(value))
				  {
					  if(!entry.getKey().equals(""))
						  keys.add(entry.getKey());
					  break;
				  }
			 }
		  }
		  
		  for(Integer key:keys){
			  String arr[]=map.get(key).toString().split(",");
			  
			  freqMap.put(key,cw.returnFrequency(arr[0]));
		  }
		  keys.clear();
		  keys=null;
		  tagTermCount.put(value, getTotalTerms(freqMap)); 
		  return freqMap;
	}
	
}
