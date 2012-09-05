package com.netkiller.service.conversion.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.stereotype.Component;

import com.google.appengine.api.conversion.Asset;
import com.google.appengine.api.conversion.Conversion;
import com.google.appengine.api.conversion.ConversionResult;
import com.google.appengine.api.conversion.ConversionServiceFactory;
import com.google.appengine.api.conversion.Document;
import com.netkiller.core.AppException;
import com.netkiller.service.conversion.ConversionResource;
import com.netkiller.service.conversion.ConversionService;
import com.netkiller.service.conversion.ConversionType;
import com.netkiller.service.conversion.ConversionUtil;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonUtil;

@Component("GoogleConversionService")
public class GoogleConversionService implements ConversionService {

	private static final AppLogger log = AppLogger.getLogger(GoogleConversionService.class);

	@Override
	public void convertAndWrite(ConversionType srcType, ConversionType destType, byte[] input,
			OutputStream outputStream, Map<String, ConversionResource> resources) throws AppException {
		try {
			byte[] output = this.convert(srcType, destType, input, resources);
			if (output != null) {

				outputStream.write(output);

			}
		} catch (IOException e) {
			String message = "Unable to convert from " + srcType.toString() + " to " + destType.toString();
			throw new AppException(message);
		}

	}

	@Override
	public byte[] convert(ConversionType srcType, ConversionType destType, byte[] input,
			Map<String, ConversionResource> resources) throws AppException {
		byte[] output = null;
		String inputMimeType = ConversionUtil.getMimeType(srcType);
		String outputMimeType = ConversionUtil.getMimeType(destType);
		Asset asset = new Asset(inputMimeType, input, "sample.html");
		List<Asset> assetsList = new ArrayList<Asset>();
		assetsList.add(asset);
		if (resources != null) {
			for (String resourceName : resources.keySet()) {
				ConversionResource resource = resources.get(resourceName);
				byte[] data = null;
				if (resource.getData() == null) {
					data = getResourceData(resource);
				} else {
					data = resource.getData();
				}
				Asset subAsset = new Asset("image/png", data, resourceName);
				assetsList.add(subAsset);

			}
		}
		Document document = new Document(assetsList);
		Conversion conversion = new Conversion(document, outputMimeType);

		com.google.appengine.api.conversion.ConversionService service = ConversionServiceFactory.getConversionService();
		ConversionResult result1 = service.convert(conversion);

		if (result1.success()) {
			// Note: in most cases, we will return data all in one asset,
			// except that we return multiple assets for multi-page images.
			for (Asset asset1 : result1.getOutputDoc().getAssets()) {
				String sheetName = "Students";
				output = asset1.getData();
			}
		} else {
			String message = "Unable to convert from " + srcType.toString() + " to " + destType.toString()
					+ result1.getErrorCode().toString();
			throw new AppException(message);
		}
		return output;
	}
	
	private byte[] getResourceData(ConversionResource resource) {
		String resourceName  = resource.getResourceName();
		
		byte[] data = null;
		try {
			
			data = CommonUtil.getByteArray(new URL(resourceName).openStream());
		} catch (Exception e) {
			InputStream is = null;
			is = GoogleConversionService.class.getClassLoader().getResourceAsStream(resourceName);
				data = CommonUtil.getByteArray(is);
			
		}
		return data;
		
	}

	

	@Override
	public List<byte[]> convert(ConversionType srcType, ConversionType destType, List<byte[]> inputList,
			Map<String, ConversionResource> resources) throws AppException {
		List<byte[]> ouputData = new ArrayList<byte[]>();
		for (byte[] data : inputList) {
			byte[] output = this.convert(srcType, destType, data, null);
			ouputData.add(output);
		}
		return ouputData;
	}

	@Override
	public Future<byte[]> convertAsync(ConversionType srcType, ConversionType destType, byte[] input,
			Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<byte[]>> convertAsync(ConversionType srcType, ConversionType destType, List<byte[]> input,
			Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

}
