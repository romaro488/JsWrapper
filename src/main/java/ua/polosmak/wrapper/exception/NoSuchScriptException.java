package ua.polosmak.wrapper.exception;

import ua.polosmak.wrapper.messages.ErrorMessages;

/**
 * Signals that the specified script does not exist.
 *
 * @author Roman Polosmak
 */
public class NoSuchScriptException extends ScriptServiceException {

    public NoSuchScriptException() {
        super(ErrorMessages.SCRIPT_WITH_SPECIFIED_ID_DOES_NOT_EXIST);
    }

    /**
     * Create a new NoSuchScriptException.
     *
     * @param message the detail message
     */
    public NoSuchScriptException(String message) {
        super(message);
    }
}