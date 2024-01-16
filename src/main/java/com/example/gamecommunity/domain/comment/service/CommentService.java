package com.example.gamecommunity.domain.comment.service;


import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Post post = postRepository.findByPostId(postId).orElseThrow( () ->
                new BusinessException(HttpStatus.BAD_REQUEST,  ErrorCode.NOT_FOUND_POST_EXCEPTION));
        String content = commentRequestDto.content();
        Comment comment = new Comment(user, post, content);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(User user, Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(() ->
                new BusinessException(HttpStatus.BAD_REQUEST,ErrorCode.NOT_FOUND_COMMENT));
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,  ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
        }
        comment.update(commentRequestDto.content());
        return new CommentResponseDto(comment);

    }


    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(() ->
                new BusinessException(HttpStatus.BAD_REQUEST,ErrorCode.NOT_FOUND_COMMENT));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,  ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
        }

        commentRepository.delete(comment);
    }
    public Page<CommentResponseDto> getComments(
        int page, int size, String sortKey, boolean isAsc,
        Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() ->
            new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_POST_EXCEPTION));

        // 페이징 및 정렬처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortKey);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Comment> commentList = commentRepository
            .findAllByPost(post, pageable);

        return commentList.map(CommentResponseDto::new);
    }
}
