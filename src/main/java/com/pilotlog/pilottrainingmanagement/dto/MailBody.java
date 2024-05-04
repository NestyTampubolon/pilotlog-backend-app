package com.pilotlog.pilottrainingmanagement.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
