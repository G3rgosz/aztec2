
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;

public class App {

	public static void main(String[] args) throws WriterException, IOException, NotFoundException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Beolvasandó txt fájl: ");
		String txt = sc.nextLine();

		System.out.print("Célútvonal: ");
		String url = sc.nextLine();
   
		String charset = "UTF-8";

        try {
            
            FileReader fRead = new FileReader(txt);
            Scanner fsc = new Scanner(fRead);
            while( fsc.hasNext() ) {
                
                String row = fsc.nextLine();
                String[] rowData = row.split("\\t");
				String data = rowData[0];
				String filename = rowData[1];
				String path = url + filename;
				createAZTEC(data, path + ".png", charset, 925, 925);
				System.out.println("Aztec-kód létrehozva! ;) ");
				createPdf(path);
            }
            fsc.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

		// createAZTEC(data, path + ".png", charset, 925, 925);
		// System.out.println("Aztec-kód létrehozva! ;) ");
		// createPdf(path);

	}

    public static void createAZTEC(String data, String path, String charset,int height, int width) throws WriterException, IOException {
		Map<EncodeHintType, Object> hashMap = new HashMap<>(1);
		hashMap.put(EncodeHintType.AZTEC_LAYERS, 1);

        BitMatrix matrix = new MultiFormatWriter().encode( new String(data.getBytes(charset), charset), BarcodeFormat.AZTEC, width, height, hashMap);

        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
	
	public static void createPdf(String path)throws FileNotFoundException, MalformedURLException {
		Document document = new Document(new Rectangle(1000,1000));
		String input = path + ".png";
		String output = path + ".pdf";
		try {
		  FileOutputStream fos = new FileOutputStream(output);
		  PdfWriter writer = PdfWriter.getInstance(document, fos);
		  writer.open();
		  document.open();
		  document.add(Image.getInstance(input));
		  document.close();
		  writer.close();
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	}
}
