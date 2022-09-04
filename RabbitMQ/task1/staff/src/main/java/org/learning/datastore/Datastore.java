package org.learning.datastore;

import lombok.NoArgsConstructor;
import org.learning.event.NotificationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class Datastore {
    private List<NotificationEvent> datastore = new ArrayList<>();

    public void addToStore(NotificationEvent notificationEvent) {
        datastore.add(notificationEvent);
    }

    public List<NotificationEvent> getAllRecords() {
        return datastore;
    }

}
