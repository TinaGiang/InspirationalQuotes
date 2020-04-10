package au.edu.unsw.infs3634.inspirationalquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        //Calling setQuote() method when user clicks on the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                setQuote();
            }
        });
    }

    public void setQuote() {

        //Creating Retrofit instance, parsing the retrieved Json and calling the object
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.chucknorris.io/jokes/").addConverterFactory(GsonConverterFactory.create()).build();
        QuoteService service = retrofit.create(QuoteService.class);
        Call<QuoteResponse> quoteCall = service.getQuote("dev");

        //Executing the network request
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
