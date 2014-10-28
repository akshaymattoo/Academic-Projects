import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class RemoveStopWords {
	
	
	public static String removeStopWords(String str) throws IOException 
    {
		String finalString="";
		String arr[]=str.split(" ");
		List<String> stopWords = getStopWords();
		boolean flag =false;
	    for(String string:arr) {
	        
			StringBuilder sb = new StringBuilder();
			StandardAnalyzer ana = new StandardAnalyzer(Version.LUCENE_45);
	        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_45, new StringReader(string));
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, ana.STOP_WORDS_SET);
	        
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, StopFilter.makeStopSet(Version.LUCENE_45, stopWords));
	        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
	        tokenStream.reset();
	        while (tokenStream.incrementToken()) 
	        {
	        	flag=true;
	           /* if (sb.length() > 0) 
	            {
	                sb.append(" ");
	            }*/
	            
	            if(token.length()>1 && !(token.toString().matches(".*\\d.*") && (!token.toString().equals(""))))
	            	sb.append(token.toString().trim());
	        }
	        if(tokenStream!=null)
	        	tokenStream.close();
	        if(flag == true)
	        finalString+=sb.toString()+" ";
	        flag=false;
		}   
		return finalString.trim();
    }
	
	public static Map<Integer, String> removeStopWords(Map<Integer, String> mp) throws IOException 
    {
		Map<Integer,String> finalMap = new HashMap<Integer,String>();
		Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
			String string=(String)pairs.getValue();
			StringBuilder sb = new StringBuilder();
			StandardAnalyzer ana = new StandardAnalyzer(Version.LUCENE_45);
	        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_45, new StringReader(string));
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, ana.STOP_WORDS_SET);
	        List<String> stopWords = getStopWords();
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, StopFilter.makeStopSet(Version.LUCENE_45, stopWords));
	        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
	        tokenStream.reset();
	        while (tokenStream.incrementToken()) 
	        {
	            if (sb.length() > 0) 
	            {
	                sb.append(" ");
	            }
	            
	            if(token.length()>1 && !(token.toString().matches(".*\\d.*") && (!token.toString().equals(""))))
	            	sb.append(token.toString().trim());
	        }
	        if(tokenStream!=null)
	        	tokenStream.close();
	        finalMap.put(Integer.parseInt(pairs.getKey().toString()),sb.toString());
		}   
		return finalMap;
    }
	
	
	public List removeStopWords(List list) throws IOException 
    {
		List finalList = new ArrayList();
		for(Object str:list)
		{
			String string=(String)str;
			StringBuilder sb = new StringBuilder();
			StandardAnalyzer ana = new StandardAnalyzer(Version.LUCENE_45);
	        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_45, new StringReader(string));
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, ana.STOP_WORDS_SET);
	        List<String> stopWords = getStopWords();
	        tokenStream = new StopFilter(Version.LUCENE_45, tokenStream, StopFilter.makeStopSet(Version.LUCENE_45, stopWords));
	        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
	        tokenStream.reset();
	        while (tokenStream.incrementToken()) 
	        {
	            if (sb.length() > 0) 
	            {
	                sb.append(" ");
	            }
	            
	            if(token.length()>1 && !(token.toString().matches(".*\\d.*") && (!token.toString().equals(""))))
	            	sb.append(token.toString().trim());
	        }
	        if(tokenStream!=null)
	        	tokenStream.close();
	        finalList.add(sb.toString().trim());
		}   
		return finalList;
    }
	
	
	public static List<String> getStopWords()
	{
		

		Set<String> set = new HashSet<String>();
		
		List<String> list = new ArrayList<String>();
		
		String[] spam = new String[] {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail","details", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};
		
		
		List<String> lst = new ArrayList<String>(Arrays.asList(spam));
		lst.add("com.google");
		lst.add("able");
	    lst.add("according");
	    lst.add("accordingly");
	    lst.add("actually");
	    lst.add("after");
	    lst.add("afterwards");
	    lst.add("again");
	    lst.add("against");
	    lst.add("allow");
	    lst.add("allowed");
	    lst.add("allows");
	    lst.add("alone");
	    lst.add("along");
	    lst.add("already");
	    lst.add("also");
	    lst.add("although");
	    lst.add("always");
	    lst.add("am");
	    lst.add("among");
	    lst.add("amongst");
	    lst.add("an");
	    lst.add("and");
	    lst.add("another");
	    lst.add("any");
	    lst.add("anybody");
	    lst.add("anyhow");
	    lst.add("anyone");
	    lst.add("anything");
	    lst.add("anyway");
	    lst.add("anyways");
	    lst.add("anywhere");
	    lst.add("apart");
	    lst.add("appear");
	    lst.add("appreciate");
	    lst.add("appropriate");
	    lst.add("are");
	    lst.add("around");
	    lst.add("as");
	    lst.add("aside");
	    lst.add("ask");
	    lst.add("asking");
	    lst.add("associated");
	    lst.add("at");
	    lst.add("available");
	    lst.add("away");
	    lst.add("awfully");
	    lst.add("b");
	    lst.add("be");
	    lst.add("became");
	    lst.add("because");
	    lst.add("become");
	    lst.add("becomes");
	    lst.add("becoming");
	    lst.add("been");
	    lst.add("before");
	    lst.add("beforehand");
	    lst.add("behind");
	    lst.add("being");
	    lst.add("believe");
	    lst.add("below");
	    lst.add("beside");
	    lst.add("besides");
	    lst.add("best");
	    lst.add("better");
	    lst.add("between");
	    lst.add("beyond");
	    lst.add("both");
	    lst.add("brief");
	    lst.add("but");
	    lst.add("But");
	    lst.add("by");
	    lst.add("c");
	    lst.add("came");
	    lst.add("can");
	    lst.add("cannot");
	    lst.add("cant");
	    lst.add("can't");
	    lst.add("cause");
	    lst.add("causes");
	    lst.add("certain");
	    lst.add("certainly");
	    lst.add("changes");
	    lst.add("clearly");
	    lst.add("co");
	    lst.add("com");
	    lst.add("come");
	    lst.add("comes");
	    lst.add("concerning");
	    lst.add("consequently");
	    lst.add("consider");
	    lst.add("considering");
	    lst.add("contain");
	    lst.add("containing");
	    lst.add("contains");
	    lst.add("corresponding");
	    lst.add("could");
	    lst.add("course");
	    lst.add("currently");
	    lst.add("d");
	    lst.add("definitely");
	    lst.add("described");
	    lst.add("despite");
	    lst.add("did");
	    lst.add("different");
	    lst.add("do");
	    lst.add("does");
	    lst.add("doing");
	    lst.add("done");
	    lst.add("down");
	    lst.add("downwards");
	    lst.add("during");
	    lst.add("e");
	    lst.add("each");
	    lst.add("edu");
	    lst.add("eg");
	    lst.add("eight");
	    lst.add("either");
	    lst.add("else");
	    lst.add("elsewhere");
	    lst.add("enough");
	    lst.add("entirely");
	    lst.add("especially");
	    lst.add("et");
	    lst.add("etc");
	    lst.add("even");
	    lst.add("ever");
	    lst.add("every");
	    lst.add("everybody");
	    lst.add("everyone");
	    lst.add("everything");
	    lst.add("everywhere");
	    lst.add("ex");
	    lst.add("exactly");
	    lst.add("example");
	    lst.add("except");
	    lst.add("f");
	    lst.add("far");
	    lst.add("few");
	    lst.add("fifth");
	    lst.add("first");
	    lst.add("five");
	    lst.add("followed");
	    lst.add("following");
	    lst.add("follows");
	    lst.add("for");
	    lst.add("former");
	    lst.add("formerly");
	    lst.add("forth");
	    lst.add("four");
	    lst.add("from");
	    lst.add("further");
	    lst.add("furthermore");
	    lst.add("g");
	    lst.add("get");
	    lst.add("gets");
	    lst.add("getting");
	    lst.add("given");
	    lst.add("gives");
	    lst.add("go");
	    lst.add("goes");
	    lst.add("going");
	    lst.add("gone");
	    lst.add("got");
	    lst.add("gotten");
	    lst.add("greetings");
	    lst.add("h");
	    lst.add("had");
	    lst.add("happens");
	    lst.add("hardly");
	    lst.add("has");
	    lst.add("have");
	    lst.add("having");
	    lst.add("he");
	    lst.add("hello");
	    lst.add("help");
	    lst.add("hence");
	    lst.add("her");
	    lst.add("here");
	    lst.add("hereafter");
	    lst.add("hereby");
	    lst.add("herein");
	    lst.add("hereupon");
	    lst.add("hers");
	    lst.add("herself");
	    lst.add("hi");
	    lst.add("him");
	    lst.add("himself");
	    lst.add("his");
	    lst.add("hither");
	    lst.add("hopefully");
	    lst.add("how");
	    lst.add("howbeit");
	    lst.add("however");
	    lst.add("i");
	    lst.add("ie");
	    lst.add("if");
	    lst.add("ignored");
	    lst.add("immediate");
	    lst.add("in");
	    lst.add("inasmuch");
	    lst.add("inc");
	    lst.add("indeed");
	    lst.add("indicate");
	    lst.add("indicated");
	    lst.add("indicates");
	    lst.add("inner");
	    lst.add("insofar");
	    lst.add("instead");
	    lst.add("into");
	    lst.add("inward");
	    lst.add("is");
	    lst.add("it");
	    lst.add("its");
	    lst.add("itself");
	    lst.add("inside");
	    lst.add("j");
	    lst.add("just");
	    lst.add("k");
	    lst.add("keep");
	    lst.add("keeps");
	    lst.add("kept");
	    lst.add("know");
	    lst.add("knows");
	    lst.add("known");
	    lst.add("l");
	    lst.add("last");
	    lst.add("lately");
	    lst.add("later");
	    lst.add("latter");
	    lst.add("latterly");
	    lst.add("least");
	    lst.add("less");
	    lst.add("lest");
	    lst.add("let");
	    lst.add("like");
	    lst.add("liked");
	    lst.add("likely");
	    lst.add("little");
	    lst.add("ll"); //lst.added to avoid words like you'll,I'll etc.
	    lst.add("look");
	    lst.add("looking");
	    lst.add("looks");
	    lst.add("ltd");
	    lst.add("m");
	    lst.add("mainly");
	    lst.add("many");
	    lst.add("may");
	    lst.add("maybe");
	    lst.add("me");
	    lst.add("mean");
	    lst.add("meanwhile");
	    lst.add("merely");
	    lst.add("might");
	    lst.add("more");
	    lst.add("moreover");
	    lst.add("most");
	    lst.add("mostly");
	    lst.add("much");
	    lst.add("must");
	    lst.add("my");
	    lst.add("myself");
	    lst.add("n");
	    lst.add("name");
	    lst.add("namely");
	    lst.add("nd");
	    lst.add("near");
	    lst.add("nearly");
	    lst.add("necessary");
	    lst.add("need");
	    lst.add("needs");
	    lst.add("neither");
	    lst.add("never");
	    lst.add("nevertheless");
	    lst.add("new");
	    lst.add("next");
	    lst.add("nine");
	    lst.add("no");
	    lst.add("nobody");
	    lst.add("non");
	    lst.add("none");
	    lst.add("noone");
	    lst.add("nor");
	    lst.add("normally");
	    lst.add("not");
	    lst.add("nothing");
	    lst.add("novel");
	    lst.add("now");
	    lst.add("nowhere");
	    lst.add("o");
	    lst.add("obviously");
	    lst.add("of");
	    lst.add("off");
	    lst.add("often");
	    lst.add("oh");
	    lst.add("ok");
	    lst.add("okay");
	    lst.add("old");
	    lst.add("on");
	    lst.add("once");
	    lst.add("one");
	    lst.add("ones");
	    lst.add("only");
	    lst.add("onto");
	    lst.add("or");
	    lst.add("other");
	    lst.add("others");
	    lst.add("otherwise");
	    lst.add("ought");
	    lst.add("our");
	    lst.add("ours");
	    lst.add("ourselves");
	    lst.add("out");
	    lst.add("outside");
	    lst.add("over");
	    lst.add("overall");
	    lst.add("own");
	    lst.add("p");
	    lst.add("particular");
	    lst.add("particularly");
	    lst.add("per");
	    lst.add("perhaps");
	    lst.add("placed");
	    lst.add("please");
	    lst.add("plus");
	    lst.add("possible");
	    lst.add("presumably");
	    lst.add("probably");
	    lst.add("provides");
	    lst.add("q");
	    lst.add("que");
	    lst.add("quite");
	    lst.add("qv");
	    lst.add("r");
	    lst.add("rather");
	    lst.add("rd");
	    lst.add("re");
	    lst.add("really");
	    lst.add("reasonably");
	    lst.add("regarding");
	    lst.add("regardless");
	    lst.add("regards");
	    lst.add("relatively");
	    lst.add("respectively");
	    lst.add("right");
	    lst.add("s");
	    lst.add("said");
	    lst.add("same");
	    lst.add("saw");
	    lst.add("say");
	    lst.add("saying");
	    lst.add("says");
	    lst.add("second");
	    lst.add("secondly");
	    lst.add("see");
	    lst.add("seeing");
	    lst.add("seem");
	    lst.add("seemed");
	    lst.add("seeming");
	    lst.add("seems");
	    lst.add("seen");
	    lst.add("self");
	    lst.add("selves");
	    lst.add("sensible");
	    lst.add("sent");
	    lst.add("serious");
	    lst.add("seriously");
	    lst.add("seven");
	    lst.add("several");
	    lst.add("shall");
	    lst.add("she");
	    lst.add("should");
	    lst.add("since");
	    lst.add("six");
	    lst.add("so");
	    lst.add("some");
	    lst.add("somebody");
	    lst.add("somehow");
	    lst.add("someone");
	    lst.add("something");
	    lst.add("sometime");
	    lst.add("sometimes");
	    lst.add("somewhat");
	    lst.add("somewhere");
	    lst.add("soon");
	    lst.add("sorry");
	    lst.add("specified");
	    lst.add("specify");
	    lst.add("specifying");
	    lst.add("still");
	    lst.add("sub");
	    lst.add("such");
	    lst.add("sup");
	    lst.add("sure");
	    lst.add("t");
	    lst.add("take");
	    lst.add("taken");
	    lst.add("tell");
	    lst.add("tends");
	    lst.add("th");
	    lst.add("than");
	    lst.add("thank");
	    lst.add("thanks");
	    lst.add("thanx");
	    lst.add("that");
	    lst.add("thats");
	    lst.add("the");
	    lst.add("their");
	    lst.add("theirs");
	    lst.add("them");
	    lst.add("themselves");
	    lst.add("then");
	    lst.add("thence");
	    lst.add("there");
	    lst.add("thereafter");
	    lst.add("thereby");
	    lst.add("therefore");
	    lst.add("therein");
	    lst.add("theres");
	    lst.add("thereupon");
	    lst.add("these");
	    lst.add("they");
	    lst.add("think");
	    lst.add("third");
	    lst.add("this");
	    lst.add("thorough");
	    lst.add("thoroughly");
	    lst.add("those");
	    lst.add("though");
	    lst.add("three");
	    lst.add("through");
	    lst.add("throughout");
	    lst.add("thru");
	    lst.add("thus");
	    lst.add("to");
	    lst.add("together");
	    lst.add("too");
	    lst.add("took");
	    lst.add("toward");
	    lst.add("towards");
	    lst.add("tried");
	    lst.add("tries");
	    lst.add("truly");
	    lst.add("try");
	    lst.add("trying");
	    lst.add("twice");
	    lst.add("two");
	    lst.add("u");
	    lst.add("un");
	    lst.add("under");
	    lst.add("unfortunately");
	    lst.add("unless");
	    lst.add("unlikely");
	    lst.add("until");
	    lst.add("unto");
	    lst.add("up");
	    lst.add("upon");
	    lst.add("us");
	    lst.add("use");
	    lst.add("used");
	    lst.add("useful");
	    lst.add("uses");
	    lst.add("using");
	    lst.add("usually");
	    lst.add("uucp");
	    lst.add("v");
	    lst.add("value");
	    lst.add("various");
	    lst.add("ve"); //lst.added to avoid words like I've,you've etc.
	    lst.add("very");
	    lst.add("via");
	    lst.add("viz");
	    lst.add("vs");
	    lst.add("w");
	    lst.add("want");
	    lst.add("wants");
	    lst.add("was");
	    lst.add("way");
	    lst.add("we");
	    lst.add("welcome");
	    lst.add("well");
	    lst.add("went");
	    lst.add("were");
	    lst.add("what");
	    lst.add("whatever");
	    lst.add("when");
	    lst.add("whence");
	    lst.add("whenever");
	    lst.add("where");
	    lst.add("whereafter");
	    lst.add("whereas");
	    lst.add("whereby");
	    lst.add("wherein");
	    lst.add("whereupon");
	    lst.add("wherever");
	    lst.add("whether");
	    lst.add("which");
	    lst.add("while");
	    lst.add("whither");
	    lst.add("who");
	    lst.add("whoever");
	    lst.add("whole");
	    lst.add("whom");
	    lst.add("whose");
	    lst.add("why");
	    lst.add("will");
	    lst.add("willing");
	    lst.add("wish");
	    lst.add("with");
	    lst.add("within");
	    lst.add("without");
	    lst.add("wonder");
	    lst.add("would");
	    lst.add("would");
	    lst.add("x");
	    lst.add("y");
	    lst.add("yes");
	    lst.add("yet");
	    lst.add("you");
	    lst.add("your");
	    lst.add("yours");
	    lst.add("yourself");
	    lst.add("yourselves");
	    lst.add("z");
	    lst.add("zero");
		lst.add("like");
		lst.add("apart");
		lst.add("changes");
		lst.add("I'd");
		lst.add("e.g");
		lst.add("The");
		lst.add("problem");
		lst.add("type");
		lst.add("something");
		lst.add("matter");
		lst.add("way");
		lst.add("Is");
		lst.add("using");
		lst.add("check");
		lst.add("I'm");
		lst.add("gives");
		lst.add("www.google.com");
		lst.add("href");
		lst.add("http");
		lst.add("answer");
		lst.add("NA");
		lst.add("num"); 
		lst.add("int"); 
		lst.add("double");
		lst.add("How");
		lst.add("If");
		lst.add("a_");
		lst.add("length");
		lst.add("little");
		lst.add("hand");
		lst.add("missing");
		
		set.addAll(lst);
		StringBuilder result=new StringBuilder();
		for(String term:set){
			result.append(Character.toUpperCase(term.charAt(0))).append(term.substring(1));
			String str= result.toString();
			list.add(str);
			result.delete(0, result.length());
		}
		list.addAll(set);
		return list;
	}
	
}
