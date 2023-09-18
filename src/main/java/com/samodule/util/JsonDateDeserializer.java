package com.samodule.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Used to serialize Java.util.Date, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Date>{
	static final Logger log = Logger.getLogger(JsonDateDeserializer.class.getName());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");

	@Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

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