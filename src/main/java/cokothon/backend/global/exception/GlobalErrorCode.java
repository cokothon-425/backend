package cokothon.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    DEFAULT("응답 처리 중, 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    SECURITY_USER_NOT_FOUND("로그인된 유저의 정보를 불러오는 데 실패했습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다.", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND("사용자를 찾을 수 없습니다", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND("책을 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    GROUP_NOT_FOUND("독서모임을 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    GROUP_MEMBER_NOT_FOUND("독서모임 멤버 정보를 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    RECORD_NOT_FOUND("기록을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;
}