package com.metacube.ipathshala.service.conversion.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.service.conversion.ConversionResource;
import com.metacube.ipathshala.service.conversion.ConversionService;
import com.metacube.ipathshala.service.conversion.ConversionType;

@Component("ItextConversionService")
public class ItextConversionService implements ConversionService {
	@Override
	public void convertAndWrite(ConversionType srcType,
			ConversionType destType, byte[] input, OutputStream outputStream, Map<String, ConversionResource> resources) throws AppException {
		try {
			com.lowagie.text.Document document = new com.lowagie.text.Document(
					PageSize.LETTER);
			switch(destType)	{
			case HTML:
				break;
			case PDF:
				PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
				break;
			}
			
			document.open();
			switch(srcType)	{
			case HTML:
				HTMLWorker htmlWorker = new HTMLWorker(document);
				String html = new String(input);
				htmlWorker.parse(new StringReader(html));				
				break;
			case PDF:
				break;
			}
			
			document.close();
		} catch (IOException e) {
			String message = "Unable to convert from "+srcType.toString()+" to "+destType.toString();
			throw new AppException(message);
		} catch (DocumentException e) {
			String message = "Unable to convert from "+srcType.toString()+" to "+destType.toString();
			throw new AppException(message);
		}
	}

	@Override
	public byte[] convert(ConversionType srcType, ConversionType destType,
			byte[] input, Map<String, ConversionResource> resources) throws AppException {
		byte[] data = null;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(5000);
			com.lowagie.text.Document document = new com.lowagie.text.Document(
					PageSize.LETTER);
			switch(srcType)	{
			case HTML:
				break;
			case PDF:
				PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
				break;
			}
			
			document.open();
			switch(srcType)	{
			case HTML:
				HTMLWorker htmlWorker = new HTMLWorker(document);
				String html = new String(input);
				htmlWorker.parse(new StringReader(html));
				break;
			case PDF:
				break;
			}
			document.close();
			data = outputStream.toByteArray();
		} catch (IOException e) {
			String message = "Unable to convert from "+srcType.toString()+" to "+destType.toString();
			throw new AppException(message);
		} catch (DocumentException e) {
			String message = "Unable to convert from "+srcType.toString()+" to "+destType.toString();
			throw new AppException(message);
		}
		return data;
	}

	@Override
	public List<byte[]> convert(ConversionType srcType,
			ConversionType destType, List<byte[]> inputList, Map<String, ConversionResource> resources) throws AppException {
		List<byte[]> ouputData = new ArrayList<byte[]>();
		for(byte[] data: inputList){
			byte[] output = this.convert(srcType, destType, data, null);
			ouputData.add(output);
		}
		return ouputData;
	}

	@Override
	public Future<byte[]> convertAsync(ConversionType srcType,
			ConversionType destType, byte[] input, Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<byte[]>> convertAsync(ConversionType srcType,
			ConversionType destType, List<byte[]> input, Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

}
