package com.willaevangelista.qrcode.generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.willaevangelista.qrcode.generator.dto.QrCodeGenerateResponse;
import com.willaevangelista.qrcode.generator.ports.StoragePort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Service responsible for generating QR Code images and uploading them to a storage provider.
 *
 * This class encodes a given text into a QR Code image (PNG format) using the ZXing library, then uploads the
 * generated image to a storage service through the {@link StoragePort} port. The URL of the uploaded file is
 * returned as the response.
 *
 * This service follows the Hexagonal Architecture pattern, depending on the {@link StoragePort} abstraction rather
 * than a concrete storage implementation, allowing the storage provider to be swapped without modifying this class.
 *
 * @see StoragePort
 * @see QrCodeGenerateResponse
 */

@Service
public class QrCodeGeneratorService {

    private final StoragePort storage;

    /**
     * Constructs a {@code QrCodeGeneratorService} with the provided storage port.
     *
     * @param storage the storage port used to upload the generated QR Code image
     */
    public QrCodeGeneratorService(StoragePort storage) {
        this.storage = storage;
    }

    /**
     * Generates a QR Code image from the given text and uploads it to the storage provider.
     *
     * The QR Code is generated as a 200x200 PNG image. A randomly generated UUID is used as the file name to ensure
     * uniqueness. After uploading, the public URL of the stored image is returned wrapped in a
     * {@link QrCodeGenerateResponse}.
     *
     * @param text the text or URL to be encoded into the QR Code
     * @return a {@link QrCodeGenerateResponse} containing the public URL of the uploaded QR Code image
     * @throws WriterException if an error occurs during QR Code generation
     * @throws IOException     if an error occurs while writing the image to the output stream
     */
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenerateResponse(url);
    }
}
