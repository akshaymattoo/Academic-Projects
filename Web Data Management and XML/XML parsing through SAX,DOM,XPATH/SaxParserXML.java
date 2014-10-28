import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class SaxParserXML extends DefaultHandler{

	
	String reading;
	
	SigmodRecord sgr;
	List<Issue> issueList;
	Issue issue;
	List<Article> articleList;
	Articles articles;
	List<Author> authorList;
	Authors authors;
	Author author;
	Article article;
    public SaxParserXML ( String file ) {
		super();
		try {
		    SAXParserFactory factory = SAXParserFactory.newInstance();
		    factory.setValidating(false);
		    factory.setNamespaceAware(false);
		    XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		    xmlReader.setContentHandler(this);
		    xmlReader.parse(file);
		} catch (Exception e) {
		    throw new Error(e);
		}
    }

    public void startElement ( String uri, String name, String tag, Attributes atts ) {
	  
    	if(tag.equals("SigmodRecord")){
    		sgr =new SigmodRecord();
    		issueList =new ArrayList<Issue>();
    	}
    	
    	if(tag.equals("issue")){
    		issue = new Issue();
    	}
    	if(tag.equals("articles"))
    	{
    		articles = new Articles();
    		articleList = new ArrayList<Article>();
    	}
    	if(tag.equals("article"))
    	{
    		article = new Article();
    	}
    	if(tag.equals("authors"))
    	{
    		authors= new Authors();
    		authorList = new ArrayList<Author>();
    	}
    	if(tag.equals("author"))
    	{
    		author =new Author();
    	}
    }

    public void endElement ( String uri, String name, String tag ) {
    	if(tag.equals("SigmodRecord")){
    		sgr.setIssue(issueList);
    	}
    	
    	if(tag.equals("issue")){
    		issue.setArticles(articles);
    		issueList.add(issue);
    	}
    	
    	if(tag.equals("volume")){
    		issue.setVolume(reading);
    	}
    	if(tag.equals("number")){
    		issue .setNumber(reading);
    	}
    	
    	if(tag.equals("articles"))
    	{
    		articles.setArticle(articleList);
    	}
    	
    	if(tag.equals("article"))
    	{
    		articleList.add(article);
    		
    	}
    	
    	if(tag.equals("title"))
    	{
    		article.setTitle(reading);
    	}
    	
    	if(tag.equals("initPage"))
    	{
    		article.setInitPage(reading);
    	}
    	
    	if(tag.equals("endPage"))
    	{
    		article.setEndPage(reading);
    	}
    	
    	if(tag.equals("authors"))
    	{
    		authors.setAuthor(authorList);
    		article.setAuthors(authors);
    	}
    	
    	if(tag.equals("author"))
    	{
    		author.setAuthor(reading);
    		authorList.add(author);
    	}
    	
    }

    public void characters ( char text[], int start, int length ) {
    	  reading =  new String(text,start,length);
    }
	
	
	public static void main(String[] args) {
		SaxParserXML sxl =new SaxParserXML("SigmodRecord.xml");
		SigmodRecord sg = sxl.sgr;
		
		getTile(sg);
		System.out.println("********************************");
		getAuthor(sg);
		System.out.println("********************************");
		getVolumeInitPage(sg);
		
	}
	
	public static void getTile(SigmodRecord sg){
		
		for(Issue is:sg.getIssue()){
		
			if(is.getVolume().equals("13") && is.getNumber().equals("4"))
			{
				Articles artls = is.getArticles();
				for(Article art:artls.getArticle()){
					Authors authrs = art.getAuthors();
					
					for(Author authr:authrs.getAuthor()){
						if(authr.getAuthor().equals("David Maier"))
						{
							System.out.println("<title>"+art.getTitle()+"</title>");
						}
					}
				}
			}
		}
		
	}
	
	public static void getAuthor(SigmodRecord sg){
		
		for(Issue is:sg.getIssue()){
			
				Articles artls = is.getArticles();
				for(Article art:artls.getArticle()){
					
					if(art.getTitle().contains("database") || art.getTitle().contains("Database"))
					{	
					
						Authors authrs = art.getAuthors();
						
						for(Author authr:authrs.getAuthor()){
							 
							System.out.println("<author>"+authr.getAuthor()+"</author>");
							 
						}
						
					}
				}
		}
		
	}
	
	public static void getVolumeInitPage(SigmodRecord sg){
		
		for(Issue is:sg.getIssue()){
			
			Articles artls = is.getArticles();
			for(Article art:artls.getArticle()){
				
				if(art.getTitle().contains("Research in Knowledge Base Management Systems."))
				{	
				
					System.out.println("<volume>"+is.getVolume()+"</volume>");
					System.out.println("<number>"+is.getNumber()+"</number>");
					System.out.println("<initPage>"+art.getInitPage()+"</initPage>");
					System.out.println("<endPage>"+art.getEndPage()+"</endPage>");
					
				}
			}
	}
		
	}
}

class Article {

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitPage() {
		return initPage;
	}
	public void setInitPage(String initPage) {
		this.initPage = initPage;
	}
	public String getEndPage() {
		return endPage;
	}
	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}
	

	public Authors getAuthors() {
		return authors;
	}
	public void setAuthors(Authors authors) {
		this.authors = authors;
	}



	private String title;
	private String initPage;
	private String endPage;
	private Authors authors;
}

class Author {

	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}

class Articles {

	private List<Article> article;

	public List<Article> getArticle() {
		return article;
	}

	public void setArticle(List<Article> article) {
		this.article = article;
	}
}

class Authors {

	private List<Author> author;

	public List<Author> getAuthor() {
		return author;
	}

	public void setAuthor(List<Author> author) {
		this.author = author;
	}

 
	
}

class Issue {

	private String volume;
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	 
	public Articles getArticles() {
		return articles;
	}
	public void setArticles(Articles articles) {
		this.articles = articles;
	}

	private String number;
	private Articles articles;
	
}

class SigmodRecord {

	private List<Issue> issue;

	public List<Issue> getIssue() {
		return issue;
	}

	public void setIssue(List<Issue> issue) {
		this.issue = issue;
	}
	
	
}
