package interfaces;

import domain.BlNumber;
import domain.Service;

public class Controller {
    private final View view;
    private final Service service;

    public Controller(View view) {
        this.view = view;
        this.service = Service.getInstance();
    }

    public void handleGenerateBlNumber(BlNumberDto.RegisterRequest request) {
        view.displayInfoMessage("BL 번호 생성 시작...");

        try {
            BlNumber blNumber = service.registerBlNumber(request.toCommand());

            view.displayInfoMessage("BL 번호 생성 완료: " + blNumber.getRandom4Digit());

            StringBuilder resultBuilder = new StringBuilder();
            blNumber.getBlNumbers().forEach(bl -> {
                resultBuilder.append(bl).append("\n");
            });
            view.displayResult(resultBuilder.toString());
        } catch (Exception e) {
            view.displayInfoMessage("에러 발생: " + e.getMessage());
        }
    }
}
