package domain;

public class Command {
    /**
     * 커맨드 쓰는 이유?
     * 도메인 모델과 API 요청의 분리
     * 여기서 입력값을 다양하게 받아서 다양한 DTO로 바꿔줄 수 있음
     */
    private final String ORIGIN;
    private final String DESTINATION;
    private final int SPLIT_SEQUENCE;

    public Command(String origin, String destination, int splitSequence) {
        ORIGIN = origin;
        DESTINATION = destination;
        SPLIT_SEQUENCE = splitSequence;
    }

    public BlNumber toEntity(){
        return new BlNumber(ORIGIN, DESTINATION, SPLIT_SEQUENCE);
    }
}
