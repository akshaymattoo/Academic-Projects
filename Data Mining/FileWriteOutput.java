import java.io.FileWriter;
import java.io.IOException;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


public class FileWriteOutput {

	 private static ICsvBeanWriter beanWriter = null;
	 private static FinalOutputBean fb = null;
	 private static String[] header=null;
	 private static CellProcessor[] processors =null;
	
private static CellProcessor[] getProcessors() {
        
        final CellProcessor[] processors = new CellProcessor[] { 
                new NotNull(), // firstName
                new NotNull(), // lastName
        };
        
        return processors;
}


public static void createHeader()
{
     try {
    	 	 fb = new FinalOutputBean();
             beanWriter = new CsvBeanWriter(new FileWriter("Result.csv"),
                     CsvPreference.STANDARD_PREFERENCE);
             
             // the header elements are used to map the bean values to each column (names must match)
             header = new String[] { "Id", "Tags"};
             processors = getProcessors();
             
             // write the header
             beanWriter.writeHeader(header);
     }catch(Exception e)
     {
    	 e.printStackTrace();
     }
	
}


public static void printRows(int id, String tags)
{
	try
	{
		fb.setId(id);
	    fb.setTags(tags);
	    beanWriter.write(fb, header, processors);
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}

public static void closeReader()
{
	 if( beanWriter != null ) {
         try {
				beanWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}

}
