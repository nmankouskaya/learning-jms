package org.learning.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.learning.model.Point;
import org.learning.model.TransportMetadata;

public final class ConsumerUtil {

    public static TransportMetadata read(String dataAsString) {
        ObjectMapper mapper= new ObjectMapper();
        TransportMetadata data = null;
        try {
            data = mapper.readValue(dataAsString, TransportMetadata.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String process(TransportMetadata metadata) {
        if (metadata.getCoordinates().size() < 2) {
            return "0";
        }
        Point p1 = metadata.getCoordinates().get(metadata.getCoordinates().size()-1);
        Point p2 = metadata.getCoordinates().get(metadata.getCoordinates().size()-2);

        return getDestanceBetweenPoints(p1, p2).toString();
    }

    private static Double getDestanceBetweenPoints (Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
    }

}
