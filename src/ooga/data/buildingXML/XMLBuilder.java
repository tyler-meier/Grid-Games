package ooga.data.buildingXML;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class which contains basic element needed to build an XML document.
 * This is instantiated differently depending on what kind of
 * document you want to make
 */

public abstract class XMLBuilder {

  private DocumentBuilderFactory documentFactory;
  private DocumentBuilder documentBuilder;
  protected Document document;
  protected final String DELIMINATOR = "::";

  public XMLBuilder(String mainTag, String pathName)
  {
    buildFile();
  }

  private void buildFile() {
    try {
      documentFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentFactory.newDocumentBuilder();
      document = documentBuilder.newDocument();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }
  /**
   * Builds the root element to add new element to and eventually transform
   */
  protected void createDocument(String element, String pathName){
    // root element
    Element root = document.createElement(element);
    document.appendChild(root);
    addElementsToRoot(root);
    transform(pathName);
  }


  /**
   * Transformer allows us to write the DOM object to the desired output StreamResult
   */
  protected void transform(String pathName) {

    try{
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);

      StreamResult streamResult = new StreamResult(new File(pathName));

      transformer.transform(domSource, streamResult);

    }
    catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Allows us to write things to the XMl in different ways depending
   * on the type of document you want to write
   * @param root
   */
  abstract void addElementsToRoot(Element root);

  /**
   * Creates a single element from a given tag name and text
   * @param tagName
   * @param text
   * @return
   */
  protected Element createElement(String tagName, String text)
  {
    Element e = document.createElement(tagName);
    e.appendChild(document.createTextNode(text));
    return e;
  }

  }