package woongjin.gatherMind.exception.schedule;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException(String message) { super(message); }

    public ScheduleNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
