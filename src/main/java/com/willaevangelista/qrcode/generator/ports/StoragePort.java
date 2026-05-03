package com.willaevangelista.qrcode.generator.ports;

/**
 * Port interface that defines the contract for file storage operations.
 *
 * This interface is part of the Hexagonal Architecture (Ports and Adapters) pattern. It acts as an outbound port,
 * decoupling the application's core logic from any specific storage implementation, such as AWS S3 or a local file system.
 *
 * Any class that provides file storage capabilities must implement this interface, allowing the storage provider to
 * be swapped without affecting the rest of the application.
 */
public interface StoragePort {
    String uploadFile(byte[] fileData, String fileName, String contentType);
}
