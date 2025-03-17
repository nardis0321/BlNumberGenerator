public class ExceptionMessage {

    private static final ExceptionMessage instance = new ExceptionMessage();
    private String message;

    private ExceptionMessage(){
        message = "";
    }

    public static ExceptionMessage of() {
        return instance;
    }

    public static void setMessage(String message) {
        instance.message = message + "\n";
    }

    public static void appendMessage(String message) {
        instance.message = instance.message + message + "\n";
    }

    public String getMessage() {
        return message;
    }
}
