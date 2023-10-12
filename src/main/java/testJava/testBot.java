package testJava;

import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import LoLiConAPI.LoLiConAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;

public class testBot {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        HttpClient client = HttpClient.newHttpClient();

        LoLiConAPI loLiConAPI = new LoLiConAPI();
        String imageURL = loLiConAPI.getimageUrl("少女","黑丝");
        URL url = new URL(imageURL);
        URLConnection con = url.openConnection();
        InputStream ip = con.getInputStream();
        byte[] data = ip.readAllBytes();
        String Base64 = java.util.Base64.getEncoder().encodeToString(data);
        String MD5 = DigestUtils.md5Hex(data);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msgtype","image");
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("base64",Base64);
        jsonObject1.addProperty("md5",MD5);
        jsonObject.add("image",jsonObject1);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=194edf91-88df-4f77-bc68-da9e276bfb10"))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();

        HttpResponse<String> res = null;

        try {
            res = client.send(req,HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(res.statusCode());
        System.out.println(res.headers());
    }
}
