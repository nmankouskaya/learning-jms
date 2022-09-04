package org.learning.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class NotificationEvent implements Serializable {
    private static final long serialVersionUID = -2922387009408297069L;
    private String type;
    private String name;
    private LocalDateTime creationDateTime;
    private LocalDateTime modifiedDateTime;

    @Builder
    public NotificationEvent(String type, String name, LocalDateTime creationDateTime) {
        this.type = type;
        this.name = name;
        this.creationDateTime = creationDateTime;
    }
}
