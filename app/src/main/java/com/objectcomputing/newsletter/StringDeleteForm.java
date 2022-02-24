package com.objectcomputing.newsletter;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Deserializable
public class StringDeleteForm extends DeleteForm<String> {
    public StringDeleteForm(String id) {
        super(id);
    }
}
