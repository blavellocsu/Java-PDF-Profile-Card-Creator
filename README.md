
# Java PDF Profile Card Creator

This Java application provides a graphical user interface (GUI) that enables users to input personal information and generate a PDF profile card based on the entered data.

## Features

- **User-Friendly Interface**: The application utilizes `JFrame` to create a straightforward GUI for data entry.
- **Image Selection**: Users can select a photo using `JFileChooser`, which will be included in the profile card.
- **PDF Generation**: Employs the Apache PDFBox library to create a PDF document containing the provided information and selected image.
- **Error Handling**: Incorporates `JOptionPane` dialogs to notify users of errors or successful operations.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure that JDK 8 or higher is installed on your system.
- **Apache PDFBox Library**: Download the PDFBox library from the [official website](https://pdfbox.apache.org/) and include it in your project's classpath.

## Setup and Usage

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/brandonlavello/Java-PDF-Profile-Card-Creator.git
   ```

2. **Add PDFBox to Classpath**:

   Download the PDFBox library and add the `pdfbox-app-x.y.z.jar` file to your project's classpath.
   
3. **Compile & Run the Application using Eclipse or other method**:

   ```bash
   java -cp .:pdfbox-app-x.y.z.jar ProfileCardApp
   ```

4. **Generate a Profile Card**:
   - Enter the required personal information into the GUI fields.
   - Use the "Select Photo" button to choose an image file.
   - Click the "Submit" button to generate the PDF profile card.
   - The generated PDF will be saved in the `cards` directory within the current working directory, named according to the provided identifier (e.g., `12345.pdf`).

## Dependencies

- **Apache PDFBox**: An open-source Java library for working with PDF documents. ([pdfbox.apache.org](https://pdfbox.apache.org/))

## License

This project is licensed under the MIT License.

## Acknowledgments

This application was developed by Brandon Lavello as a practical implementation of Java GUI development and PDF generation using the Apache PDFBox library.
