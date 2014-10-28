import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class DataMine {
	static Map<String,Double> finalMap = new HashMap<String,Double>();
	static int totalWords=0; 
	static Map<Integer,String> trainingMap = null;
	static Map<String,Integer> tagTermCount =new HashMap<String,Integer>();
	 
	public static void main(String[] args) {
		try
		{
			System.out.println(new Timestamp(new java.util.Date().getTime()));
			BuildFiles bf = new BuildFiles();
			CountWords cw = new CountWords();
			int totalRows=0;
			
			Map<String,Integer> tagCount =null;
			//Map<String,Double> termTagMap =null;
			Map<Integer,String> testMap = null;
			//Map<String,Integer> testTileMap = null;
			List<String> tagList= new ArrayList<String>();
			Set<String> tagSet= new HashSet<String>();
			
			//trainingMap=bf.createTrainingMap("E:\\FALL 2013\\Chengkai 5334\\Project1\\rar\\Train\\Train4.csv",tagList);
			trainingMap=bf.createTrainingMap(args[0],tagList);
			System.out.println(trainingMap.size());
			System.out.println(new Timestamp(new java.util.Date().getTime()));
			
			totalRows = trainingMap.size();
			tagSet  =BuildModel.getTotaltags(trainingMap);
			totalWords = BuildModel.getTotalWords(trainingMap);
			
			System.out.println("Total Rows : "+totalRows+" Total Words : "+totalWords);
			
			for(String str:tagSet)
			{
				BuildModel.probabilityMap(finalMap,totalWords,str,BuildModel.getKey(str,trainingMap,tagTermCount));
			}
		
			System.out.println("Final size : "+finalMap.size());
			PrintWriter out =new PrintWriter("e:\\tags.txt");
			out.print(finalMap.toString());
			out.close();
			
			System.out.println(new Timestamp(new java.util.Date().getTime()));
			
			//testMap=bf.createTestMap("E:\\FALL 2013\\Chengkai 5334\\Project1\\rar\\Test\\Test4.csv");
			testMap=bf.createTestMap(args[1]);
			
			System.out.println("Test size : "+testMap.size());
			System.out.println(new Timestamp(new java.util.Date().getTime()));
			//testTileMap= cw.returnFrequency(testTitleList);
			
			FileWriteOutput.createHeader();
			System.out.println("Before output");
			tagCount =cw.returnFrequency(tagList);
			getOutputFile(testMap,tagSet,finalMap,tagCount,totalRows);
			System.out.println("After output");
			FileWriteOutput.closeReader();
			System.out.println(new Timestamp(new java.util.Date().getTime()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			finalMap=null;
			trainingMap=null;
			tagTermCount =null;
		}
	}
	
	
	public static void getOutputFile(Map<Integer,String> documents,Set<String> tags,Map<String,Double> ttMap,Map<String,Integer> tagCount,int totalRows)
	{
		String finalTags="";
		String termTag=""; 
		int documentId=0;
		double termProbability=0;
		double documentProbability=0;
		Map<String,Double> finalMap = new HashMap<String,Double>();
		Map<String,Integer> termListMap =null;
		for(Entry<Integer,String> entry:documents.entrySet()) 
		 {
			
			documentId=entry.getKey();
			
			Set<String> termSet = BuildFiles.StringToSet(entry.getValue());
			/*
			 * Map of count of each word
			 */
			termListMap = BuildFiles.returnFrequency(entry.getValue());
			
			for(String tag:tags)
			{
				for(String term:termSet)
				{
					termTag = term+","+tag;
					if(ttMap.containsKey(termTag))
					{
						//termProbability=ttMap.get(termTag);
						//System.out.println("term: "+term+"---"+termListMap.get(term));
						termProbability+=Math.pow(ttMap.get(termTag), termListMap.get(term));
					}
					
					else{
						//int tTerms =BuildModel.getTotalTerms(BuildModel.getKey(tag, trainingMap));
						int tTerms = tagTermCount.get(tag);
						int denominator = tTerms+totalWords;
						//termProbability *=Math.round(((double)1/(double)denominator) * 100.0) / 100.0;
						
						termProbability +=Math.log10(((double)1/(double)denominator));
					}
				}
				
				documentProbability = Math.log10(((double)tagCount.get(tag)/(double)totalRows));
				documentProbability+=(documentProbability+termProbability);
				finalMap.put(documentId+","+tag, documentProbability);
				termProbability=0;
				documentProbability=0;
			}
			
			finalTags = BuildFiles.getTags(finalMap);
			
			//System.out.println(documentId +" "+finalTags );
			FileWriteOutput.printRows(documentId, finalTags);
			/*
			 * Here call the Build File function to print the output file
			 */
			finalMap.clear();
			
		 }
	}
	
}
