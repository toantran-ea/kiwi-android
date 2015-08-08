package mobi.kiwi.kiwi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by toan on 8/8/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckinResponse {
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
