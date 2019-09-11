# Java-PDF-Profile-Card-Creator
A Simple GUI Java App that generates a PDF Profile Card based on the entered information

### Background about the app / Inspiration:
I work for a non-profit organization, Promise Child, that sponsors children all over the world.  Each donor that sponsors a child gets a profile card for that child.  Currently, we have a inefficient process for creating these cards— it involves duplicating files through the Save As function, and it is hard to manage 1,000+ profile cards in such a manner.


## How it works:
I decided to create a GUI app that allows for the user to input the data about the child, select the picture, then generate a PDF profile card based on this information.

For the GUI class, I extend JFrame and implement the ActionListener to gather the data.  I use a JFileChooser to get a String of the photo filepath for the child's photo.

I used JOptionPane’s to alert the user of errors / successes.

For the PDFCreator class, I used an Apache Open Source Library called PDFBox.  This allowed me to generate PDF’s.

Upon the user pressing "Submit" in the JFrame, the program checks for valid input. Assuming the input is valid, the program calls the constructor of PDFCreator.  This will create a directory in the current working directory titled /cards/, if the directory does not already exist.  Then, it will build the PDF and save it to that folder using the Promise Child Number as the name and append ".pdf" to the end. 

Example: '/ProfileCardApp/cards/1234.pdf'

*NOTE* There are 2 pdf images I used to create the PDF’s— as the apps current state, the program requires the pdf’s to be in same main folder as the program.  These images are included in a folder called "Images Needed" in the zip.  They are "back_background.pdf", and "pc_background.pdf".  
