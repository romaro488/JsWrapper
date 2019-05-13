package ua.polosmak.wrapper.exception;

import ua.polosmak.wrapper.messages.ErrorMessages;

/**
 * Signals that compilation failed.
 */
public class FailedCompilationException extends ScriptServiceException {

    public FailedCompilationException() {
        super(ErrorMessages.COMPILATION_FAILED);
    }

    public FailedCompilationException(String message) {
        super(message);
    }
}
