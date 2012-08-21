package com.metacube.ipathshala.dataupload;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import com.metacube.ipathshala.util.AppLogger;

public class XLSDataReader implements DataUploadReader {
	
	private static final AppLogger log = AppLogger.getLogger(XLSDataReader.class);

	private HSSFWorkbook workBook;
	
	@Override
	public void init(InputStream stream) {
		try {			
				workBook = new HSSFWorkbook(stream);
			
		}catch (IOException ex) {			
			log.error("Failed to import Data", ex);
		}
		
	}
	
	@Override
	public ArrayList readData(int entityType) {
		ArrayList rows = new ArrayList();
		try {
			if(workBook!=null){
				String sheetName = DataUploadEntityType.toString(entityType);
				HSSFSheet sheetData = workBook.getSheet(sheetName);
				if(sheetData!=null)
				rows = readSheet(sheetData);
			}
		} catch (Exception ex) {
			workBook=null;
			log.error("Failed to import Data", ex);
		}
		return rows;
	}
	

	/**
	 * This method reads the sheet and creates the in-memory list structure for rows and columns
	 * First row is left and assumed to be column name
	 * @param sheet
	 * @return
	 * 
	 */
	private ArrayList readSheet(HSSFSheet sheet ){
		
		ArrayList rows = new ArrayList();
		int noOfRows = sheet.getPhysicalNumberOfRows();	
		boolean firstrow = Boolean.TRUE;
		int cells = 0;
		for (int r = 0; r < noOfRows; r++) {
			HSSFRow row = sheet.getRow(r);
						
			if (row == null) {
				continue;
			}
			
			if(firstrow){
				firstrow = Boolean.FALSE;
				cells = row.getLastCellNum();
				continue;
			}

			
			if(cells<=0 )
				continue;
			
			ArrayList cols = new ArrayList();
			rows.add(cols);
			
			
			//retrieve cells			
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c,Row.CREATE_NULL_AS_BLANK);
				
				if(cell==null){
					cols.add("");
					continue;
				}
				
				Object value = "";

				switch (cell.getCellType()) {

					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;

					case HSSFCell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							value = cell.getDateCellValue();
	                    } else {
	                    	value =  cell.getNumericCellValue();
	                    }
						break;

					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
						
					 case HSSFCell.CELL_TYPE_BOOLEAN:
						 value = cell.getBooleanCellValue();
		                    break;							

					default:
				}
				/*System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="
						+ value);*/
				
				if(value instanceof String){
				value = ((String)value).trim();	
				}	
				cols.add(value);}
				
			
		}			

		return rows;
		
	}


	
	

}
