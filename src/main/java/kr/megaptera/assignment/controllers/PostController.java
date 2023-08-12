package kr.megaptera.assignment.controllers;

import kr.megaptera.assignment.application.PostService;
import kr.megaptera.assignment.dtos.PostCreateRequestDTO;
import kr.megaptera.assignment.dtos.PostCreateResponseDTO;
import kr.megaptera.assignment.dtos.PostDeleteResponseDTO;
import kr.megaptera.assignment.dtos.PostGetResponseDTO;
import kr.megaptera.assignment.dtos.PostUpdateRequestDTO;
import kr.megaptera.assignment.dtos.PostUpdateResponseDTO;
import kr.megaptera.assignment.exceptions.ErrorResponse;
import kr.megaptera.assignment.exceptions.PostIdParsingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kr.megaptera.assignment.exceptions.ErrorCode.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 조회 - GET /posts
    @GetMapping
    public ResponseEntity<List<PostGetResponseDTO>> list() {
        List<PostGetResponseDTO> responseDTOS = postService.list();
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    // 게시글 상세조회 - GET /posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostGetResponseDTO> get(@PathVariable String id) {
        PostGetResponseDTO responseDTO = postService.get(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 게시글 작성 - POST /posts
    @PostMapping
    public ResponseEntity<PostCreateResponseDTO> create(@RequestBody PostCreateRequestDTO requestDTO) {
        PostCreateResponseDTO responseDTO = postService.create(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // 게시글 수정 - PATCH /posts/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<PostUpdateResponseDTO> update(
            @PathVariable String id,
            @RequestBody PostUpdateRequestDTO requestDTO) {
        PostUpdateResponseDTO responseDTO = postService.update(
                id,
                requestDTO
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 게시글 삭제 - DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDeleteResponseDTO> delete(@PathVariable String id) {
        PostDeleteResponseDTO responseDTO = postService.delete(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


//    INVALID_INPUT_VALUE("올바른 입력값이 아닙니다", "COMMON-001",HttpStatus.BAD_REQUEST);
    @ExceptionHandler(PostIdParsingException.class)
    public ResponseEntity<ErrorResponse> postIdParsingException(PostIdParsingException e) {
        return new ResponseEntity<>(
                ErrorResponse.of(INVALID_INPUT_VALUE),
                INVALID_INPUT_VALUE.getStatus());
    }

}
