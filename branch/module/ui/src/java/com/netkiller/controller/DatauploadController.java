package com.netkiller.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.netkiller.UICommonConstants;
import com.netkiller.core.AppException;
import com.netkiller.core.EntityType;
import com.netkiller.manager.GlobalImportExportManager;
import com.netkiller.util.AppLogger;
import com.netkiller.vo.ImportVO;

@Controller
public class DatauploadController extends AbstractController {

	private static final AppLogger log = AppLogger.getLogger(SetController.class);
	
	@Autowired
	private GlobalImportExportManager globalImportExportManager;
	
	/**
	 * Show Data Upload tab.
	 * 
	 * @return
	 */
	@RequestMapping("/dataupload.do")
	public String showDatauploadTab(Model model, HttpServletRequest request) {
		log.debug("Presenting Data Upload view.");
		addToNavigationTrail("Dataupload", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_DATAUPLOAD_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}
	
	@RequestMapping("/dataupload/filelist.do")
	public String createDataFileList(HttpServletRequest request, Model model, @RequestParam("datauploadCreationFile") MultipartFile file)
			throws AppException, JAXBException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			InputStream stream = null;
			try	{
				stream = new ByteArrayInputStream(file.getBytes());
			}
			catch(UnsupportedOperationException e)	{
				String msg = "cannot obtain the stream" ;
				throw new AppException(msg, e) ;
			}
					ImportVO importvo = new ImportVO();
					importvo.setEntityType(EntityType.DATA);
					importvo.setStream(stream);
					
					globalImportExportManager.importFile(importvo);
		} catch (IOException ioexception) {
			String msg = "Error in IO of file";
			log.error(msg);
			throw new AppException(msg, ioexception);
		} catch (AppException ioexception) {
			model.addAttribute(UICommonConstants.ATTRIB_FILE_ERROR,
					UICommonConstants.MSG_FILE_ERROR);
		}
		
		model.addAttribute(UICommonConstants.ATTRIB_DATAUPLOAD_MSG,
				UICommonConstants.MSG_DATA_UPLOAD);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_DATAUPLOAD_INDEX);
		return UICommonConstants.VIEW_INDEX;

	}

	
}
