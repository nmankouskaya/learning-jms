package org.learning.config;

public class EventConstants {

    // Event names
    public static final String FAILED_NOTIFICATION = "FAILED_NOTIFICATION";
    public static final String NOTIFICATION_WAIT = "NOTIFICATION_WAIT";

    // Exchange names
    public static final String EXCHANGE_FAILED_NOTIFICATION = "EXCHANGE_FAILED_NOTIFICATION";
    public static final String EXCHANGE_NOTIFICATION_WAIT = "EXCHANGE_NOTIFICATION_WAIT";
    public static final String EXCHANGE_DLQ = "EXCHANGE_DLQ";

    // Queue names
    public static final String QUEUE_CREATE_NOTIFICATION = "QUEUE_CREATE_NOTIFICATION";
    public static final String DEAD_LETTER_QUEUE = "QUEUE_DEAD_LETTER";
}
