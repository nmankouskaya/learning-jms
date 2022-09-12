package org.learning.dto;

import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {
    private CustomSerdes() {}

    static public final class EmployeeSerde extends Serdes.WrapperSerde<Employee> {
        public EmployeeSerde() {
            super(new JsonSerializer(), new JsonDeserializer<>(Employee.class));
        }
    }
}
