package com.spring.quiz.Controllers;

import com.spring.quiz.Models.Question;
import com.spring.quiz.Models.QuestionsWrapper;
import com.spring.quiz.Models.Response;
import com.spring.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("createQuiz")
    public ResponseEntity<String> createQuiz(@RequestParam String title, @RequestParam String category, @RequestParam int numQuestions) {
        return quizService.createQuiz(title, category, numQuestions);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
