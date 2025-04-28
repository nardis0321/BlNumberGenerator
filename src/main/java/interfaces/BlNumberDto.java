package interfaces;

import domain.Command;

public class BlNumberDto {
    /**
     * 외부 입력값 검증: DTO에서 (꼭 넣어야 안전? vs 속도? : 시간 비용에 비하면 입력 실수 방지 이득)
     * 도메인의 핵심 규칙: Entity에서
     */
    public static class RegisterRequest {
        private final String origin;
        private final String destination;
        private final int splitSequence;

        public RegisterRequest(String origin, String destination, int splitSequence) {
            if(!isValidString(origin) || !isValidString(destination)) {
                throw new RuntimeException("옳지 않은 origin/destination : "+origin+" > "+destination);
            }

            this.origin = origin;
            this.destination = destination;
            this.splitSequence = splitSequence;
        }

        public Command toCommand() {
            return new Command(origin, destination, splitSequence);
        }

        private boolean isValidString(String s){
            // 1. 문자열이 null이거나 빈 문자열이면 false 반환
            if (s == null || s.isEmpty()) {
                return false;
            }

            // 2. 문자열 길이가 3이어야 함
            if (s.length() != 3) {
                return false;
            }

            // 3. 문자열이 영문자만 포함하는지 확인
            return s.matches("[a-zA-Z]{3}");
        }
    }
}
