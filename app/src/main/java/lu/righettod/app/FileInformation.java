package lu.righettod.app;

import java.util.Base64;

public class FileInformation {

    private String name;

    private String base64EncodedContent;

    public String getBase64EncodedContent() {
        return base64EncodedContent;
    }

    public void setBase64EncodedContent(String base64EncodedContent) {
        this.base64EncodedContent = base64EncodedContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDecodedContent() {
        return Base64.getDecoder().decode(this.base64EncodedContent);
    }
}
