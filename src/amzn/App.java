package amzn;



	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
	/**
	 * Hello world!
	 *
	 */
	public class App 
	{

	    private static final String CSV_FILE_PATH= "rr.csv";
	    public static void main(String[] args)
	    {
	    	writeDataForCustomSeparatorCSV("retailer.txt");
	    	writeDataForCustomSeparatorCSV(CSV_FILE_PATH);
	    	readAllDataAtOnce(CSV_FILE_PATH);
	    	String jdbcUrl="jdbc:mysql://localhost:3306/amzn";
	        String username="root";
	        String password="Adi@2368";

	        String filePath=CSV_FILE_PATH;

	        int batchSize=10;

	        Connection connection=null;


	        try{
	            connection= DriverManager.getConnection(jdbcUrl,username,password);
	            connection.setAutoCommit(false);

	            String sql="insert into retailer(name,age,region,phone) values(?,?,?,?)";

	            PreparedStatement statement=connection.prepareStatement(sql);

	            BufferedReader lineReader=new BufferedReader(new FileReader(filePath));

	            String lineText=null;
	            int count=0;

	            lineReader.readLine();
	            while ((lineText=lineReader.readLine())!=null){
	                String[] data=lineText.split(",");

	                String name=data[0];
	                String age=data[1];
	                String region=data[2];
	                String phone=data[3];

	                statement.setString(1,name);
	                statement.setString(2,age);
	                statement.setString(3,region);
	                statement.setString(4,phone);
	                statement.addBatch();
	                if(count%batchSize==0){
	                    statement.executeBatch();
	                    count++;
	                }
	            }
	            lineReader.close();
	            statement.executeBatch();
	            connection.commit();
	            connection.close();
	            System.out.println("Data has been inserted successfully.");

	        }
	        catch (Exception exception){
	            exception.printStackTrace();
	        }
	    }
	   
	    	public static void writeDataForCustomSeparatorCSV(String filePath)
	    	{
	    	  // first create file object for file placed at location
	    	    // specified by filepath
	    	    File file = new File(filePath);
	    	  
	    	    try {
	    	        // create FileWriter object with file as parameter
	    	        FileWriter outputfile = new FileWriter(file);
	    	  
	    	        // create CSVWriter with '|' as separator
	    	        CSVWriter writer = new CSVWriter(outputfile, ',',
	    	                                         CSVWriter.NO_QUOTE_CHARACTER,
	    	                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	    	                                         CSVWriter.DEFAULT_LINE_END);
	    	  
	    	        // create a List which contains String array
	    	        List<String[]> data = new ArrayList<String[]>();
	    	        data.add(new String[] { "Name", "Age", "Region","Number" });
	    	        data.add(new String[] { "Aditya", "23", "KA","9980403011" });
	    	        data.add(new String[] { "Shiva", "22", "WB","3456792092" });
	    	        data.add(new String[] { "Amar", "22", "OD","9349321234" });
	    	        data.add(new String[] { "Aranya", "33", "UP","1234567890" });
	    	        data.add(new String[] { "Amish", "44", "UP","7747761234" });
	    	        data.add(new String[] { "Akash", "55", "KA","8789675420" });
	    	        data.add(new String[] { "Akashya", "46", "TN","2345124567" });
	    	        data.add(new String[] { "Anuradha", "27", "KL","8745093245" });
	    	        data.add(new String[] { "Ahmed", "28", "MH","5684796530" });
	    	        data.add(new String[] { "Ashish", "29", "PUN","1209475896" });
	    	        data.add(new String[] { "Nirav", "33", "GUJ","6578694059" });

	    	        writer.writeAll(data);
	    	  
	    	        // closing writer connection
	    	        writer.close();
	    	    }
	    	    catch (IOException e) {
	    	        // TODO Auto-generated catch block
	    	        e.printStackTrace();
	    	    }
	    	}
	    	public static void readAllDataAtOnce(String file)
	    	{
	    	    try {
	    	        // Create an object of file reader
	    	        // class with CSV file as a parameter.
	    	        FileReader filereader = new FileReader(file);
	    	  
	    	        // create csvReader object and skip first Line
	    	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	    	                                  .withSkipLines(1)
	    	                                  .build();
	    	        List<String[]> allData = csvReader.readAll();
	    	  
	    	        // print Data
	    	        for (String[] row : allData) {
	    	            for (String cell : row) {
	    	                System.out.print(cell + "\t");
	    	            }
	    	            System.out.println();
	    	        }
	    	    }
	    	    catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	}


