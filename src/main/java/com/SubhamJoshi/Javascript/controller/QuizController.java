package com.SubhamJoshi.Javascript.controller;


import com.SubhamJoshi.Javascript.dto.QuestionDTO;
import com.SubhamJoshi.Javascript.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {



    private final QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping("create")
    public ResponseEntity<Object> createQuiz(@RequestParam String category, @RequestParam Integer numQ, @RequestParam String title){
        return  this.quizService.createQuiz(category,numQ,title);
    }


    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuiz(@PathVariable Integer id){

        return this.quizService.getQuiz(id);

    }




}
