package com.example.gamecommunity.domain.comment.service;

import com.example.gamecommunity.domain.post.service.PostService;
import java.util.List;
import java.util.stream.Collectors;

import com.example.gamecommunity.domain.comment.dto.CommentRequestDto;
import com.example.gamecommunity.domain.comment.dto.CommentResponseDto;
import com.example.gamecommunity.domain.comment.entity.Comment;
import com.example.gamecommunity.domain.comment.repository.CommentRepository;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public CommentResponseDto createComment(User user, Long postId, CommentRequestDto commentRequestDto) {
        Post post = postService.getFindPost(postId);
        Comment comment = new Comment(user, post, commentRequestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(User user, Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new BusinessException(HttpStatus.BAD_REQUEST,ErrorCode.NOT_FOUND_COMMENT));
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,  ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);

    }


    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new BusinessException(HttpStatus.BAD_REQUEST,ErrorCode.NOT_FOUND_COMMENT));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,  ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
        }

        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> getComment(Long postId) {
        return commentRepository.findAllById(postId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
