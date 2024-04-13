package com.SubhamJoshi.Javascript.controller;


import com.SubhamJoshi.Javascript.Wrapper.TitleWrapper;
import com.SubhamJoshi.Javascript.model.Question;
import com.SubhamJoshi.Javascript.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService =questionService;
    }



    @GetMapping("title")
    public ResponseEntity<List<String>> getQuestion(){
        return this.questionService.getAllTitle();
    }


    @GetMapping("/allQuestion")
public ResponseEntity<List<Question>> getAllQuestion(){
      return this.questionService.getAllQuestion();
    }


    @PostMapping("/")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){

        return this.questionService.createQuestionUser(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id,@RequestBody Question question){
        return this.questionService.updateQuestionById(id,question);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteQuestion(@PathVariable Integer id){
        return this.questionService.deleteQuestionById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuestionId(@PathVariable Integer id){
        return this.questionService.getQuestionById(id);
    }


    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return this.questionService.getQuestionCategory(category);
    }

    @GetMapping("difficulty/{difficulty_level}")
    public ResponseEntity<List<Question>> getQuestionByDifficulty(@PathVariable String difficulty_level){
        return this.questionService.getQuestionDifficulty(difficulty_level);
    }

}
