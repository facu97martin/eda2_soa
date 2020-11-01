package com.example.eda2_martin_facundoleonel_40570462.clases.responses;

public class RegistrarEventoResponse {

    private Boolean success;
    private String env;
    private MyEvent event;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public MyEvent getEvent() {
        return event;
    }

    public void setEvent(MyEvent event) {
        this.event = event;
    }
}
