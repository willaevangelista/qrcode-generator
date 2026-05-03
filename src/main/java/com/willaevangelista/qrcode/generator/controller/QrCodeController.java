package com.willaevangelista.qrcode.generator.controller;

import com.willaevangelista.qrcode.generator.dto.QrCodeGenerateRequest;
import com.willaevangelista.qrcode.generator.dto.QrCodeGenerateResponse;
import com.willaevangelista.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling HTTP requests related to QR Code generation.
 *
 * This class acts as the entry point of the application, receiving incoming requests and delegating the business
 * logic to the {@link QrCodeGeneratorService} service layer. It follows the separation of concerns principle,
 * keeping the controller focused solely on request handling and response formatting.
 *
 * All endpoints in this controller are prefixed with {@code /qrcode}, as defined by the {@code @RequestMapping}
 * annotation. Responses are automatically serialized to JSON by the {@code @RestController} annotation.
 *
 * @see QrCodeGeneratorService
 */

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    /**
     * Constructs a {@code QrCodeController} with the provided QR Code generator service.
     *
     * @param qrCodeService the service responsible for generating and uploading QR Code images
     */
    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.qrCodeGeneratorService = qrCodeService;
    }

    /**
     * Handles POST requests to {@code /qrcode} and generates a QR Code image from the provided text.
     *
     * The text received in the request body is passed to the service layer, which encodes it into a QR Code image
     * and uploads it to the storage provider. On success, the public URL of the generated image is returned with
     * HTTP status {@code 200 OK}.
     *
     * If any exception occurs during the process, the error is logged to the standard output and an HTTP {@code 500
     * Internal Server Error} response is returned.
     *
     * @param request the request body containing the text to be encoded into the QR Code
     * @return a {@link ResponseEntity} containing the {@link QrCodeGenerateResponse} with the public URL of the
     * generated QR Code image, or {@code 500} if an error occurs
     */
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request){
        try {
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
