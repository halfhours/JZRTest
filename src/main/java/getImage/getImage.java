package getImage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class getImage {

    String url = "";

    public getImage(){
    };

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.bilibili.com/");
        URLConnection con = url.openConnection();
    }
}
