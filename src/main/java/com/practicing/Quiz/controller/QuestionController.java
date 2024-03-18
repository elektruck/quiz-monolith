package com.practicing.Quiz.controller;

import com.practicing.Quiz.model.Question;
import com.practicing.Quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    private static final Logger LOGGER = Logger.getLogger(QuestionService.class.getName());

    private void logException(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
        } catch(Exception e) {
            logException(e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category) {
        try {
            return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            logException(e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            questionService.addQuestion(question);
            return new ResponseEntity<String>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            logException(e);
        }
        return new ResponseEntity<String>("Insertion error or ill-formed request", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id) {
        try {
            boolean deleted = questionService.deleteQuestion(id);
            if(deleted) {
                return new ResponseEntity<String>("Question " + id + " deleted", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Question " + id + " not found", HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            logException(e);
        }
        return new ResponseEntity<String>("Invalid question ID or ill-formed request", HttpStatus.BAD_REQUEST);
    }
}