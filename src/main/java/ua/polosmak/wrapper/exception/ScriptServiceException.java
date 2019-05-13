package ua.polosmak.wrapper.exception;

import ua.polosmak.wrapper.messages.ErrorMessages;

/**
 * Signals that an attempt to use the script service has failed.
 */
public class ScriptServiceException extends Exception {


    public ScriptServiceException() {
        super(ErrorMessages.USE_SCRIPT_SERVICE_EXCEPTION);
    }

    public ScriptServiceException(String message) {
        super(message);
    }
}
