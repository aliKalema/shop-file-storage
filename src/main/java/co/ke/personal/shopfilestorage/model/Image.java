package co.ke.personal.shopfilestorage.model;

import io.imagekit.sdk.models.results.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String fileId;
    private String name;
    private String url;
    private boolean isPrivateFile;
    private List<String> tags;
    public Image(Result result){
        Image image = new Image();
        image.name = result.getName();
        image.url= result.getUrl();
        image.fileId = result.getFileId();
        image.isPrivateFile = result.isPrivateFile();
        image.tags = result.getTags();
    }

}
