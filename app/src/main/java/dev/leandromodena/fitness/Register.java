package dev.leandromodena.fitness;

public class Register {
    String type;
    double response;
    String createDate;

    public Register(){

    }

    public Register(String type, double response, String createDate) {
        this.type = type;
        this.response = response;
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getResponse() {
        return response;
    }

    public void setResponse(double response) {
        this.response = response;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
