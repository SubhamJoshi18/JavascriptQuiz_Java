package com.SubhamJoshi.Javascript.service;

import com.SubhamJoshi.Javascript.dto.QuestionDTO;
import com.SubhamJoshi.Javascript.model.Question;
import com.SubhamJoshi.Javascript.model.Quiz;
import com.SubhamJoshi.Javascript.repository.QuestionRepository;
import com.SubhamJoshi.Javascript.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {



    private final QuizRepository quizRepository;

 @Autowired
    QuestionRepository questionRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository){
         this.quizRepository= quizRepository;
    }

    public ResponseEntity<Object> createQuiz(String category, Integer numQ, String title){
        List<Question> questionslist = this.questionRepository.findByRandomQuestion(category,numQ);
        if(questionslist.size() < 5){
            throw new RuntimeException("LIMIT DOES NOT MATCH");
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionslist);
        this.quizRepository.save(quiz);
        return  new ResponseEntity<>(quiz, HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionDTO>> getQuiz(Integer id){
        Optional<Quiz> quiz = this.quizRepository.findById(id);

        List<Question> questionFromDB = quiz.get().getQuestions();
        if(questionFromDB.isEmpty() || questionFromDB.size() == 0){
            throw new RuntimeException("NO QUESTION");
        }
        List<QuestionDTO> questionToClient = new ArrayList<>();

        for(Question q : questionFromDB){

            QuestionDTO questionDTO = new QuestionDTO(q.getId(),q.getQuestion_title(),q.getOption1(),q.getOption2(),q.getOption3(), q.getOption4() );

              questionToClient.add(questionDTO);
        }

        questionToClient.stream().filter(data -> data.getQuestion_title().length() >10).forEach(x -> System.out.println(x));

        return new ResponseEntity<>(questionToClient, HttpStatus.OK);
    }


}
