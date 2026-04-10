package com.chuwa.redbook.service.impl;

import com.chuwa.redbook.dao.CommentRepository;
import com.chuwa.redbook.dao.PostRepository;
import com.chuwa.redbook.entity.Comment;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.exception.BlogAPIException;
import com.chuwa.redbook.exception.ResourceNotFoundException;
import com.chuwa.redbook.payload.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock(name = "modelMapper")
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentDto commentDto;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Post title");
        post.setDescription("Post description");
        post.setContent("Post content");

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("Alice");
        commentDto.setEmail("alice@example.com");
        commentDto.setBody("This is a comment.");

        comment = new Comment();
        comment.setId(1L);
        comment.setName("Alice");
        comment.setEmail("alice@example.com");
        comment.setBody("This is a comment.");
        comment.setPost(post);
    }

    @Test
    void testCreateComment_Success() {
        Comment requestComment = new Comment();
        requestComment.setName(commentDto.getName());
        requestComment.setEmail(commentDto.getEmail());
        requestComment.setBody(commentDto.getBody());

        Comment savedComment = new Comment();
        savedComment.setId(1L);
        savedComment.setName(commentDto.getName());
        savedComment.setEmail(commentDto.getEmail());
        savedComment.setBody(commentDto.getBody());
        savedComment.setPost(post);

        Mockito.when(modelMapper.map(ArgumentMatchers.any(CommentDto.class), ArgumentMatchers.eq(Comment.class)))
                .thenReturn(requestComment);
        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.save(ArgumentMatchers.any(Comment.class)))
                .thenReturn(savedComment);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Comment.class), ArgumentMatchers.eq(CommentDto.class)))
                .thenReturn(commentDto);

        CommentDto result = commentService.createComment(post.getId(), commentDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(commentDto.getName(), result.getName());
        Assertions.assertEquals(commentDto.getEmail(), result.getEmail());
        Assertions.assertEquals(commentDto.getBody(), result.getBody());
        Mockito.verify(commentRepository, Mockito.times(1)).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void testCreateComment_PostNotFound() {
        Mockito.when(postRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> commentService.createComment(99L, commentDto));
    }

    @Test
    void testGetCommentsByPostId_Success() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Mockito.when(commentRepository.findByPostId(ArgumentMatchers.eq(post.getId())))
                .thenReturn(comments);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Comment.class), ArgumentMatchers.eq(CommentDto.class)))
                .thenReturn(commentDto);

        List<CommentDto> results = commentService.getCommentsByPostId(post.getId());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(commentDto, results.get(0));
    }

    @Test
    void testGetCommentById_Success() {
        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(comment));
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Comment.class), ArgumentMatchers.eq(CommentDto.class)))
                .thenReturn(commentDto);

        CommentDto result = commentService.getCommentById(post.getId(), comment.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(commentDto, result);
    }

    @Test
    void testGetCommentById_CommentNotFound() {
        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> commentService.getCommentById(post.getId(), 99L));
    }

    @Test
    void testGetCommentById_CommentDoesNotBelongToPost() {
        Post otherPost = new Post();
        otherPost.setId(2L);
        comment.setPost(otherPost);

        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(comment));

        BlogAPIException exception = Assertions.assertThrows(BlogAPIException.class,
                () -> commentService.getCommentById(post.getId(), comment.getId()));

        Assertions.assertEquals("Comment does not belong to post", exception.getMessage());
    }

    @Test
    void testUpdateComment_Success() {
        CommentDto updateDto = new CommentDto();
        updateDto.setName("Bob");
        updateDto.setEmail("bob@example.com");
        updateDto.setBody("Updated comment body.");

        Comment persistedComment = new Comment();
        persistedComment.setId(comment.getId());
        persistedComment.setName(comment.getName());
        persistedComment.setEmail(comment.getEmail());
        persistedComment.setBody(comment.getBody());
        persistedComment.setPost(post);

        Comment updatedComment = new Comment();
        updatedComment.setId(comment.getId());
        updatedComment.setName(updateDto.getName());
        updatedComment.setEmail(updateDto.getEmail());
        updatedComment.setBody(updateDto.getBody());
        updatedComment.setPost(post);

        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(persistedComment));
        Mockito.when(commentRepository.save(ArgumentMatchers.any(Comment.class)))
                .thenReturn(updatedComment);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Comment.class), ArgumentMatchers.eq(CommentDto.class)))
                .thenReturn(updateDto);

        CommentDto result = commentService.updateComment(post.getId(), comment.getId(), updateDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateDto.getName(), result.getName());
        Assertions.assertEquals(updateDto.getEmail(), result.getEmail());
        Assertions.assertEquals(updateDto.getBody(), result.getBody());
    }

    @Test
    void testUpdateComment_CommentDoesNotBelongToPost() {
        Post otherPost = new Post();
        otherPost.setId(2L);
        comment.setPost(otherPost);

        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(comment));

        BlogAPIException exception = Assertions.assertThrows(BlogAPIException.class,
                () -> commentService.updateComment(post.getId(), comment.getId(), commentDto));

        Assertions.assertEquals("Comment does not belong to post", exception.getMessage());
    }

    @Test
    void testDeleteComment_Success() {
        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(comment));
        Mockito.doNothing().when(commentRepository).delete(ArgumentMatchers.any(Comment.class));

        commentService.deleteComment(post.getId(), comment.getId());

        Mockito.verify(commentRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void testDeleteComment_CommentDoesNotBelongToPost() {
        Post otherPost = new Post();
        otherPost.setId(2L);
        comment.setPost(otherPost);

        Mockito.when(postRepository.findById(ArgumentMatchers.eq(post.getId())))
                .thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findById(ArgumentMatchers.eq(comment.getId())))
                .thenReturn(Optional.of(comment));

        BlogAPIException exception = Assertions.assertThrows(BlogAPIException.class,
                () -> commentService.deleteComment(post.getId(), comment.getId()));

        Assertions.assertEquals("Comment does not belong to post", exception.getMessage());
    }

    @Test
    void testCommentServiceMapperUtil_StaticMethod() {
        CommentDto result = CommentServiceImpl.commentServiceMapperUtil(comment);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(comment.getId(), result.getId());
        Assertions.assertEquals(comment.getName(), result.getName());
        Assertions.assertEquals(comment.getEmail(), result.getEmail());
        Assertions.assertEquals(comment.getBody(), result.getBody());
    }
}
