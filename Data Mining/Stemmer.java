import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class Stemmer{
    public static String Stem(String text) throws IOException{
        StringBuffer result = new StringBuffer();
        if (text!=null && text.trim().length()>0){
            StringReader tReader = new StringReader(text);
            Analyzer analyzer = new EnglishAnalyzer(Version.LUCENE_45);
            TokenStream tStream = analyzer.tokenStream("contents", tReader);
            CharTermAttribute term = tStream.addAttribute(CharTermAttribute.class);
            tStream.reset();
            try {
                while (tStream.incrementToken()){
                    result.append(term.toString());
                    result.append(" ");
                }
            } catch (IOException ioe){
                System.out.println("Error: "+ioe.getMessage());
            }
        }

        // If, for some reason, the stemming did not happen, return the original text
        if (result.length()==0)
            result.append(text);
        return result.toString().trim();
    }

    public static void main (String[] args){
        try {
        	String val="Michele Bachmann amenities pressed her allegations that the former head of her Iowa presidential bid was bribed by the campaign of rival Ron Paul to endorse him, even as one of her own aides denied the charge";
        	System.out.println(val);
        	val =RemoveStopWords.removeStopWords(val);
        	System.out.println(val);
			String str =Stemmer.Stem("Michele Bachmann amenities pressed her allegations that the former head of her Iowa presidential bid was bribed by the campaign of rival Ron Paul to endorse him, even as one of her own aides denied the charge.");
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}