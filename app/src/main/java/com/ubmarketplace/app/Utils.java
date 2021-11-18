package com.ubmarketplace.app;

import java.time.Instant;

public final class Utils {
    public static Long getCurrentEpochMilli() {
        return Instant.now().toEpochMilli();
    }

    public static String formatPhoneNumber(String unFormattedPhoneNumber) {
        return unFormattedPhoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }
}
