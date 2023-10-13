package LoLiConAPI;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class setu {
    private String  title, author, ext;
    private String[] tags;
    private boolean r18;
    private int pid, uid, width, height, p, uploadDate, aiType;
    private JsonObject urls;

    public String getOriginalUrl(){
        return urls != null ? this.urls.get("original").getAsString():"";
    }
}
