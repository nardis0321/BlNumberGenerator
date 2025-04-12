package domain;

public class Service {

    private static final Service instance = new Service();

    private Service(){}

    public static Service getInstance(){
        return instance;
    }

    public BlNumber registerBlNumber(Command command){

        BlNumber blNumber = command.toEntity();
        while(!Reader.isValidNumber(blNumber)){
            blNumber.reGenerate();
        }

        Writer.writeToExcel(blNumber);

        return blNumber;
    }
}
