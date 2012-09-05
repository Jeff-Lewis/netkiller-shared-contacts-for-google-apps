package com.netkiller.view.ajax.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

/**
 * Deserializer to map JSON to Bean when JSON data is received from client is sent to server.
 *
 * @author vnarang
 *
 */
final class ConvertingDeserializer extends JsonDeserializer<Object> {

	/**
	 * Conversion service handle
	 */
	private final ConversionService conversionService;

	/**
	 * TypeDescriptor handle defining target data type.
	 */
	private final TypeDescriptor targetType;

	public ConvertingDeserializer(ConversionService conversionService, TypeDescriptor targetType) {
		this.conversionService = conversionService;
		this.targetType = targetType;
	}

	/* (non-Javadoc)
	 * @see org.codehaus.jackson.map.JsonDeserializer#deserialize(org.codehaus.jackson.JsonParser, org.codehaus.jackson.map.DeserializationContext)
	 */
	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Object value = jp.getText();
		TypeDescriptor sourceType = TypeDescriptor.forObject(value);
		return this.conversionService.convert(value, sourceType, targetType);
	}

}
