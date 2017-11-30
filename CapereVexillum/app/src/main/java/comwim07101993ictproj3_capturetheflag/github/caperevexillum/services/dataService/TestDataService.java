package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.*;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Answers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.Variables;

/**
 * Created by georg on 16/11/2017.
 */

public class TestDataService implements TestIDataService {

    //Virtual devices connect to local host through 10.0.2.2

    private static final String GET_ALL = "GET/Vragen";
    private static final String GET_RANDOM = "GET/RandomVragen/";

    private RequestQueue queue;

    public TestDataService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    @Override
    public void getRandomQuestions(final Response.Listener<List<Quiz1>> listener, final Response.ErrorListener errorListener, int amount, int category) {


        createRequestAndAddToQueue(listener, errorListener, Variables.SERVER_IP+GET_RANDOM+category+"/"+ amount);
    }

    private void createRequestAndAddToQueue(final Response.Listener<List<Quiz1>> listener,
                                            final Response.ErrorListener errorListener,
                                            String url) {
        createRequestAndAddToQueue(listener, errorListener, url, Request.Method.GET, null);
    }

    private void createRequestAndAddToQueue(final Response.Listener<List<Quiz1>> listener,
                                            final Response.ErrorListener errorListener,
                                            String url, int method, Map<String, Object> data) {
        final Response.Listener<String> originalResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type collectionType = new TypeToken<List<Quiz1>>() {
                    }.getType();
                    Log.d("string",response);
                    List<Quiz1> boxSearchCollection = new Gson().fromJson(response, collectionType);
                    for (Answers a1 : boxSearchCollection.get(0).getAnswers()
                         ) {
                        Log.d("answer", a1.getAnswer());
                    }
                    listener.onResponse(boxSearchCollection);
                } catch (Exception e) {
                    // if an error occurred, notify listener
                    errorListener.onErrorResponse(new VolleyError(e));
                }
            }
        };

        // create new hashmap with all the data as json to send with the request
        final Map<String, String> params = new HashMap<>();
        if (data != null) {
            Gson gson = new Gson();
            for (String key : data.keySet()) {
                params.put(key, gson.toJson(data.get(key)));
            }
        }

        // create new request
        StringRequest request = new StringRequest(method, url,
                originalResponseListener, errorListener) {
            // override the getParams method to pass the params that should be sent with the request

            @Override
            protected Map<String, String> getParams() {
                // return the created params for the request
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // add request to queue
        queue.add(request);
    }


}

