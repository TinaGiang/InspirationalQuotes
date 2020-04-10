package au.edu.unsw.infs3634.inspirationalquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView quote;
    private Button button;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = findViewById(R.id.quote);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                setQuote();
            }
        });

        setQuote();
    }

    public void setQuote() {

        //create Retrofit instance & parse the retrieved Json using the Gson deserialiser
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.chucknorris.io/jokes/").addConverterFactory(GsonConverterFactory.create()).build();

        //get service & call object for the request
        QuoteService service = retrofit.create(QuoteService.class);
        Call<QuoteResponse> quoteCall = service.getQuote("dev");

        //execute network request
        quoteCall.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                Log.d(TAG, "onResponse: SUCCESS");
                QuoteResponse quotes = response.body();
                quote.setText(quotes.getValue());
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: FAILURE");
            }
        });

    }

}
