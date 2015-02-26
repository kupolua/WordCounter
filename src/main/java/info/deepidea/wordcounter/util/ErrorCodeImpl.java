package info.deepidea.wordcounter.util;

public enum ErrorCodeImpl implements ErrorCode {
    REQUEST_IS_EMPTY_OR_NULL,
    SOURCE_IS_EMPTY_OR_NON_READABLE,
    CANNOT_COUNT_TEXT,
    CANNOT_CONNECT,
    DEPTH_IS_OUT_OF_RANGE
}
