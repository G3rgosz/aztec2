

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class App {
	public static void main(String[] args) throws WriterException, IOException, NotFoundException{

		String data = "teszt22222222";
		String path = "aztec.png";
		String charset = "UTF-8";

		Map<EncodeHintType, Object> hashMap = new HashMap<>(2);

		hashMap.put(EncodeHintType.AZTEC_LAYERS, 1);

		createAZTEC(data, path, charset, hashMap, 925, 925);
		System.out.println("Aztec-kód létrehozva! ;) ");
		createPdf();

	}

    public static void createAZTEC(String data, String path, String charset, Map hashMap,int height, int width) throws WriterException, IOException{
        BitMatrix matrix = new MultiFormatWriter().encode( new String(data.getBytes(charset), charset), BarcodeFormat.AZTEC, width, height, hashMap);

        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
	
	public static void createPdf()throws FileNotFoundException, MalformedURLException {
		Document document = new Document(new Rectangle(1000,1000));
		String input = "aztec.png";
		String output = "aztec.pdf";
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
