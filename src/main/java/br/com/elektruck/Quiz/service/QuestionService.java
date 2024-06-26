package br.com.elektruck.Quiz.service;

import br.com.elektruck.Quiz.repository.QuestionDAO;
import br.com.elektruck.Quiz.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDAO questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public void addQuestion(Question question) {
        questionDao.save(question);
    }

    public boolean deleteQuestion(int id) {
        Optional<Question> q = questionDao.findById(id);
        if(q.isPresent()) {
            questionDao.delete(q.get());
            return true;
        }
        return false;
    }
}