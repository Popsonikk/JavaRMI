import java.util.ArrayList;
import java.util.List;

public class Test {
    private List<Question> questionList;
    Test()
    {
        this.questionList=new ArrayList<>();
    }
    public void  addQuestion(Question q)
    {
        questionList.add(q);
    }
    public Question getQuestion(int i)
    {
        return questionList.get(i);
    }

}
