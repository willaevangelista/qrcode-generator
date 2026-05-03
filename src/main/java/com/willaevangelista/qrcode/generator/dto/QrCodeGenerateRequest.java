package com.willaevangelista.qrcode.generator.dto;

/**
 * Immutable Data Transfer Object (DTO) representing the request payload for QR Code generation.
 *
 * This record carries the input data sent by the client to the QR Code generation endpoint.
 * As a Java {@code record}, it automatically provides a canonical constructor, accessor methods, and implementations
 * of {@code equals()}, {@code hashCode()}, and {@code toString()}, without requiring any boilerplate code.
 *
 * Unlike class-based DTOs annotated with {@code @Data} (Lombok), records are inherently immutable — they have no
 * setters, cannot be subclassed, and do not allow instance fields to be declared outside the record header.
 *
 * @param text the text or URL to be encoded into the QR Code image
 */

public record QrCodeGenerateRequest(String text) {
}
