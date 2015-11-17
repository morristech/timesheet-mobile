package com.samsistemas.timesheet.util;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jonatan.salas
 */
public class AuthUtil {
    private static final String STRING_PATTERN = "%s:%s";
    private static final String KEY = "Authorization";

    /**
     *
     * @param credentials
     * @return
     */
    public static Map<String, String> getAuthHeaders(@NonNull final String[] credentials) {
        final Map<String, String> requestParams = new HashMap<>(1);
        final String username = credentials[0];
        final String password = credentials[1];

        requestParams.put(KEY, getAuthCredential(username, password));
        return requestParams;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static String getAuthCredential(@NonNull final String username,  @NonNull final String password) {
        final String credentials = String.format(STRING_PATTERN, username, password);
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }
}