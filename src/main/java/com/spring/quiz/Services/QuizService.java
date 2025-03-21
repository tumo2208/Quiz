package com.spring.quiz.Services;

import com.spring.quiz.DAOs.QuestionDAO;
import com.spring.quiz.DAOs.QuizDAO;
import com.spring.quiz.Models.Question;
import com.spring.quiz.Models.QuestionsWrapper;
import com.spring.quiz.Models.Quiz;
import com.spring.quiz.Models.Response;
import com.spring.quiz.Utils.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<String> createQuiz(String title, String category, int numQuestions) {
        List<Question> questions = questionDAO.findRandomByCategory(category, numQuestions);
        Quiz quiz = new Quiz(title, Category.valueOf(category), questions);
        try {
            quizDAO.save(quiz);
            return new ResponseEntity<>("Quiz is added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fail to add this quiz", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(int id) {
        try {
            Optional<Quiz> quiz = quizDAO.findById(id);
            List<Question> questions = quiz.get().getQuestions();
            List<QuestionsWrapper> questionsForUsers = new ArrayList<>();
            for (int i = 0; i < questions.size(); ++i) {
                Question question = questions.get(i);
                QuestionsWrapper questionForUsers = new QuestionsWrapper(i + 1, question.getQuestion(),
                        question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
                questionsForUsers.add(questionForUsers);
            }

            return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        try {
            Quiz quiz = quizDAO.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int res = 0;
            int i = 0;
            for (Response response : responses) {
                if (response.getResponse() == questions.get(i).getRightAnswer()) {
                    res++;
                }
                ++i;
            }

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }

    }
}
