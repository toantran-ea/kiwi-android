package mobi.kiwi.kiwi.rest;

import mobi.kiwi.kiwi.models.CheckinRequest;
import mobi.kiwi.kiwi.models.CheckinResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by toan on 8/8/15.
 */
public interface KiwiService {
    @POST("/api/checkin")
    void checkin(@Body CheckinRequest request, Callback<CheckinResponse> callback);

    @GET("/api/checkin")
    void verify(Callback<CheckinResponse> callback);
}
