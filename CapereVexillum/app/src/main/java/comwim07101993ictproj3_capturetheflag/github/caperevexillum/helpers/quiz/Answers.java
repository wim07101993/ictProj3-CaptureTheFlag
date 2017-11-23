package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz;

/**
 * Created by georg on 19/11/2017.
 */

public class Answers {
   private String Answer;
   private String Correct;


   public Answers(String Answer, String Correct){
       this.Answer = Answer;
       this.Correct = Correct;
   }
    public String getAnswer() {
        return Answer;
    }
    public String isAnswerCorrect(){return Correct;}
}
