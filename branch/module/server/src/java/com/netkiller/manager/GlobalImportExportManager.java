package com.netkiller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.core.AppException;
import com.netkiller.service.GlobalImportExportService;
import com.netkiller.vo.ImportVO;

@Component
public class GlobalImportExportManager {
	
	@Autowired
	private GlobalImportExportService globalImportExportService;
	
	
	public void importFile(ImportVO filevo) throws AppException {
		globalImportExportService.importFile(filevo);	
	}

}
