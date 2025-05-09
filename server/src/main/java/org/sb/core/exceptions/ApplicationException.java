package org.sb.core.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

public class ApplicationException extends RuntimeException {

    private final int statusCode;
    private final Object detail;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApplicationException(String message) {
        this(message, 400, null);
    }

    public ApplicationException(String message, int statusCode) {
        this(message, statusCode, null);
    }

    public ApplicationException(String message, int statusCode, Object detail) {
        super(message);
        this.statusCode = statusCode;
        this.detail = detail;
    }

    public Response toResponse() {
        Object formattedDetail;

        if (detail instanceof String) {
            try {
                formattedDetail = objectMapper.readValue((String) detail, Object.class);
            } catch (Exception e) {
                formattedDetail = detail;
            }
        } else {
            formattedDetail = detail;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("message", getMessage());
        data.put("code", statusCode);
        Response.Status status = Response.Status.fromStatusCode(statusCode);
        data.put("reason", status != null ? status.getReasonPhrase() : "Internal Server Error");
        data.put("detail", formattedDetail);

        return Response
                .status(statusCode)
                .entity(data)
                .build();
    }
}
