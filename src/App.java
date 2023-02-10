

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class App {
	public static void main(String[] args) throws WriterException, IOException, NotFoundException{

		String data = "teszt";
		String path = "aztec.png";
		String charset = "UTF-8";

		Map<EncodeHintType, Object> hashMap = new HashMap<>(3);

		//hashMap.put(EncodeHintType.ERROR_CORRECTION,1);
		hashMap.put(EncodeHintType.AZTEC_LAYERS, 1);


		createAZTEC(data, path, charset, hashMap, 1000, 1000);
		System.out.println("Aztec-kód létrehozva! ;) ");
	}

    public static void createAZTEC(String data, String path, String charset, Map hashMap,int height, int width) throws WriterException, IOException{
        BitMatrix matrix = new MultiFormatWriter().encode( new String(data.getBytes(charset), charset), BarcodeFormat.AZTEC, width, height, hashMap);

        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
}
