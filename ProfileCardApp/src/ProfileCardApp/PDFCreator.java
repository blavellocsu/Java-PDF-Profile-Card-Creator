package ProfileCardApp;

import static java.lang.System.err;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PDFCreator {
	
	//filename
	String fileName;
	
	//Working Directory
	private String directory = System.getProperty("user.dir");

	//Font stuff
	private static PDFont helveticaFont = PDType1Font.HELVETICA;
	private static int titleSize = 14;
	private static int titleSpacing = 5;
	private static int paragraphSize = 10;
	private static int paragraphSpacing = 0;
    private static final float LEADING = -1.5f * paragraphSize;

	
	//Page Sizes
	private final float WIDTH = 396f;
	private final float HEIGHT = 306f;
	private static float width;
	
	//Child Photo Sizes
	private final int FRONT_IMAGE_WIDTH = 120;
	private final int FRONT_IMAGE_HEIGHT = 75;
	private final int BACK_IMAGE_WIDTH = 350;
	private final int BACK_IMAGE_HEIGHT = 240;
	
	//text coordinates (in points)
	private int [] promiseLocation = {28,272};
	private int [] childLocation = {42,255};
	private int [] countryLocation = {280,272};
	private int [] numberLocation = {115,203};
	private int [] ageLocation = {47,179};
	private int [] gradeLocation = {63,155};
	private int [] bioLocation = {45,131};
	private int [] nameFrontLocation = {289,143};
	private int [] nameBackLocation = {120,05};
	private int [] pictureFrontLocation = {274,154};
	private int [] pictureBackLocation = {22,15};
	
	//Text Strings
	private final String PROMISE = "PROMISE";
	private final String CHILD = "CHILD";
	private String countryUpper;
		
	//Create New Pages
	private PDPage frontPage;
	private PDPage backPage;
	
	//Constructor for PDF, only need one because all fields required
	public PDFCreator (String name, String number, String age, 
			String grade, String country, String bio, String filepath) {
		//create filename
		fileName = "" + number + ".pdf";
		
		//create new pages
		frontPage = new PDPage(new PDRectangle(WIDTH,HEIGHT));
		backPage = new PDPage(new PDRectangle(WIDTH,HEIGHT));
		
		//Convert country text to uppercase before writing text
		countryUpper = country.toUpperCase();
		
		//try / catch, create new PDF
		try (final PDDocument document = new PDDocument())
		   {
			  //add front Page to document
		      document.addPage(frontPage);
		      
		      //get mediaBox for addParagraph text wrap function
		      PDRectangle mediaBox = frontPage.getMediaBox();
	          float marginX = 30;
	          width = mediaBox.getWidth() - 2 * marginX;

		      //create new stream for writing text to Front Page
		      PDPageContentStream contentStreamFront = new PDPageContentStream(document, frontPage);
		      
		      //Create new image for child image from filepath provided in constructor
		      PDImageXObject pdImage = PDImageXObject.createFromFile(filepath, document);
		      
		      //Size and Place image
		      pdImage.setHeight(FRONT_IMAGE_HEIGHT);
		      pdImage.setWidth(FRONT_IMAGE_WIDTH);
		      contentStreamFront.drawImage(pdImage, pictureFrontLocation[0], pictureFrontLocation[1]);
		      
		      //add background image to file
			  addBackground(document, "pc_background.pdf");
			  
			  //begin text stream
		      addTextAtPosition(PROMISE, titleSize, titleSpacing, promiseLocation[0], promiseLocation[1], contentStreamFront);
		      addTextAtPosition(CHILD, titleSize, titleSpacing, childLocation[0], childLocation[1], contentStreamFront);
		      addCountry(countryUpper, countryLocation[0], countryLocation[1], contentStreamFront);
		      addTextAtPosition(number, paragraphSize, paragraphSpacing, numberLocation[0], numberLocation[1], contentStreamFront);
		      addTextAtPosition(age, paragraphSize, paragraphSpacing, ageLocation[0], ageLocation[1], contentStreamFront);
		      addTextAtPosition(grade, paragraphSize, paragraphSpacing, gradeLocation[0], gradeLocation[1], contentStreamFront);
		      addBio(bio, bioLocation[0], bioLocation[1], contentStreamFront);
		      addTextAtPosition(name, paragraphSize, paragraphSpacing, nameFrontLocation[0], nameFrontLocation[1], contentStreamFront);
		      
		      //Stream must be closed
		      contentStreamFront.close();  
		      
		      //begin working on backPage, add backPage to doc, create new stream
		      document.addPage(backPage);
		      PDPageContentStream contentStreamBack = new PDPageContentStream(document, backPage);
		      
		      //create child image, size, place
		      PDImageXObject pdImageBack = PDImageXObject.createFromFile(filepath, document);
		      pdImageBack.setHeight(BACK_IMAGE_HEIGHT);
		      pdImageBack.setWidth(BACK_IMAGE_WIDTH);
		      contentStreamBack.drawImage(pdImageBack, pictureBackLocation[0], pictureBackLocation[1]);
			 
		      //add background
		      addBackground(document,"back_background.pdf");
		      
		      //add name
		      addTextAtPosition(name, paragraphSize, paragraphSpacing, nameBackLocation[0], 
		    		  nameBackLocation[1], contentStreamBack);
		     
		      //Stream must be closed
		      contentStreamBack.close();  

		      //create cards directory if it doesn't exist
		      new File(System.getProperty("user.dir") + "/cards").mkdirs();
		      
		      //save and close file
		      document.save(directory + "/cards/" + fileName);
		      document.close();
		      
		      System.out.println("Your file created in : " + System.getProperty("user.dir") + "/cards");
		      JOptionPane.showMessageDialog(null, "Card # " + number + 
		    		  " successfully created in /cards/", "Success", JOptionPane.INFORMATION_MESSAGE);
		   }
		   catch (IOException e)
		   {
			   //error printing
			JOptionPane.showMessageDialog(null, "Exception while trying to create document\n" + e, 
					"Error", JOptionPane.ERROR_MESSAGE);
		      err.println("Exception while trying to create document\n" + e);
		   }
	}
	
	
	/* 
	 * CREDIT *
	 * 
	 * StackOverflow - https://stackoverflow.com/questions/8929954/watermarking-with-pdfbox
	 * 
	 * Droidman posted this solution to add a background watermark, I utilized his HashMap logic
	 * overlay my background images.
	 * 
	 **/
	//Add Background Image Function
	static void addBackground (PDDocument d, String background) {
        try {
			HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
	        for(int i=0; i<d.getNumberOfPages(); i++){
	            overlayGuide.put(i+1, background);
	        }
	        Overlay overlay = new Overlay();
	        overlay.setInputPDF(d);
	        overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
			overlay.overlay(overlayGuide);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Exception while trying to create document\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			 err.println("Exception while trying to create document\n" + e);
		}
	}//end addBackground
	
	//addTextAtPosition
	//Basic function for adding text, no fancy text wrap
	static public void addTextAtPosition(String text, int size, int spacing, int x, int y, 
			PDPageContentStream stream) {
		try {
			stream.beginText();
			//formatting
		    stream.setFont(PDType1Font.HELVETICA, size);
		    stream.setCharacterSpacing(spacing);
		    //newLineAtOffset determines where text is written
			stream.newLineAtOffset(x, y);
			//write text
			stream.showText("" + text);
		    stream.endText();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Exception while trying to create document\n" + e, 
					"Error", JOptionPane.ERROR_MESSAGE);
			 err.println("Exception while trying to create document\n" + e);
		}
	} //end addTextAtPosition
	
	//addBio
	//adds text wrap paragraph at bio location
	static public void addBio(String text, int x, int y, PDPageContentStream stream) {
		try {
			stream.beginText();
			
			//formatting
			stream.setFont(helveticaFont, paragraphSize);
		    stream.setCharacterSpacing(paragraphSpacing);
		    
		    //call addParagraph text wrap function, pass in data
			addParagraph(stream, width, x, y, text, true);
		    stream.endText();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Exception while trying to create document\n" + e, 
					"Error", JOptionPane.ERROR_MESSAGE);
			 err.println("Exception while trying to create document\n" + e);
		}
	} //end addBio
	
	//addCountry
	//adds text wrap paragraph at country location
	static public void addCountry(String text, int x, int y, PDPageContentStream stream) {
		try {
			stream.beginText();
			
			//formatting
			stream.setFont(helveticaFont, titleSize);
		    stream.setCharacterSpacing(titleSpacing);
		    
		    //call addParagraph text wrap function, pass in data
			addParagraph(stream, width-330, x, y, text, true);
		    stream.endText();
		    
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Exception while trying to create document\n" + e, 
					"Error", JOptionPane.ERROR_MESSAGE);
			 err.println("Exception while trying to create document\n" + e);
		}
	}//end addBio
	
	
	/*
	 *  CREDIT *
	 *  
	 * I modified a Text Wrap function to write my addParagraph and parseLines functions
	 * https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/
	 * 
	 **/
	//addParagraph
	private static void addParagraph(PDPageContentStream contentStream, float width, float sx,
            float sy, String text, boolean justify) throws IOException {
		List<String> lines = parseLines(text, width);
		contentStream.newLineAtOffset(sx, sy);
		for (String line: lines) {
			float charSpacing = 0;
			if (justify){
				if (line.length() > 1) {
					float size = paragraphSize * helveticaFont.getStringWidth(line) / 1000;
					float free = width - size;
					if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
						charSpacing = free / (line.length() - 1);
					}
				}
			}
			contentStream.showText(line);
			contentStream.newLineAtOffset(0,LEADING);
		}
	}//end addParagraph
	
	//parseLines
	private static List<String> parseLines(String text, float width) throws IOException {
		List<String> lines = new ArrayList<String>();
		int lastSpace = -1;
		while (text.length() > 0) {
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = paragraphSize * helveticaFont.getStringWidth(subString) / 1000;
			if (size > width) {
				if (lastSpace < 0){
					lastSpace = spaceIndex;
				}
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == text.length()) {
				lines.add(text);
				text = "";
			} else {
				lastSpace = spaceIndex;
			}
		}
		return lines;
	}//end parseLines
} // end class
