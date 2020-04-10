package au.edu.unsw.infs3634.inspirationalquotes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuoteService {

    @GET("random")
    Call<QuoteResponse> getQuote(@Query("category") String category);

}
