package com.willaevangelista.qrcode.generator.infraestructure;

import com.willaevangelista.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


/**
 * AWS S3 adapter that implements the {@link StoragePort} port for file storage operations.
 *
 * This class is part of the Hexagonal Architecture (Ports and Adapters) pattern, acting as an outbound adapter that
 * connects the application's core logic to AWS S3 as the concrete storage provider.
 *
 * The AWS region and bucket name are injected from the application's configuration properties ({@code aws.s3.region}
 * and {@code aws.s3.bucket-name}), and the {@link S3Client} is initialized during construction based on those values.
 *
 * @see StoragePort
 */
@Component
public class S3StorageAdapter implements StoragePort {

    private final S3Client s3Client;

    private final String bucketName;

    private final String region;

    /**
     * Constructs an {@code S3StorageAdapter} and initializes the {@link S3Client} using the provided AWS region and
     * bucket name from the application properties.
     *
     * @param region     the AWS region where the S3 bucket is hosted (e.g., {@code "us-east-1"}),
     *                   injected from the {@code aws.s3.region} property
     * @param bucketName the name of the S3 bucket where files will be uploaded,
     *                   injected from the {@code aws.s3.bucket-name} property
     */
    public S3StorageAdapter(@Value("${aws.s3.region}") String region,
                            @Value("${aws.s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .build();
    }

    /**
     * Uploads a file to the configured AWS S3 bucket and returns its public URL.
     *
     * The file is uploaded synchronously using a {@link PutObjectRequest} with the specified file name as the S3
     * object key and the given content type as metadata.
     * After a successful upload, the public URL is constructed in the format:
     * {@code https://<bucketName>.s3.<region>.amazonaws.com/<fileName>}.
     *
     * @param fileData    the raw byte array containing the file content to be uploaded
     * @param fileName    the name to assign to the object in the S3 bucket, used as the object key
     * @param contentType the MIME type of the file (e.g., {@code "image/png"}, {@code "application/pdf"})
     * @return the public URL of the uploaded file in the S3 bucket
     */
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, fileName);
    }
}
