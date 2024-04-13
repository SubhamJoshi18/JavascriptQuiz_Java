package com.SubhamJoshi.Javascript.repository;

import com.SubhamJoshi.Javascript.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question,Integer> {



    @Query(value = "SELECT * FROM question q where q.category =?1 ORDER BY RANDOM() LIMIT ?2" , nativeQuery = true)
    List<Question> findByRandomQuestion(String category, Integer numQ);

    @Query(value = "SELECT * FROM question q where q.category =?1", nativeQuery = true)
    List<Question> findByCategory(String category);



    @Query(value = "SELECT * FROM question q where q.difficulty_level=?1",nativeQuery = true)
    List<Question> findByDifficultyLevel(String difficulty_level);
}
