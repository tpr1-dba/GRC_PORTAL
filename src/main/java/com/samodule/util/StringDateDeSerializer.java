package com.samodule.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class StringDateDeSerializer extends JsonDeserializer<Date> {

	static final Log log = LogFactory.getLog(StringDateDeSerializer.class.getName());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
			throws IOException, JsonProcessingException {

		log.info(jsonparser);
		String date = jsonparser.getText();
		log.info(date);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("ERROR", e);
			throw new RuntimeException(e);
		}

	}

}