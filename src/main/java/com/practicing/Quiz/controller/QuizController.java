package com.practicing.Quiz.controller;

import com.practicing.Quiz.model.Question;
import com.practicing.Quiz.model.QuestionWrapper;
import com.practicing.Quiz.model.Response;
import com.practicing.Quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    private static final Logger LOGGER = Logger.getLogger(QuizController.class.getName());

    private void logException(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQuestions, @RequestParam String title) {
        try {
                quizService.createQuiz(category, numQuestions, title);
                return new ResponseEntity<String>("%s quiz with title '%s' and %d questions created".formatted(category, title, numQuestions), HttpStatus.CREATED);
            } catch (Exception e) {
                logException(e);
            }
        return new ResponseEntity<String>("Quiz creation error", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        try {
            List<QuestionWrapper> quizQuestions = quizService.getQuizQuestions(id);
        return new ResponseEntity<List<QuestionWrapper>>(quizQuestions, HttpStatus.OK);
        } catch (Exception e) {
            logException(e);
        }
        return new ResponseEntity<List<QuestionWrapper>>(new ArrayList<QuestionWrapper>(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuest(@PathVariable Integer id, @RequestBody List<Response> responses) {
        try {
            String grade = quizService.calculateResult(id, responses);
            return new ResponseEntity<String>(grade, HttpStatus.OK);
        } catch (Exception e) {
            logException(e);
        }
        return new ResponseEntity<String>("Ill-formed answers submission", HttpStatus.BAD_REQUEST);
    }
}