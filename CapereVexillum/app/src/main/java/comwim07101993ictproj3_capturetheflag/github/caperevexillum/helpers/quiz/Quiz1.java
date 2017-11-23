package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sanli on 17/10/2017.
 */

public class Quiz1 {
    private String Question;
    private List<Answers> Answers;

    public Quiz1(String Question, List<Answers> Answers){
        this.Question = Question;
        this.Answers  =Answers;
    }
    public String getQuestion() {
        return Question;
    }

    public List<Answers> getAnswers() {
        //Collections.shuffle(Answers);
        return Answers;
    }

    public Answers getAnswer(int i){
        return Answers.get(i);
    }

}
