package info.deepidea.wordcounter.util;

public class WordCounterArgumentException extends IllegalArgumentException {
    private ErrorCode errorCode;
    private String clientRequest;

    public WordCounterArgumentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public WordCounterArgumentException(ErrorCode errorCode, String clientRequest) {
        this.errorCode = errorCode;
        this.clientRequest = clientRequest;
    }

    @Override
    public String getMessage() {
        String message = ErrorMessenger.getErrorMsg(errorCode);
        if (clientRequest != null){
            message += clientRequest;
        }
        return message;
    }
}
