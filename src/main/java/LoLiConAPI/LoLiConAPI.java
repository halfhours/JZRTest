package LoLiConAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class LoLiConAPI {
    public LoLiConAPI(){}

    public String getimageUrl(String ... args) {
        HttpClient client = HttpClient.newHttpClient();
        JsonObject json = new JsonObject();
        json.addProperty("r18", 0);
        json.addProperty("num", 1);
        json.addProperty("excludeAI", true);
        if(args.length != 0) {
            JsonArray tag = new JsonArray();
            for (String value : args) {
                tag.add(value);
            }
            json.add("tag", tag);
        }
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://api.lolicon.app/setu/v2"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        JsonObject result = null;
        if (res != null) {
            result = gson.fromJson(res.body(), JsonObject.class);
            JsonObject rs1 = result.getAsJsonArray("data").get(0).getAsJsonObject();
            JsonObject rs2 = rs1.getAsJsonObject("urls");
            return rs2.get("original").getAsString();
        }
        return null;
    }

    public static void main(String[] args) {
        LoLiConAPI LoLiConAPI = new LoLiConAPI();
        String Path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        String target = LoLiConAPI.getimageUrl("少女","黑丝");
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
