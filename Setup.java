package test;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {

	static WebDriver driver;
	static MongoCollection<Document> collection;
	public static void main(String[] args) {
		
		
		connectMongoDb();
		setup();
		test_One();
		tearDown();
	} 
	
	
	public static void connectMongoDb()
	{
		// this create method will return a mongo client 
		MongoClient clientobj = MongoClients.create("mongodb://localhost:27017");
		
		//getDatabase this  method will return a mongodb 
		MongoDatabase database = clientobj.getDatabase("testing");
		//create collection 
		 collection = database.getCollection("web");
			
	}
	
	public static void setup()
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--headless");
		driver = new ChromeDriver();
	}
	
	
	
	public static void test_One()
	{
		
		driver.get("https://www.mongodb.com");
		String url = driver.getCurrentUrl();
		String title = driver.getTitle();
		Document d1=new Document();
		//key , value pair  JSON format
		d1.append("url", url);
		d1.append("title", title);
		
		List<Document> docsList = new ArrayList<Document>();
		docsList.add(d1);
		collection.insertMany(docsList);
	}
	
	
	
	public static void tearDown()
	{
		
		driver.quit();
	}
	
}
