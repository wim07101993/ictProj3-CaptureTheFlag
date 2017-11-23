package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService;

import com.android.volley.Response;

import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz1;

/**
 * Created by georg on 16/11/2017.
 */

public interface TestIDataService {
    void getRandomQuestions(final Response.Listener<List<Quiz1>> listener, final Response.ErrorListener errorListener, int amount, int category);

}
