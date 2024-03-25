package org.ssb.meet.service.validators;

import org.springframework.stereotype.Service;

@Service
public class InputValidator {

    public boolean validateMeetingRoomName(String name){
        return name.length()>=3 && name.length()<=20;
    }
}
