package mobi.kiwi.kiwi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by toan on 8/8/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckinRequest {
    private String email;
    private String random;
    private String token;

    public CheckinRequest () {

    }

    public CheckinRequest(String email, String random, String token) {
        setEmail(email);
        setRandom(random);
        setToken(token);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
