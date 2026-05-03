package com.willaevangelista.qrcode.generator.dto;

/**
 * Immutable Data Transfer Object (DTO) representing the response payload after a QR Code generation request.
 *
 * This record carries the output data returned to the client upon successful QR Code generation, containing the
 * public URL where the generated QR Code image can be accessed.
 *
 * As a Java {@code record}, it automatically provides a canonical constructor, accessor methods, and implementations
 * of {@code equals()}, {@code hashCode()}, and {@code toString()}, without requiring any boilerplate code.
 *
 * Unlike class-based DTOs annotated with {@code @Data} (Lombok), records are inherently immutable — they have no
 * setters, cannot be subclassed, and do not allow instance fields to be declared outside the record header.
 *
 * @param url the public URL of the uploaded QR Code image stored in the storage provider
 */

public record QrCodeGenerateResponse(String url) {
}
