package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService;

import android.content.Context;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;

/**
 * Created by georg on 16/11/2017.
 */

public class DataService implements IDataService {
    private static final String SERVER_IP = "10.0.2.2";
    //Virtual devices connect to local host through 10.0.2.2
    private static final String API_URL = "http://10.0.2.2:8000/api/v1/";
    private static final String GET_ALL = "GET/Vragen";
    private static final String GET_RANDOM = "GET/RandomVragen/";

    private RequestQueue queue;

    public DataService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    @Override
    public void getRandomQuestions(final Response.Listener<List<Quiz>> listener, final Response.ErrorListener errorListener, int amount, int category) {


        createRequestAndAddToQueue(listener, errorListener, API_URL+GET_RANDOM+category+"/"+ amount);
    }

    private void createRequestAndAddToQueue(final Response.Listener<List<Quiz>> listener,
                                            final Response.ErrorListener errorListener,
                                            String url) {
        createRequestAndAddToQueue(listener, errorListener, url, Request.Method.GET, null);
    }

    private void createRequestAndAddToQueue(final Response.Listener<List<Quiz>> listener,
                                            final Response.ErrorListener errorListener,
                                            String url, int method, Map<String, Object> data) {
        // create response listener that handles the response and notifies listener
        final Response.Listener<String> originalResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // convert response to sheet music and notify listener

                    Type collectionType = new TypeToken<List<Quiz>>() {
                    }.getType();
                    List<Quiz> boxSearchCollection = new Gson().fromJson(response, collectionType);

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

        // add request to queue
        queue.add(request);
    }

}
