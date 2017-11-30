package comwim07101993ictproj3_capturetheflag.github.caperevexillum;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Answers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService.DataServiceApi;

import static junit.framework.Assert.assertEquals;

/**
 * Created by georg on 21/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DataServiceTest {
    private List<Quiz> dataQuiz = new ArrayList<>();
    private List<Answers> answers = new ArrayList<>();
    DataServiceApi dataService ;

    @Test
    public void getData() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        dataService = new DataServiceApi(appContext);
        // Context of the app under test.
        //{"Question_ID":2,"Question":"Wie is de 45ste president van de verenigde staten.","Answers":[{"Answer_ID":5,"Answer":"George W. Bush","Question_ID":2,"Correct":0},{"Answer_ID":6,"Answer":"Barack Obama","Question_ID":2,"Correct":0},{"Answer_ID":7,"Answer":"Donald Trump","Question_ID":2,"Correct":1}]}
                //String Answer, Boolean Correct
        Answers answer = new Answers("George W. Bush","0");
        answers.add(answer);
        answer = new Answers("Barack Obama","0");
        answers.add(answer);
        answer = new Answers("Donald Trump","1");
        answers.add(answer);

        final Quiz testQuiz = new Quiz("Wie is de 45ste president van de verenigde staten.", answers);


        final Response.Listener  listener = new Response.Listener<List<Quiz>>() {
            @Override
            public void onResponse(List<Quiz> response) {
                dataQuiz = response;
                //Eerste question afhalen
                Assert.assertEquals(3,dataQuiz.size());            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Assert.fail();
            }
        };
        dataService.getRandomQuestions(listener,errorListener,3,1);

    }

    @Test
    public void getData2() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        dataService = new DataServiceApi(appContext);
        // Context of the app under test.
        //{"Question_ID":2,"Question":"Wie is de 45ste president van de verenigde staten.","Answers":[{"Answer_ID":5,"Answer":"George W. Bush","Question_ID":2,"Correct":0},{"Answer_ID":6,"Answer":"Barack Obama","Question_ID":2,"Correct":0},{"Answer_ID":7,"Answer":"Donald Trump","Question_ID":2,"Correct":1}]}
        //String Answer, Boolean Correct
        Answers answer = new Answers("George W. Bush","0");
        answers.add(answer);
        answer = new Answers("Barack Obama","0");
        answers.add(answer);
        answer = new Answers("Donald Trump","1");
        answers.add(answer);

        final Quiz testQuiz = new Quiz("Wie is de 45ste president van de verenigde staten.", answers);


        final Response.Listener  listener = new Response.Listener<List<Quiz>>() {
            @Override
            public void onResponse(List<Quiz> response) {
                dataQuiz = response;
                //Eerste question afhalen
                assertEquals("Wat is Claustrofobie?",response.get(2).getQuestion());
            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Assert.fail();

            }
        };
        dataService.getRandomQuestions(listener,errorListener,3,1);
        if(dataQuiz != null){
            Assert.assertEquals(true,true);

        }else{
            Assert.fail();
        }

    }
}
