public class BlNumber {

    private final String PREFIX = "RSA";
    private final String ORIGIN;
    private final String DESTINATION;
    private int number;
    private final int SPLIT_SEQUENCE;

    public BlNumber(String origin, String DESTINATION, int SPLIT_SEQUENCE) {
        this.ORIGIN = origin;
        this.DESTINATION = DESTINATION;
        number = (int) (Math.random() * 10000);
        this.SPLIT_SEQUENCE = SPLIT_SEQUENCE - 1;
    }

    public void reGenerate(){
        number = (int) (Math.random() * 10000);
    }

    public int getNumber() {
        return number;
    }

    public String getStringNumber() {
        return String.format("%04d", number);
    }

    public String getOrigin() {
        return ORIGIN;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public String getBlNumber(){
        return String.format("%s%s%s%04d%02d", PREFIX, ORIGIN, DESTINATION, number, SPLIT_SEQUENCE);
    }
}