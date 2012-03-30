package com.netkiller.web.sharedcontacts.upload;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileReader {

	protected final Logger logger = Logger.getLogger(getClass().getName());
	
	String fileName;
	InputStream inputStream;

	ArrayList<String> storeValues = new ArrayList<String>();
	ArrayList<ArrayList<String>> storeValuesList = new ArrayList<ArrayList<String>>();

	public CSVFileReader(String FileName) {
		this.fileName = FileName;
	}

	public CSVFileReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void ReadFile() {

		try {
			// storeValues.clear();//just in case this is the second call of the
			// ReadFile Method./
			// BufferedReader br = new BufferedReader( new
			// FileReader(fileName));
			//BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
			BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream, "UTF-8"));
			//BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream, "MS949"));
			//BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream, "Cp1252"));
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;

			while ((fileName = br.readLine()) != null) {

				lineNumber++;
				
				logger.log(Level.INFO, fileName);
				// storeValues.add(fileName);
				// break comma separated line using ","
				st = new StringTokenizer(fileName, ",");
				storeValues = new ArrayList<String>();
				while (fileName.indexOf(",") != -1) {
					if (fileName.indexOf("\"") == -1 ||fileName.indexOf("\"")>fileName.indexOf(",")) {
						String token = fileName.substring(0, fileName.indexOf(","));
						logger.log(Level.INFO, "token:"+token);
						fileName = fileName.substring(fileName.indexOf(",")+1);
						storeValues.add(token);
						logger.log(Level.INFO, "Token:"+token);
					} else	{
						
						
							int index1 =  fileName.indexOf("\"");
							String portion1  = fileName.substring(index1+1);
							int index2=portion1.indexOf("\"");
							String portion2 = portion1.substring(index2+1);
							int index3=portion2.indexOf(",");
							storeValues.add(fileName.substring(index1+1,index1+index2+index3+1));
							logger.log(Level.INFO, "Token1:"+fileName.substring(index1+1,index1+index2+2));
							fileName = fileName.substring(index1+index2+index3+2+1);
							
							
							
						
					}
					
				}
				logger.log(Level.INFO, "Token3"+fileName);
				storeValues.add(fileName);
				/*while (st.hasMoreTokens()) {
					String nextToken = st.nextToken();
					System.out.println("nextToken");
					if (!StringUtils.isBlank(nextToken))
						storeValues.add(nextToken.substring(1, nextToken.length() - 1));
					else {
						storeValues.add("");
					}

					System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : " + nextToken);

				}*/
				logger.log(Level.INFO, "Size of row is" + storeValues.size());
				storeValuesList.add(storeValues);

				// reset token number
				tokenNumber = 0;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<ArrayList<String>> getStoreValuesList() {
		return storeValuesList;
	}

	public void setStoreValuesList(ArrayList<ArrayList<String>> storeValuesList) {
		this.storeValuesList = storeValuesList;
	}

	// mutators and accesors
	public void setFileName(String newFileName) {
		this.fileName = newFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public ArrayList getFileValues() {
		return this.storeValues;
	}

	public void displayArrayList() {
		for (int x = 0; x < this.storeValues.size(); x++) {
			System.out.println(storeValues.get(x));
		}
	}

}