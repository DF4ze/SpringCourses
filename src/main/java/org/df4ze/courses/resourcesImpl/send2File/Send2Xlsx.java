package org.df4ze.courses.resourcesImpl.send2File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.df4ze.courses.resources.send2File.EntityWrapper;
import org.df4ze.courses.resources.send2File.Send2File;
import org.springframework.stereotype.Component;

@Component
public class Send2Xlsx implements Send2File {

	private XSSFSheet mySheet;
	private XSSFWorkbook myWorkBook;
	private File myFile;


	public Send2Xlsx() {
	}

	@Override
	public void openFile(String filePath) throws IOException {
		myFile = new File(filePath); 
		FileInputStream fis = new FileInputStream(myFile); 
		myWorkBook = new XSSFWorkbook (fis); 
		mySheet = myWorkBook.getSheetAt(0);
	}

	@Override
	public void createFile(String filePath) throws IOException {
		myWorkBook = new XSSFWorkbook();
		mySheet = myWorkBook.createSheet("CourseComplete");
		
		myFile = new File(filePath);
		FileOutputStream fos = new FileOutputStream(myFile);
		myWorkBook.write(fos);
		fos.flush();
		fos.close();
		
		openFile(filePath);
	}
	
	@Override
	public void closeFile() {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(myFile);
			myWorkBook.write(os);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
		
		}finally {
			if( myWorkBook != null ){
				try {
					myWorkBook.close();
				} catch (IOException e) {
				}
				try {
					if( os != null )
						os.close();
				} catch (IOException e) {
				}
			}
		}
        

	}

	@Override
	public void addRow(EntityWrapper wrapper) {
		// get the last row number to append new data 
		int rownum = mySheet.getLastRowNum(); 
		Row row = mySheet.createRow(rownum++);
		int cellnum = 0;
		for (Object obj : wrapper.getAttributesList()) {
			addCell2Row( row, cellnum, obj );
			cellnum++;
		} 
	}

	@Override
	public void addRows(Collection<EntityWrapper> wrappers) {
		for( EntityWrapper wrapper : wrappers) {
			addRow(wrapper);
		}
	}
	
	private void addCell2Row( Row row, int cellnum, Object obj ){
		Cell cell = row.createCell(cellnum++);
		if( obj != null ){
			if (obj instanceof String) { 
				cell.setCellValue((String) obj); 
			} else if (obj instanceof Boolean) { 
				cell.setCellValue((Boolean) obj); 
			} else if (obj instanceof Date) { 
				cell.setCellValue((Date) obj); 
			} else if (obj instanceof Double) { 
				cell.setCellValue((Double) obj); 
			} else if (obj instanceof Float) { 
				cell.setCellValue((Float) obj); 
			} else if (obj instanceof Long) { 
				cell.setCellValue((Long) obj); 
			} else if (obj instanceof Integer) { 
				cell.setCellValue((Integer) obj); 
			} else
				throw new RuntimeException("Not Yet Implemented : "+obj.getClass().getName());
		}
	}


}
