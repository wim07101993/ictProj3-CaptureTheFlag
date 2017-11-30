package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService;

import com.android.volley.Response;

import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;

/**
 * Created by georg on 16/11/2017.
 */
@Deprecated
public interface IDataService {
    void getRandomQuestions(final Response.Listener<List<Quiz>> listener, final Response.ErrorListener errorListener, int amount, int category);

}
