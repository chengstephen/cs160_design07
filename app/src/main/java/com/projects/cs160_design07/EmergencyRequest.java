package com.projects.cs160_design07;

/**
 * Created by cstep on 4/19/2017.
 */

public class EmergencyRequest extends Request {

    public EmergencyRequest() {
        super();
    }

    public EmergencyRequest(String message, String name) {
        super(message, name, "0 minutes");
    }

}
