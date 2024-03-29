package br.com.elektruck.Quiz.service;

import br.com.elektruck.Quiz.model.Question;
import br.com.elektruck.Quiz.repository.QuestionDAO;
import br.com.elektruck.Quiz.repository.QuizDAO;
import br.com.elektruck.Quiz.model.QuestionWrapper;
import br.com.elektruck.Quiz.model.Quiz;
import br.com.elektruck.Quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private QuestionDAO questionDAO;

    public void createQuiz(String category, int numQuestions, String title) {
        List<Question> questions = questionDAO.findRandomQuestionsByCategory(category, numQuestions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);
    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {
        var quizList = quizDAO.findById(id);
        if(quizList.isPresent()) {
            List<Question> questionsFromDB = quizList.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return questionsForUser;
        }
        return new ArrayList<>();
    }

    public String calculateResult(Integer id, List<Response> answers) {
        var quiz = quizDAO.findById(id);
        if(quiz.isPresent()) {
            List<Question> questions = quiz.get().getQuestions();
            List<Response> correctAnswers = new ArrayList<>();
            for(Question q : questions) {
                Response r = new Response(q.getId(), q.getRightAnswer());
                correctAnswers.add(r);
            }
            answers.retainAll(correctAnswers);
            return "Questions answered correctly: %d of %d".formatted(answers.size(), correctAnswers.size());
        }
        return "No questions answered";
    }
}