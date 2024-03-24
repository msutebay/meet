package org.ssb.meet.exception;

public class MeetingRoomNotAvailableException extends Exception{
    public MeetingRoomNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
