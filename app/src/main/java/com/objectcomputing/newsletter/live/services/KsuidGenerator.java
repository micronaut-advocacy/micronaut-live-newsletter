package com.objectcomputing.newsletter.live.services;

import com.amirkhawaja.Ksuid;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@Singleton
public class KsuidGenerator implements IdGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(KsuidGenerator.class);
    @Override
    @NonNull
    public Optional<String> generate() {
        try {
            return Optional.of(new Ksuid().generate());
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("IOException generating a KS-UID");
            }
        }
        return Optional.empty();
    }
}
