package org.learning.service;

import lombok.RequiredArgsConstructor;
import org.learning.event.EventStatus;
import org.learning.event.NotificationEvent;
import org.learning.exception.UnprocessableNotificationException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class SendEventMessageRecoverer implements MethodInvocationRecoverer<Void> {

    private static final String RECOVER_TRIGGERED_WARN_MESSAGE = "Message was not consumed after defined amount of retries. Recover logic triggered.";
    private static final String RECOVER_AFTER_FAILED_CREATE = "Recover logic has been triggered because of error during receiving notification : ";
    private static final String MESSAGE_ROUTED_TO_DLQ_WARN_MESSAGE = "Message was routed to the DLQ.";
    private static final String RECOVER_ERROR_MESSAGE = "Error occurred while recovering process: ";
    private final EventPublisher eventPublisher;

    @Override
    public Void recover(Object[] args, Throwable cause) {
        final var message = (Message) args[1];
        System.out.println(RECOVER_TRIGGERED_WARN_MESSAGE);
        final var event = (NotificationEvent) new SimpleMessageConverter().fromMessage(message);
        final var error = cause.getCause();
        System.out.println(RECOVER_AFTER_FAILED_CREATE + error.getMessage());
        if (error instanceof UnprocessableNotificationException) {
            event.setType(EventStatus.UNPROCESSED.name());
            event.setModifiedDateTime(LocalDateTime.now());
            eventPublisher.publishToFailed(event);
        } else {
            event.setType(EventStatus.WAIT.name());
            event.setModifiedDateTime(LocalDateTime.now());
            eventPublisher.publishToWait(event);
        }
        return null;
    }
}
