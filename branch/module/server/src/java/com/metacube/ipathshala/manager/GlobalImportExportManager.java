package com.metacube.ipathshala.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.service.GlobalImportExportService;
import com.metacube.ipathshala.vo.ImportVO;

@Component
public class GlobalImportExportManager {
	
	@Autowired
	private GlobalImportExportService globalImportExportService;
	
	
	public void importFile(ImportVO filevo) throws AppException {
		globalImportExportService.importFile(filevo);	
	}

}
