<div align='center' id='top'>

# QR Code Generator

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
  ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
  ![Dependabot](https://img.shields.io/badge/dependabot-025E8C?style=for-the-badge&logo=dependabot&logoColor=white)
</div>

A Spring Boot REST API that generates QR Code images from text or URLs and stores them on AWS S3, returning a public access link. This project demonstrates the integration of Google's ZXing library for QR code generation and AWS S3 for storage.

---

## Table of Contents
- [Technologies](#technologies)
- [Architecture](#architecture)
- [How It Works](#howItWorks)
- [Endpoints](#endpoints)
- [Enviroment Variables](#enviromentVariables)
- [Running Locally Without Docker](#docker)
- [AWS Setup](#awsSetup)
- [CI/CD](#ci-cd)
- [License](#license)

---

<div id='technologies'/> 

## Technologies
 
| Badge | Technology | Purpose |
|---|---|---|
| ![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) | Java 21 | Programming language |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | Spring Boot | Application framework |
| ![ZXing](https://img.shields.io/badge/ZXing-000000?style=for-the-badge&logo=qrcode&logoColor=white) | ZXing | QR Code image generation |
| ![AWS](https://img.shields.io/badge/AWS_SDK_v2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) | AWS SDK for Java v2 | S3 integration |
| ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) | Docker | Containerization |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Maven | Build and dependency management |


<div id='architecture'/> 

## Architecture

This project follows the **Hexagonal Architecture (Ports and Adapters)** pattern, keeping the core business logic decoupled from external services such as AWS S3.

```
Controller → Service → StoragePort (interface) ← S3StorageAdapter
```

---

<div id='howItWorks'/> 

## How It Works

1. The client sends a `POST /qrcode` request with a text or URL
2. The service encodes the input into a 200x200 PNG QR Code image using ZXing
3. The image is uploaded to an AWS S3 bucket via the `S3StorageAdapter`
4. The public URL of the uploaded image is returned in the response

---

<div id='endpoints'/> 

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

<div id='enviromentVariables'/> 

## Environment Variables

| Variable | Description |
|---|---|
| `AWS_ACCESS_KEY_ID` | AWS IAM user access key |
| `AWS_SECRET_ACCESS_KEY` | AWS IAM user secret key |
| `AWS_REGION` | AWS region where the S3 bucket is hosted (e.g. `us-east-1`) |
| `AWS_BUCKET_NAME` | Name of the S3 bucket used for storing QR Code images |

Create a `.env` file at the project root based on the example below:

```env
AWS_ACCESS_KEY_ID=your_access_key
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_REGION=your_region
AWS_BUCKET_NAME=your_bucket_name
```

---


<div id='endpoints'/> 

## Running Locally with Docker

```bash
# Build the image
docker build -t qrcode-generator:1.0 .

# Run the container
docker run -p 8080:8080 --env-file .env qrcode-generator:1.0
```

---

<div id='docker'/> 

## Running Locally without Docker

```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=your_region
export AWS_BUCKET_NAME=your_bucket_name

./mvnw spring-boot:run
```

---

<div id='awsSetup'/> 

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

<div id='ci-cd'/> 

## CI/CD
 
This project uses **GitHub Actions** workflows that run automatically on every Pull Request targeting the `main` branch.
 
| Badge | Workflow | File | Description |
|---|---|---|---|
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Maven Build & Test | `maven.yml` | Compiles the project and runs all tests |
| ![CodeQL](https://img.shields.io/badge/CodeQL-000000?style=for-the-badge&logo=github&logoColor=white) | CodeQL Analysis | `code_ql.yml` | Static analysis to detect vulnerabilities in the source code |
| ![Semgrep](https://img.shields.io/badge/Semgrep-1B2D3E?style=for-the-badge&logo=semgrep&logoColor=white) | Semgrep | `semgrep.yml` | Static analysis focused on security patterns and code quality |
| ![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white) | SonarQube | `sonar.yml` | Code quality and coverage analysis via SonarQube |
| ![Trivy](https://img.shields.io/badge/Trivy-1904DA?style=for-the-badge&logo=aquasecurity&logoColor=white) | Trivy | `trivy.yml` | Container image scanning for known CVEs and vulnerabilities |
| ![Dependabot](https://img.shields.io/badge/Dependabot-025E8C?style=for-the-badge&logo=dependabot&logoColor=white) | Dependabot | `dependabot.yml` | Automated dependency updates and security alerts |



<div id='license'/> 
  
## License
This project is licensed under the MIT License - see the `LICENSE` file for details.

<div align='right'>
  
  [Back to top of page ⬆️](#top)

</div>
