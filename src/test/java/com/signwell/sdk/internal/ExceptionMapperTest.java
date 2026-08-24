package com.signwell.sdk.internal;

import static org.junit.jupiter.api.Assertions.*;

import com.signwell.sdk.errors.*;
import com.signwell.sdk.raw.ApiException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExceptionMapperTest {
    @ParameterizedTest
    @MethodSource("statuses")
    void mapsEveryDocumentedStatusToCanonicalException(int status, Class<?> expected) {
        HttpHeaders headers =
                HttpHeaders.of(
                        Map.of("Content-Type", List.of("application/json")), (name, value) -> true);
        RuntimeException mapped =
                ExceptionMapper.map(
                        new ApiException(
                                status,
                                "generated message with ?secret=query",
                                headers,
                                "{\"secret\":\"response\"}"));
        assertInstanceOf(expected, mapped);
        assertInstanceOf(ApiStatusException.class, mapped);
        assertEquals(status, ((ApiStatusException) mapped).getStatusCode());
        assertNotNull(((ApiStatusException) mapped).getResponse());
        assertFalse(mapped.getMessage().contains("secret"));
        assertFalse(mapped.getMessage().contains("query"));
        assertInstanceOf(ApiException.class, mapped.getCause());
        if (status == 403) assertInstanceOf(ForbiddenException.class, mapped);
    }

    @Test
    void unknownStatusRemainsApiStatusException() {
        RuntimeException mapped =
                ExceptionMapper.map(new ApiException(418, "teapot", null, "private"));
        assertEquals(ApiStatusException.class, mapped.getClass());
        assertEquals(418, ((ApiStatusException) mapped).getStatusCode());
    }

    @Test
    void payloadFailuresAreNotReportedAsHttpStatusZero() {
        RuntimeException mapped =
                ExceptionMapper.map(new ApiException(new java.io.IOException("private payload")));
        assertEquals(SignWellException.class, mapped.getClass());
        assertFalse(mapped.getMessage().contains("private"));
        assertInstanceOf(ApiException.class, mapped.getCause());
    }

    private static Stream<Arguments> statuses() {
        return Stream.of(
                Arguments.of(400, BadRequestException.class),
                Arguments.of(401, AuthenticationException.class),
                Arguments.of(403, PermissionDeniedException.class),
                Arguments.of(404, NotFoundException.class),
                Arguments.of(409, ConflictException.class),
                Arguments.of(422, UnprocessableEntityException.class),
                Arguments.of(429, RateLimitException.class),
                Arguments.of(500, InternalServerException.class),
                Arguments.of(503, InternalServerException.class));
    }
}
