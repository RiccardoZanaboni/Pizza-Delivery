package pizzeria;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Browse implements Runnable {
	public void run(){
		String uri = "https://drive.google.com/open?id=1IywtXGVTaywaYirjZSVLLV3KDOI1bBx-";
		try {
			Desktop.getDesktop().browse(new URI(uri));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
