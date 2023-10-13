package LoLiConAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class LoLiConAPI {
    private setu[] setu;
    private Error error;
    private Properties properties = new Properties();

    private static final Logger logger = Logger.getLogger(String.valueOf(LoLiConAPI.class));

    public LoLiConAPI(){}

    /**
     *
     * @param num 图片数量[1,20]
     * @param r18Flag 是否为r18图片
     * @param args 图片相关的标签
     */
    public LoLiConAPI(int num, boolean r18Flag, String ... args){
        HttpResponse<String> res = contactAPI(getInputJson(num ,r18Flag, args));
        
        if(res != null){
            collectData(res);
        }
    }

    /**
     * 获取一张随机已查询图片的url
     * @return 图片url或“”
     */
    public String getRandomUrl(){
        if(this.setu.length > 0){
            int index = new Random().nextInt(this.setu.length);
            return this.setu[index].getOriginalUrl();
        }else {
            return "";
        }
    }

    public String getimageUrl(boolean r18Flag, String ... args) {
        this.clean();
        HttpResponse<String> res = contactAPI(getInputJson(1 ,r18Flag, args));

        if (res != null) {
            if(collectData(res)) {
                return this.getRandomUrl();
            }
        }
        return null;
    }

    private JsonObject getInputJson(int num, boolean r18Flag, String ... args){
        JsonObject json = new JsonObject();
        json.addProperty("r18", r18Flag?1:0);
        json.addProperty("num", num);
        json.addProperty("excludeAI", true);
        if(args.length != 0) {
            JsonArray tag = new JsonArray();
            for (String value : args) {
                tag.add(value);
            }
            json.add("tag", tag);
        }
        return json;
    }

    private HttpResponse<String> contactAPI(JsonObject json){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(properties.getProperty("loliconAPI")))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING,e.toString());
        }
        return res;
    }

    private boolean collectData(HttpResponse<String> res){
        try {
            Gson gson = new Gson();
            JsonArray result = gson.fromJson(res.body(), JsonObject.class).getAsJsonArray("data");
            setu = new setu[result.size()];
            for (int i = 0; i < result.size(); i++) {
                JsonObject setuData = result.get(i).getAsJsonObject();
                setu[i] = gson.fromJson(setuData, setu.class);
            }
            return true;
        }catch (Exception e){
            logger.warning("收集数据错误"+ e);
            return false;
        }
    }

    public void clean(){
        this.setu = null;
        this.error = null;
    }

    public static void main(String[] args) {
        LoLiConAPI LoLiConAPI = new LoLiConAPI(3, true, "少女","黑丝");
        String Path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        String target = LoLiConAPI.getimageUrl(false, "少女","黑丝");
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
            logger.log(Level.WARNING,e.toString());
        }
    }

    {
        try {
            properties.load(new FileInputStream("src/main/resources/MyConfig.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
