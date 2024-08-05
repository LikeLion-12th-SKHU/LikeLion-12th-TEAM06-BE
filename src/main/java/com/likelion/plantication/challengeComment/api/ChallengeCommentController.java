package com.likelion.plantication.challengeComment.api;

import com.likelion.plantication.challengeComment.application.ChallengeCommentService;
import com.likelion.plantication.challengeComment.domain.ChallengeComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "챌린지 댓글", description = "챌린지 댓글을 담당하는 API")
@RequestMapping("/api/v1/challenge/{challengeId}/comments")
public class ChallengeCommentController {

    private final ChallengeCommentService challengeCommentService;

    @PostMapping("/add")
    @Operation(
            summary = "댓글 추가",
            description = "챌린지에 댓글을 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "댓글 추가 성공"),
                    @ApiResponse(responseCode = "404", description = "챌린지 또는 사용자 없음"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
            }
    )
    public ResponseEntity<String> addComment(
            @PathVariable @NotNull Long challengeId,
            @RequestParam @NotNull Long userId,
            @RequestParam @NotNull String content) {
        challengeCommentService.addComment(challengeId, userId, content);
        return ResponseEntity.status(201).body("댓글이 추가되었습니다.");
    }

    @GetMapping
    @Operation(
            summary = "댓글 조회",
            description = "챌린지의 모든 댓글을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "챌린지가 없음")
            }
    )
    public ResponseEntity<List<ChallengeComment>> getComments(
            @PathVariable @NotNull Long challengeId) {
        List<ChallengeComment> comments = challengeCommentService.getCommentsByChallenge(challengeId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/delete/{commentId}")
    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다. 댓글을 삭제하려면 댓글 작성자여야 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "댓글 또는 사용자 없음"),
                    @ApiResponse(responseCode = "403", description = "댓글 삭제 권한 없음")
            }
    )
    public ResponseEntity<String> deleteComment(
            @PathVariable @NotNull Long commentId,
            @RequestParam @NotNull Long userId) {
        challengeCommentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}