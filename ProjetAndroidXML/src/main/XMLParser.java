package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import constants.Vote;
import constants.XMLConstants;

public class XMLParser {

	public static Document loadXMLDocument(String xmlPath) {
		try {
			return new SAXBuilder().build(new File(xmlPath));
		} catch (JDOMException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
		return null;
	}

	public static Comment createcommentFromElement(Element element) {
		return new Comment(
				element.getAttributeValue(XMLConstants.ID_ATTRIBUTE_COMMENT),
				element.getChildText(XMLConstants.AUTOR_COMMENT),
				element.getChildText(XMLConstants.CONTENTS_COMMENT),
				element.getChildText(XMLConstants.DATE_COMMENT));
	}

	public static Anecdote createAnecdoteFromElement(Element element) {
		if (element != null) {
			ArrayList<String> sources = new ArrayList<String>();
			if (element.getChild(XMLConstants.SOURCES_ANECDOTE) != null) {
				List<Element> listSources = element.getChild(XMLConstants.SOURCES_ANECDOTE)
						.getChildren(XMLConstants.SOURCE_ANECDOTE);
				Iterator<Element> it = listSources.iterator();
				while (it.hasNext()) {
					sources.add(it.next().getValue());
				}
			}
			ArrayList<Comment> comments = new ArrayList<Comment>();
			if (element.getChild(XMLConstants.COMMENTS_ANECDOTE) != null) {
				List<Element> listcomments = element.getChild(XMLConstants.COMMENTS_ANECDOTE)
						.getChildren(XMLConstants.COMMENT_ANECDOTE);
				Iterator<Element> it = listcomments.iterator();
				while (it.hasNext()) {
					comments.add(createcommentFromElement(it.next()));
				}
			}
			String id = element
					.getAttributeValue(XMLConstants.ID_ATTRIBUTE_ANECDOTE);
			String autor = element.getChildText(XMLConstants.AUTOR_ANECDOTE);
			String title = element.getChildText(XMLConstants.TITLE_ANECDOTE);
			String category = element
					.getChildText(XMLConstants.CATEGORY_ANECDOTE);
			String contents = element
					.getChildText(XMLConstants.CONTENTS_ANECDOTE);
			String date = element.getChildText(XMLConstants.DATE_ANECDOTE);
			int ididntknowvote = Integer.valueOf(
					element.getChildText(XMLConstants.IDIDNTKNOWVOTE_ANECDOTE))
					.intValue();
			int iknewvote = Integer.valueOf(
					element.getChildText(XMLConstants.IKNEWVOTE_ANECDOTE))
					.intValue();
			String videoid = element
					.getChildText(XMLConstants.VIDEOID_ANECDOTE);
			String imagelink = element
					.getChildText(XMLConstants.IMAGELINK_ANECDOTE);
			return new Anecdote(id, autor, title, category, contents, date,
					ididntknowvote, iknewvote, videoid, imagelink, sources,
					comments);
		} else
			return null;
	}

	public static ArrayList<Anecdote> parseXML(Document document,
			String stringFilterCategory) {
		Element root = document.getRootElement();
		ArrayList<Anecdote> resultArray = new ArrayList<Anecdote>();
		List<Element> listAnecodote = root
				.getChildren(XMLConstants.ANECDOTE_ANECDOTE);
		Iterator<Element> it = listAnecodote.iterator();
		while (it.hasNext()) {
			Element element = it.next();
			boolean isRightCategory = stringFilterCategory.equals("") ? true
					: element.getChildText(XMLConstants.CATEGORY_ANECDOTE)
							.equals(stringFilterCategory);
			if (isRightCategory)
				resultArray.add(createAnecdoteFromElement(element));
		}
		return resultArray;
	}

	public static void displayAnecdotes(ArrayList<Anecdote> anecdoteArray) {
		for (Anecdote a : anecdoteArray) {
			System.out.println(a.toString());
		}
	}
	public static void postVote(Document document,int anecdoteId, Vote votecase){
		String xmlVoteCase=votecase==Vote.IDIDNTKNOW?XMLConstants.IDIDNTKNOWVOTE_ANECDOTE:XMLConstants.IKNEWVOTE_ANECDOTE;
		
		Element root = document.getRootElement();
		List<Element> listAnecodote = root
				.getChildren(XMLConstants.ANECDOTE_ANECDOTE);
		Iterator<Element> it = listAnecodote.iterator();
		boolean find = false;
		while (it.hasNext() && find == false) {
			Element element = it.next();
			if (element.getAttributeValue(XMLConstants.ID_ATTRIBUTE_ANECDOTE)
					.equals(String.valueOf(anecdoteId))) {
				int val = Integer.valueOf(
						element.getChildText(xmlVoteCase))
						.intValue() + 1;
				element.getChild(xmlVoteCase).setText(
						String.valueOf(val));
			}
		}
		writeXML(document);
	}

	public static void iKnewIt(Document document, int anecdoteId) {
		postVote(document,anecdoteId,Vote.IKNEW);
	}
	public static void iDidntKnowIt(Document document, int anecdoteId) {
		postVote(document,anecdoteId,Vote.IDIDNTKNOW);

	}
	public static void postComment(Document document, int anecdoteId,String autor,String contents){
		{
			Element root = document.getRootElement();
			List<Element> listAnecodote = root
					.getChildren(XMLConstants.ANECDOTE_ANECDOTE);
			Iterator<Element> it = listAnecodote.iterator();
			boolean find = false;
			while (it.hasNext() && find == false) {
				Element element = it.next();
				if (element.getAttributeValue(XMLConstants.ID_ATTRIBUTE_ANECDOTE)
						.equals(String.valueOf(anecdoteId))) {
					Element elementComments=element.getChild(XMLConstants.COMMENTS_ANECDOTE);
					List<Element> listComment= elementComments.getChildren(XMLConstants.COMMENT_ANECDOTE);
					Iterator<Element> iteratorComment=listComment.iterator();
					int id = countForId(iteratorComment);
					Element newCom = createElementComment(anecdoteId, autor,
							contents, id);
					elementComments.addContent(newCom);
					find=true;
				}
			}
			writeXML(document);

		}
	}

	private static Element createElementComment(int anecdoteId, String autor,
			String contents, int id) {
		Element newCom= new Element(XMLConstants.COMMENT_ANECDOTE);
		newCom.setAttribute(new Attribute(XMLConstants.ID_ATTRIBUTE_COMMENT,anecdoteId+"_"+id));
		newCom.addContent(new Element(XMLConstants.AUTOR_COMMENT).setText(autor));
		newCom.addContent(new Element(XMLConstants.CONTENTS_COMMENT).setText(contents));
		newCom.addContent(new Element(XMLConstants.DATE_COMMENT).setText(createDateXML()));
		return newCom;
	}

	private static String createDateXML() {
		return new SimpleDateFormat("hh:mm dd/MM/yyyy").format(new Date());
	}
	public static void postAnecdote(Document document,String autor,String title,String category,String contents,String videoid,String imagelink,ArrayList<String> sources){
		Element root= document.getRootElement();
		List<Element> listAnecdotes= root.getChildren(XMLConstants.ANECDOTE_ANECDOTE);
		Iterator<Element> it= listAnecdotes.iterator();
		int id = countForId(it);
		Element newAnecdote = createElementAnecdote(autor, title, category,
				contents, videoid, imagelink, sources, id);
		root.addContent(newAnecdote);
		writeXML(document);	
	}

	private static Element createElementAnecdote(String autor, String title,
			String category, String contents, String videoid, String imagelink,
			ArrayList<String> sources, int id) {
		Element newAnecdote= new Element(XMLConstants.ANECDOTE_ANECDOTE);
		newAnecdote.setAttribute(new Attribute(XMLConstants.ID_ATTRIBUTE_ANECDOTE, String.valueOf(id)));
		newAnecdote.addContent(new Element(XMLConstants.AUTOR_ANECDOTE).setText(autor));
		newAnecdote.addContent(new Element(XMLConstants.TITLE_ANECDOTE).setText(title));
		newAnecdote.addContent(new Element(XMLConstants.CATEGORY_ANECDOTE).setText(category));
		newAnecdote.addContent(new Element(XMLConstants.CONTENTS_ANECDOTE).setText(contents));
		newAnecdote.addContent(new Element(XMLConstants.DATE_ANECDOTE).setText(createDateXML()));
		newAnecdote.addContent(new Element(XMLConstants.IDIDNTKNOWVOTE_ANECDOTE).setText("0"));
		newAnecdote.addContent(new Element(XMLConstants.IKNEWVOTE_ANECDOTE).setText("0"));
		newAnecdote.addContent(new Element(XMLConstants.VIDEOID_ANECDOTE).setText(videoid));
		newAnecdote.addContent(new Element(XMLConstants.IMAGELINK_ANECDOTE).setText(imagelink));
		Element sourcesElement = new Element(XMLConstants.SOURCES_ANECDOTE);
		for(String s : sources)
			sourcesElement.addContent(new Element(XMLConstants.SOURCE_ANECDOTE).setText(s));
		newAnecdote.addContent(sourcesElement);
		newAnecdote.addContent(new Element(XMLConstants.COMMENTS_ANECDOTE));
		return newAnecdote;
	}

	private static int countForId(Iterator<Element> it) {
		int id =1;
		while(it.hasNext()){
			id++;
			it.next();
		}
		return id;
	}

	
	public static void editAnecdote(Document document, int anecdoteId, String contents){
		Element root = document.getRootElement();
		List<Element> listElements= root.getChildren(XMLConstants.ANECDOTE_ANECDOTE);
		Iterator<Element> it= listElements.iterator();
		boolean find= false;
		while(it.hasNext() && find == false){
			Element element = it.next();
			if (element.getAttributeValue(XMLConstants.ID_ATTRIBUTE_ANECDOTE)
					.equals(String.valueOf(anecdoteId))) 
				element.getChild(XMLConstants.CONTENTS_ANECDOTE).setText(contents);
			
			}
		writeXML(document);
	}

	private static void writeXML(Document document) {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(document, new FileWriter(document.getBaseURI()
					.replaceFirst("file:/", "")));
		} catch (IOException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Document xmlDocument = XMLParser
				.loadXMLDocument("xmlfolder/xmlfile.xml");
		
		XMLParser.postComment(xmlDocument, 5,"Nicolas","j'edite un com2");
		
		
		XMLParser.editAnecdote(xmlDocument, 2, "Anecdote Edite");
		XMLParser.iDidntKnowIt(xmlDocument, 6);
		XMLParser.iKnewIt(xmlDocument, 6);
		ArrayList<Anecdote> anecdoteArray = XMLParser.parseXML(xmlDocument, "");
		XMLParser.displayAnecdotes(anecdoteArray);

	}
}
