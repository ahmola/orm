package com.practice.orm.service;

import com.practice.orm.dto.comment.CreateCommentDTO;
import com.practice.orm.dto.comment.UpdateCommentDTO;
import com.practice.orm.exception.CommentNotFoundException;
import com.practice.orm.exception.UserNotFoundException;
import com.practice.orm.model.Comment;
import com.practice.orm.model.User;
import com.practice.orm.repositroy.CommentRepository;
import com.practice.orm.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findByCommentId(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findByUserId(Long user_id) {
        return userRepository.findById(user_id).get().getComments();
    }

    public Comment save(CreateCommentDTO commentDTO){
        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .build();

        User user = userRepository.findByUsername(commentDTO.getUsername()).get();
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public void deleteAllByUserId(Long user_id) {
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException(user_id.toString());
        User user = userRepository.findById(user_id).get();
        commentRepository.deleteAll(user.getComments());
    }


    public void deleteById(Long comment_id){
        if(!commentRepository.existsById(comment_id))
            throw new CommentNotFoundException(comment_id.toString());
        commentRepository.deleteById(comment_id);
    }

    public Boolean update(UpdateCommentDTO commentDTO) {
        Comment comment;
        if (!commentRepository.existsById(commentDTO.getId())){
             comment = Comment.builder()
                    .content(commentDTO.getContent())
                    .user(userRepository.findByUsername(commentDTO.getUsername()).get())
                    .build();
            commentRepository.save(comment);
            return true;
        }

        comment = commentRepository.findById(commentDTO.getId()).get();
        comment.setContent(commentDTO.getContent());
        comment.setUser(userRepository.findByUsername(commentDTO.getUsername()).get());

        for (Long subId:commentDTO.getSubCommentsId()) {
            if(comment.getSubComments().stream().noneMatch(sub -> sub.getId() == subId)){
                comment.getSubComments().add(commentRepository.findById(subId).get());
            }
        }

        commentRepository.save(comment);
        return false;
    }
}
