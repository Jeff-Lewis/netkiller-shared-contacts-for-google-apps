package com.netkiller.service.conversion;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.netkiller.core.AppException;

public interface ConversionService {


	public void convertAndWrite(ConversionType srcType,ConversionType destType,byte[] input,  OutputStream outputStream, Map<String, ConversionResource> resources) throws AppException;
	public byte[] convert(ConversionType srcType, ConversionType destType,byte[] input, Map<String, ConversionResource> resources) throws AppException;
	public List<byte[]> convert(ConversionType srcType,ConversionType destType,List<byte[]> inputList, Map<String, ConversionResource> resources) throws AppException;
	public Future<byte[]> convertAsync(ConversionType srcType,ConversionType destType,byte[] input, Map<String, ConversionResource> resources);
	public Future<List<byte[]>> convertAsync(ConversionType srcType,ConversionType destType,List<byte[]> input, Map<String, ConversionResource> resources);
	
}
