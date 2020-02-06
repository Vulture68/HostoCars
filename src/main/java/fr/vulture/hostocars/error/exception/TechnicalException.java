package fr.vulture.hostocars.error.exception;

import javax.validation.constraints.NotNull;
import org.slf4j.helpers.MessageFormatter;

/**
 * Technical exception class for the application.
 */
public class TechnicalException extends Exception {

    private static final long serialVersionUID = -6234245210996098925L;

    /**
     * Constructor with a message and its arguments.
     *
     * @param message
     *     The message
     * @param args
     *     The messages arguments
     */
    public TechnicalException(@NotNull final String message, @NotNull final Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    /**
     * Constructor with an initial throwable, a message and its arguments.
     *
     * @param cause
     *     The initial throwable
     * @param message
     *     The message
     * @param args
     *     The messages arguments
     */
    public TechnicalException(@NotNull final Throwable cause, @NotNull final String message, @NotNull final Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage(), cause);
    }

}