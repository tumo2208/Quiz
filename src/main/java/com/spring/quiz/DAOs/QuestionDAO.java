package com.spring.quiz.DAOs;

import com.spring.quiz.Models.Question;
import com.spring.quiz.Utils.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {


    List<Question> findByCategory(Category category);

    @Query(value = "SELECT * FROM questions WHERE questions.category=:category ORDER BY RAND() LIMIT :numQuestions", nativeQuery = true)
    List<Question> findRandomByCategory(String category, int numQuestions);
}
