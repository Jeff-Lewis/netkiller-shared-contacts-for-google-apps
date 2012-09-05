package com.netkiller.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CSVFileReader {
	private static final AppLogger log = AppLogger
			.getLogger(CSVFileReader.class);

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

			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.inputStream, "UTF-8"));
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;

			while ((fileName = br.readLine()) != null) {

				lineNumber++;
				st = new StringTokenizer(fileName, ",");
				storeValues = new ArrayList<String>();
				while (fileName.indexOf(",") != -1) {
					if (fileName.indexOf("\"") == -1
							|| fileName.indexOf("\"") > fileName.indexOf(",")) {
						String token = fileName.substring(0,
								fileName.indexOf(","));

						fileName = fileName
								.substring(fileName.indexOf(",") + 1);
						storeValues.add(token);

					} else {

						int index1 = fileName.indexOf("\"");
						String portion1 = fileName.substring(index1 + 1);
						int index2 = portion1.indexOf("\"");
						String portion2 = portion1.substring(index2 + 1);
						int index3 = portion2.indexOf(",");
						storeValues.add(fileName.substring(index1 + 1, index1
								+ index2 + index3 + 1));

						fileName = fileName.substring(index1 + index2 + index3
								+ 2 + 1);

					}

				}
				storeValues.add(fileName);
				storeValuesList.add(storeValues);

				// reset token number
				tokenNumber = 0;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
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
