package ua.top.bootjava.error;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(CharSequence url, ErrorType type, String typeMessage, String... details) {
        this.url = url.toString();
        this.type = type;
        this.typeMessage = typeMessage;
        this.details = details;
    }

/*
    public ErrorInfo(StringBuffer requestURL,
                     ua.top.bootjava.error.ErrorType errorType,
                     String message,
                     String[] strings) {
    }
*/
}
