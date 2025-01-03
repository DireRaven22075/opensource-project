package team.ccnu.project.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ccnu.project.data.request.UploadReplyDTO;
import team.ccnu.project.domain.entity.Comment;
import team.ccnu.project.service.CommentService;
import team.ccnu.project.data.request.UploadCommentDTO;
import team.ccnu.project.data.request.DeleteCommentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService comments;
    @PostMapping
    public ResponseEntity<?> apiAddComment(
            @RequestBody UploadCommentDTO dto,
            HttpServletRequest request
    ) {
        comments.addComment(dto, request.getSession());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/reply")
    public ResponseEntity<?> apiReplies(
            @RequestBody UploadReplyDTO dto,
            HttpServletRequest request
    ) {
        comments.addReply(dto, request.getSession());
        return ResponseEntity.ok().build();
    }
    @Autowired
    private CommentService commentService;

    @GetMapping("/{pst}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long pst) {
        List<Comment> comments = commentService.getAllCommentsByPostSn(pst);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{pst}")
    public ResponseEntity<Comment> addComment(@PathVariable Long pst, @RequestBody UploadCommentDTO uploadCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(uploadCommentDTO.getText());
        // Post 설정 로직 추가 필요
        Comment savedComment = commentService.addComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/{pst}/{cmnt}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long pst, @PathVariable Long cmnt, @RequestBody UploadCommentDTO uploadCommentDTO) {
        Comment comment = commentService.getCommentBySn(cmnt)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(uploadCommentDTO.getText());
        // Post 설정 로직 추가 필요
        Comment updatedComment = commentService.updateComment(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{pst}/{cmnt}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long cmnt, @RequestBody DeleteCommentDTO deleteCommentDTO) {
        if (!cmnt.equals(deleteCommentDTO.getCommentSn())) {
            return ResponseEntity.badRequest().build();
        }
        commentService.deleteComment(cmnt);
        return ResponseEntity.ok().build();
    }
}

/*
DTO와 요청 데이터의 일치: 클라이언트에서 전송하는 JSON 데이터 구조가 DTO 클래스의 필드와 정확히 일치해야 함. 예를 들어, 클라이언트가 {"content": "example"} 형식으로 데이터를 보내야 UploadCommentDTO에 매핑 가능
필드 이름 확인: JSON 데이터의 필드 이름이 DTO 클래스의 필드 이름과 정확히 일치하는지 확인: 어트리뷰트 추가
*/