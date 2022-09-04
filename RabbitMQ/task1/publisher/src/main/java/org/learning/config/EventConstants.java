package org.learning.config;

public class EventConstants {

    // Event names
    public static final String CREATE_NOTIFICATION = "CREATE_NOTIFICATION";
    public static final String NOTIFICATION_RETRY = "NOTIFICATION_RETRY";
    public static final String FAILED_NOTIFICATION = "FAILED_NOTIFICATION";

    // Exchange names
    public static final String EXCHANGE_CREATE_NOTIFICATION = "EXCHANGE_CREATE_NOTIFICATION";
    public static final String EXCHANGE_FAILED_NOTIFICATION = "EXCHANGE_FAILED_NOTIFICATION";
    public static final String EXCHANGE_NOTIFICATION_WAIT = "EXCHANGE_NOTIFICATION_WAIT";
    public static final String EXCHANGE_DLQ = "EXCHANGE_DLQ";

    // Queue names
    public static final String QUEUE_CREATE_NOTIFICATION = "QUEUE_CREATE_NOTIFICATION";
    public static final String QUEUE_FAILED_NOTIFICATION = "QUEUE_FAILED_NOTIFICATION";
    public static final String QUEUE_NOTIFICATION_WAIT = "QUEUE_NOTIFICATION_WAIT";
    public static final String DEAD_LETTER_QUEUE = "QUEUE_DEAD_LETTER";
}
