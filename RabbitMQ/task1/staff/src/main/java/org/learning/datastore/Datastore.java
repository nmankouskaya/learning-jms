package org.learning.datastore;

import lombok.NoArgsConstructor;
import org.learning.event.NotificationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class Datastore {
    private List<NotificationEvent> datastore = new ArrayList<>();

    public void addToStore(NotificationEvent notificationEvent) {
        datastore.add(notificationEvent);
    }

    public NotificationEvent getLastNotArchivedRecord() {
        if (!datastore.isEmpty()) {
            datastore.stream().filter(notification -> !notification.getType()
                    .equalsIgnoreCase("ARCHIVED")).collect(Collectors.toList());
            return datastore.get(datastore.size() - 1);
        }
        return null;
    }

    public List<NotificationEvent> getAllRecords() {
        return datastore;
    }

}
