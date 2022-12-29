package postTest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class SendPost {
    public SendPost(){}

    public String PostSendWay() {
        HttpClient client = HttpClient.newHttpClient();
        JsonObject json = new JsonObject();
        json.addProperty("r18", 0);
        json.addProperty("num", 1);
        json.addProperty("excludeAI", true);
        JsonArray tag = new JsonArray();
        tag.add("萝莉|少女");
        tag.add("白丝|黑丝");
        json.add("tag", tag);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://api.lolicon.app/setu/v2"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        JsonObject result =  gson.fromJson(res.body(),JsonObject.class);
        JsonObject rs1 = result.getAsJsonArray("data").get(0).getAsJsonObject();
        JsonObject rs2 = rs1.getAsJsonObject("urls");

        return rs2.get("original").toString();
    }

    public static void main(String[] args) {
        SendPost sendPost = new SendPost();
        String Path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        String target = sendPost.PostSendWay();
        System.out.println(target);
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add(Path);
        cmd.add("--start-maximized");
        cmd.add("--incognito");
        cmd.add(target);

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
