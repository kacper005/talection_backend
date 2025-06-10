package com.talection.talection.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Utility class for verifying Google ID tokens.
 * This class uses the Google API client library to verify the authenticity of ID tokens
 * issued by Google, ensuring that they are valid and intended for the specified client ID.
 */
public class GoogleTokenVerifier {

    @Value("${google.client-id}")
    private String CLIENT_ID;

    public GoogleIdToken.Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
        var transport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new SecurityException("Invalid ID token");
        }
    }
}
