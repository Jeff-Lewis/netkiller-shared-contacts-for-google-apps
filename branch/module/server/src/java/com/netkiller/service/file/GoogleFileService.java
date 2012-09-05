package com.netkiller.service.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.files.GSFileOptions.GSFileOptionsBuilder;
import com.netkiller.core.AppException;
import com.netkiller.util.AppLogger;

@Component("GoogleFileService")
public class GoogleFileService implements FileService {

	private static final AppLogger log = AppLogger.getLogger(GoogleFileService.class);
	
	// Google File Service - Responsible to manage files on Google cloud
	private com.google.appengine.api.files.FileService fileService;

	// Defines settings for permission, scope, file type before creating a
	// channel for file operations
	private GSFileOptionsBuilder optionsBuilder;

	public GoogleFileService() {
		this.fileService = FileServiceFactory.getFileService();
		optionsBuilder = new GSFileOptionsBuilder();
	}

	@Override
	public File createFile(File file) throws AppException{
		setFileOptions(file.getFileInfo());		
		try{
			setFileOptions(file.getFileInfo());
			AppEngineFile writableFile = fileService.createNewGSFile(optionsBuilder.build());			
			
			boolean lockForWrite = true; // Do you want to exclusively lock this object?
			FileWriteChannel writeChannel = fileService.openWriteChannel(writableFile, lockForWrite);

			writeChannel.write(ByteBuffer.wrap(file.getData()));
			
			// Finalize the object
			writeChannel.closeFinally();
			}catch(IOException e){
				throw new AppException("Error eccured..!! ", e);
			}		
		return null;
	}

	@Override
	public File readFile(String bucketName, String fileName) throws AppException {
		File file = null;
		try {
			String filename = "/gs/" + bucketName + "/" + fileName;
			AppEngineFile readableFile = new AppEngineFile(filename);
			boolean lockForWrite = false; 
			FileReadChannel readChannel = fileService.openReadChannel(readableFile, lockForWrite);
			// Again, different standard Java ways of reading from the channel.
			/*
			 * CharsetDecoder decoder = Charset.forName("UTF8").newDecoder();
			 * decoder.onMalformedInput(CodingErrorAction.IGNORE);
			 * BufferedReader reader = new
			 * BufferedReader(Channels.newReader(readChannel,decoder, 93205));
			 */

			BufferedInputStream inputStream = new BufferedInputStream(Channels.newInputStream(readChannel));
			// IOUtils.toByteArray(inputStream);
			/*
			 * StringBuffer dataString = new StringBuffer(); String line = null;
			 */
			/*
			 * while (!StringUtils.isBlank(line = reader.readLine())) {
			 * System.out.println(line); dataString.append(line); }
			 */
			file = new File();
			file.setData(IOUtils.toByteArray(inputStream));
			System.out.println(new String(file.getData()));

			readChannel.close();
		} catch (IOException e) {
			throw new AppException("Error while reading file with name : " + fileName, e);
		}
		return file;
	}

	@Override
	public File updateFile(String fileNme, byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setFileOptions(FileInfo fileInfo) {
		this.optionsBuilder.setAcl(fileInfo.getAcl().toString());
		this.optionsBuilder.setMimeType(fileInfo.getFileType());
		this.optionsBuilder.setBucket(fileInfo.getBucketName());
		this.optionsBuilder.setKey(fileInfo.getFileName());
		this.optionsBuilder.setContentDisposition("attachment;filename=filename.pdf");
		/*
		 * if (fileInfo.getOwner() != null) {
		 * this.optionsBuilder.addUserMetadata("owner", fileInfo.getOwner()); }
		 */
	}

}
