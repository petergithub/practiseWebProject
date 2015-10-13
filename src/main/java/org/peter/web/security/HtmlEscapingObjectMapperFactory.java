package org.peter.web.security;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author http://stackoverflow.com/questions/25403676/initbinder-with-requestbody-escaping-xss-in-spring-3-2-4
 * @version Date：Sep 23, 2015 1:34:34 PM
 */
public class HtmlEscapingObjectMapperFactory implements FactoryBean<ObjectMapper> {

	private final ObjectMapper objectMapper;

	public HtmlEscapingObjectMapperFactory() {
		objectMapper = new ObjectMapper();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
	}

	@Override
	public ObjectMapper getObject() throws Exception {
		return objectMapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public static class HTMLCharacterEscapes extends CharacterEscapes {
		private static final long serialVersionUID = -5743602876943129724L;
		private final int[] asciiEscapes;

		public HTMLCharacterEscapes() {
			// start with set of characters known to require escaping
			// (double-quote, backslash etc)
			asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
			// and force escaping of a few others:
			asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
		}

		@Override
		public int[] getEscapeCodesForAscii() {
			return asciiEscapes;
		}

		// and this for others; we don't need anything special here
		@Override
		public SerializableString getEscapeSequence(int ch) {
			return new SerializedString(
					StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));

		}
	}
}