<div align='center' id='top'>

# QR Code Generator

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
  ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
  ![Dependabot](https://img.shields.io/badge/dependabot-025E8C?style=for-the-badge&logo=dependabot&logoColor=white)
</div>

A Spring Boot REST API that generates QR Code images from text or URLs and stores them on AWS S3, returning a public access link. This project demonstrates the integration of Google's ZXing library for QR code generation and AWS S3 for storage.

---

## Technologies

- Java 21
- Spring Boot
- ZXing (QR Code generation)
- AWS SDK for Java v2 (S3)
- Docker
- Maven

---

## Architecture

This project follows the **Hexagonal Architecture (Ports and Adapters)** pattern, keeping the core business logic decoupled from external services such as AWS S3.

```
Controller → Service → StoragePort (interface) ← S3StorageAdapter
```

---

## How It Works

1. The client sends a `POST /qrcode` request with a text or URL
2. The service encodes the input into a 200x200 PNG QR Code image using ZXing
3. The image is uploaded to an AWS S3 bucket via the `S3StorageAdapter`
4. The public URL of the uploaded image is returned in the response

---

## Endpoints

### Generate QR Code

**POST** `/qrcode`

**Request body:**
```json
{
  "text": "https://example.com"
}
```

**Response:**
```json
{
  "url": "https://<bucket>.s3.<region>.amazonaws.com/<uuid>"
}
```

---

## Environment Variables

| Variable | Description |
|---|---|
| `AWS_REGION` | AWS region where the S3 bucket is hosted (e.g. `us-east-1`) |
| `BUCKET_NAME` | Name of the S3 bucket used for storing QR Code images |

Create a `.env` file at the project root based on the example below:

```env
AWS_REGION=us-east-1
BUCKET_NAME=your-bucket-name
```

---

## Running Locally with Docker

```bash
# Build the image
docker build -t qrcode-generator:1.0 .

# Run the container
docker run -p 8080:8080 --env-file .env qrcode-generator:1.0
```

---

## Running Locally without Docker

```bash
export AWS_REGION=us-east-1
export BUCKET_NAME=your-bucket-name

./mvnw spring-boot:run
```

---

## AWS Setup

To run this project you will need:

1. An AWS S3 bucket with **public read access** enabled
2. An IAM user with the following permissions on the bucket:
   - `s3:PutObject`
   - `s3:GetObject`
   - `s3:DeleteObject`
3. AWS credentials configured in your environment (via `~/.aws/credentials` or environment variables)

**Bucket Policy (public read):**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicRead",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::your-bucket-name/*"
    }
  ]
}
```

## License
This project is licensed under the MIT License - see the `LICENSE` file for details.

<div align='right'>
  
  [Back to top of page ⬆️](#top)

</div>
