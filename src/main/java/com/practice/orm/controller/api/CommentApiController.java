package com.practice.orm.controller.api;

import com.practice.orm.dto.comment.CreateCommentDTO;
import com.practice.orm.dto.comment.UpdateCommentDTO;
import com.practice.orm.model.Comment;
import com.practice.orm.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Comment>> findAll(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Optional<Comment>> findCommentById(
            @RequestParam(name = "comment_id") Long comment_id){
        return new ResponseEntity<>(commentService.findByCommentId(comment_id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Comment>> findCommentsByUserId(
            @RequestParam(name = "user_id") Long user_id){
        return new ResponseEntity<>(commentService.findByUserId(user_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CreateCommentDTO commentDTO){
        return new ResponseEntity<>(commentService.save(commentDTO), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Boolean> changeComment(@RequestBody UpdateCommentDTO commentDTO){
        boolean isCreated = commentService.update(commentDTO);

        HttpStatus status;
        if(isCreated)
            status = HttpStatus.CREATED;
        else
            status = HttpStatus.OK;

        return new ResponseEntity<>(isCreated, status);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteAllByUserId(@RequestParam Long user_id){
        commentService.deleteAllByUserId(user_id);
        return new ResponseEntity<>("Delete all comments of " + user_id, HttpStatus.OK);
    }
    
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteCommentByCommentId(@RequestParam Long comment_id){
        commentService.deleteById(comment_id);
        return new ResponseEntity<>("Delete Completed", HttpStatus.OK);
    }

}
