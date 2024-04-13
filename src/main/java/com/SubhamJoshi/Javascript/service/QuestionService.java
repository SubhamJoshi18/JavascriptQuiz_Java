package com.SubhamJoshi.Javascript.service;

import com.SubhamJoshi.Javascript.Wrapper.QuestionResponse;
import com.SubhamJoshi.Javascript.Wrapper.StringWrapper;
import com.SubhamJoshi.Javascript.Wrapper.TitleWrapper;
import com.SubhamJoshi.Javascript.model.Question;
import com.SubhamJoshi.Javascript.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {


    private final QuestionRepository questionRepository;

    //@Autowired
     //QuestionRepository questionRepository
    @Autowired
    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }



    public ResponseEntity<List<String>> getAllTitle(){
        List<Question> questionList = this.questionRepository.findAll();
        if(questionList.size() == 0 || questionList.isEmpty()){
            List<String> newArray = new ArrayList<>();
            return new ResponseEntity<>(newArray, HttpStatus.NO_CONTENT);
        }
        try{

            List<String> TitleList = new ArrayList<>();

            for(int i=0; i<questionList.size(); i++){

                TitleList.add(questionList.get(i).getQuestion_title());
            }



            return new ResponseEntity<>(TitleList,HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);


    }



    public ResponseEntity<Question> createQuestionUser(Question question){
        Question question1 = this.questionRepository.save(question);
        return new ResponseEntity<>(question1, HttpStatus.CREATED);

    }



    public ResponseEntity<List<Question>> getAllQuestion(){
        List<Question> questionlist = this.questionRepository.findAll();
        int countQuestion = 0;

        for(int i = 0; i<questionlist.size(); i++){
            countQuestion++;
        }

        try{

            if(questionlist.size() ==0 || questionlist.isEmpty()){
                throw new RuntimeException("No Question Available");
            }else{
                System.out.println(countQuestion);
                return new ResponseEntity<>(questionlist,HttpStatus.OK);
            }



        }catch(Exception e){
            e.printStackTrace();
        }



        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);

    }


    public ResponseEntity<Question> updateQuestionById(Integer id,Question question){

        Optional<Question> questionid  = this.questionRepository.findById(id);
        if(!questionid.isPresent() || questionid.isEmpty()){
            throw new RuntimeException("No Question Existed");
        }

        Question existingquestion = questionid.get();
        existingquestion.setQuestion_title(question.getQuestion_title());
        existingquestion.setCategory(question.getCategory());
        existingquestion.setDifficulty_level(question.getDifficulty_level());
        existingquestion.setOption1(question.getOption1());
        existingquestion.setOption2(question.getOption2());
        existingquestion.setOption3(question.getOption3());
        existingquestion.setOption4(question.getOption4());
        existingquestion.setRight_answer(question.getRight_answer());

         this.questionRepository.save(existingquestion);
         Question newQuestion = existingquestion;


         return new ResponseEntity<>(newQuestion, HttpStatus.OK);



    }

    public ResponseEntity<String> deleteQuestionById(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(!question.isPresent() || question.isEmpty()) {
            return new ResponseEntity<>("QUESTION DOES NOT EXISTS", HttpStatus.NOT_FOUND);
        }
        Question question1 = question.get();
        this.questionRepository.delete(question1);
         return new ResponseEntity<>("Question Delete SuccessFully",HttpStatus.OK);


    }

    public ResponseEntity<Object> getQuestionById(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(!question.isPresent() || question.isEmpty()){
            StringWrapper stringWrapper = new StringWrapper("Question Does not Exists");
            return new ResponseEntity<>(stringWrapper,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(question, HttpStatus.OK);


    }


    public ResponseEntity<List<Question>> getQuestionCategory(String category){
        List<Question> questionlist = this.questionRepository.findByCategory(category);
        int count = 0;
        for(int i = 0; i<questionlist.size(); i++){
             count++;
        }
        List<String> questionCategory = questionlist.stream().map(data -> data.getCategory()).collect(Collectors.toList());
        for(int j = 0; j<questionCategory.size(); j++){
            System.out.println("The Category are  : " + "\t" + questionCategory.get(j));
        }

        try{
            if(questionlist.isEmpty() || questionlist.size() == 0){
                throw new RuntimeException("Cannot find the Category");
            }

            System.out.println(count);
             return new ResponseEntity<>(questionlist, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }



    public ResponseEntity<List<Question>> getQuestionDifficulty(String difficulty_level){
        List<Question> questions = this.questionRepository.findByDifficultyLevel(difficulty_level);
        if(questions.isEmpty() || questions.size() == 0){
            throw new RuntimeException("Difficulty Level does not match");
        }

        String[] arr = {"easy", "hard"};
        int count= 0;
        for(int i =0; i<questions.size(); i++){
            if(Arrays.asList(arr).contains(questions.get(i).getCategory())){
                  count++;
            }
        }


        return new ResponseEntity<>(questions,HttpStatus.OK);


    }
}
