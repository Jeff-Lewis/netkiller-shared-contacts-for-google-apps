package com.netkiller.service.template.impl;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.netkiller.core.AppException;
import com.netkiller.service.template.TemplateService;
import com.netkiller.util.AppLogger;

public class TemplateServiceImpl implements TemplateService{
	private static final AppLogger log = AppLogger.getLogger(TemplateServiceImpl.class);
	private VelocityEngine velocityEngine;
	private VelocityContext ctx ;

	/**
	 * @param velocityEngine
	 *            the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
		ctx = new VelocityContext();
		
	}

	@Override
	public String generateHTML(String templateName, String path,
			Map<String, Object> data) {
	
		data.put("dateTool", new DateTool());
		data.put("math", new MathTool());
		String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TemplateUtil.getQualifiedName(templateName,path), data);
		return html;
	}
	
	public String generateHTML(byte[] byteArr, Map<String, Object> modalMap) throws AppException{
		modalMap.put("dateTool", new DateTool());
		modalMap.put("math", new MathTool());
		
		for(String str : modalMap.keySet()){
			ctx.put(str, modalMap.get(str));
		}
				
		String velocityFileData = new String(byteArr);
		StringWriter writer = new StringWriter();
		
		boolean isSuccess = Velocity.evaluate(ctx, writer, "myTemplate", velocityFileData);
		
		if(isSuccess){
			String html =  writer.toString();
			return html;
		}else{
			throw new AppException("Cannot convert template to html ");
		}
		
	}

}
