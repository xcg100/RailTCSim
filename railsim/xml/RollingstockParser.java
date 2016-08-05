/*
 * File:           RollingstockParser.java
 * Date:           7. September 2007  13:14
 *
 * @author  js
 * @version generated by NetBeans XML module
 */
/*
 * File:           RollingstockParser.java
 * Date:           12. August 2007  21:53
 *
 * @author  js
 * @version generated by NetBeans XML module
 */
package org.railsim.xml;

import org.xml.sax.*;

/**
 *
 * The class reads XML documents according to specified DTD and
 * translates all related events into RollingstockHandler events.
 * <p>Usage sample:
 * <pre>
 *    RollingstockParser parser = new RollingstockParser(...);
 *    parser.parse(new InputSource("..."));
 * </pre>
 * <p><b>Warning:</b> the class is machine generated. DO NOT MODIFY</p>
 */
public class RollingstockParser implements ContentHandler {

	private java.lang.StringBuffer buffer;
	private RollingstockHandler handler;
	private java.util.Stack context;
	private EntityResolver resolver;

	/**
	 *
	 * Creates a parser instance.
	 *
	 * @param handler handler interface implementation (never <code>null</code>
	 * @param resolver SAX entity resolver implementation or <code>null</code>.
	 * It is recommended that it could be able to resolve at least the DTD.
	 */
	public RollingstockParser(final RollingstockHandler handler, final EntityResolver resolver) {
		this.handler = handler;
		this.resolver = resolver;
		buffer = new StringBuffer(111);
		context = new java.util.Stack();
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void setDocumentLocator(Locator locator) {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void startDocument() throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void endDocument() throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void startElement(java.lang.String ns, java.lang.String name, java.lang.String qname, Attributes attrs) throws SAXException {
		dispatch(true);
		context.push(new Object[]{
					qname, new org.xml.sax.helpers.AttributesImpl(attrs)
				});
		switch (qname) {
			case "rollingstock":
				handler.start_rollingstock(attrs);
				break;
			case "appearance":
				handler.handle_appearance(attrs);
				break;
			case "weight":
				handler.handle_weight(attrs);
				break;
			case "measure":
				handler.handle_measure(attrs);
				break;
			case "engine":
				handler.handle_engine(attrs);
				break;
			case "speed":
				handler.handle_speed(attrs);
				break;
			case "coupling":
				handler.handle_coupling(attrs);
				break;
			case "type":
				handler.handle_type(attrs);
				break;
		}
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void endElement(java.lang.String ns, java.lang.String name, java.lang.String qname) throws SAXException {
		dispatch(false);
		context.pop();
		if ("rollingstock".equals(qname)) {
			handler.end_rollingstock();
		}
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void characters(char[] chars, int start, int len) throws SAXException {
		buffer.append(chars, start, len);
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void ignorableWhitespace(char[] chars, int start, int len) throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void processingInstruction(java.lang.String target, java.lang.String data) throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void startPrefixMapping(final java.lang.String prefix, final java.lang.String uri) throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void endPrefixMapping(final java.lang.String prefix) throws SAXException {
	}

	/**
	 *
	 * This SAX interface method is implemented by the parser.
	 */
	@Override
	public final void skippedEntity(java.lang.String name) throws SAXException {
	}

	private void dispatch(final boolean fireOnlyIfMixed) throws SAXException {
		if (fireOnlyIfMixed && buffer.length() == 0) {
			return; //skip it
		}
		Object[] ctx = (Object[]) context.peek();
		String here = (String) ctx[0];
		Attributes attrs = (Attributes) ctx[1];
		if ("drawappearance".equals(here)) {
			if (fireOnlyIfMixed) {
				throw new IllegalStateException("Unexpected characters() event! (Missing DTD?)");
			}
			handler.handle_drawappearance(buffer.length() == 0 ? null : buffer.toString(), attrs);
		} else {
			//do not care
		}
		buffer.delete(0, buffer.length());
	}

	/**
	 *
	 * The recognizer entry method taking an InputSource.
	 *
	 * @param input InputSource to be parsed.
	 * @throws java.io.IOException on I/O error.
	 * @throws SAXException propagated exception thrown by a DocumentHandler.
	 * @throws javax.xml.parsers.ParserConfigurationException a parser satisfining requested configuration can not be created.
	 * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation can not be instantiated.
	 */
	public void parse(final InputSource input) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		parse(input, this);
	}

	/**
	 *
	 * The recognizer entry method taking a URL.
	 *
	 * @param url URL source to be parsed.
	 * @throws java.io.IOException on I/O error.
	 * @throws SAXException propagated exception thrown by a DocumentHandler.
	 * @throws javax.xml.parsers.ParserConfigurationException a parser satisfining requested configuration can not be created.
	 * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation can not be instantiated.
	 */
	public void parse(final java.net.URL url) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		parse(new InputSource(url.toExternalForm()), this);
	}

	/**
	 *
	 * The recognizer entry method taking an Inputsource.
	 *
	 * @param input InputSource to be parsed.
	 * @throws java.io.IOException on I/O error.
	 * @throws SAXException propagated exception thrown by a DocumentHandler.
	 * @throws javax.xml.parsers.ParserConfigurationException a parser satisfining requested configuration can not be created.
	 * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation can not be instantiated.
	 */
	public static void parse(final InputSource input, final RollingstockHandler handler) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		parse(input, new RollingstockParser(handler, null));
	}

	/**
	 *
	 * The recognizer entry method taking a URL.
	 *
	 * @param url URL source to be parsed.
	 * @throws java.io.IOException on I/O error.
	 * @throws SAXException propagated exception thrown by a DocumentHandler.
	 * @throws javax.xml.parsers.ParserConfigurationException a parser satisfining requested configuration can not be created.
	 * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation can not be instantiated.
	 */
	public static void parse(final java.net.URL url, final RollingstockHandler handler) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		parse(new InputSource(url.toExternalForm()), handler);
	}

	private static void parse(final InputSource input, final RollingstockParser recognizer) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		javax.xml.parsers.SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
		factory.setValidating(false);  //the code was generated according DTD
		factory.setNamespaceAware(false);  //the code was generated according DTD
		XMLReader parser = factory.newSAXParser().getXMLReader();
		parser.setContentHandler(recognizer);
		parser.setErrorHandler(recognizer.getDefaultErrorHandler());
		if (recognizer.resolver != null) {
			parser.setEntityResolver(recognizer.resolver);
		}
		parser.parse(input);
	}

	/**
	 *
	 * Creates default error handler used by this parser.
	 *
	 * @return org.xml.sax.ErrorHandler implementation
	 */
	protected ErrorHandler getDefaultErrorHandler() {
		return new ErrorHandler() {
			@Override
			public void error(SAXParseException ex) throws SAXException {
				if (context.isEmpty()) {
					System.err.println("Missing DOCTYPE.");
				}
				throw ex;
			}

			@Override
			public void fatalError(SAXParseException ex) throws SAXException {
				throw ex;
			}

			@Override
			public void warning(SAXParseException ex) throws SAXException {
				// ignore
			}
		};

	}
}
