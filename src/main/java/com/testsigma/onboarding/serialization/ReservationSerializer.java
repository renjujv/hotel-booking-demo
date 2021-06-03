package com.testsigma.onboarding.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.testsigma.onboarding.entity.Reservation;

import java.io.IOException;

public class ReservationSerializer extends StdSerializer<Reservation> {

    protected ReservationSerializer() {
        this(null);
    }

    protected ReservationSerializer(Class<Reservation> t) {
        super(t);
    }

    @Override
    public void serialize(
            Reservation reservation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",reservation.getId());
        jsonGenerator.writeStringField("checkin",reservation.getCheckin().toString());
        jsonGenerator.writeStringField("checkout",reservation.getCheckout().toString());
        jsonGenerator.writeNumberField("bookedRoom",reservation.getBookedRoom().getRoomNumber());
        jsonGenerator.writeEndObject();
    }
}
