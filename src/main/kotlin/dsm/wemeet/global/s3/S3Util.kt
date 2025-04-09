package dsm.wemeet.global.s3

import dsm.wemeet.global.s3.exception.BadFileExtException
import dsm.wemeet.global.s3.exception.EmptyFileException
import dsm.wemeet.global.s3.exception.FileDeleteException
import dsm.wemeet.global.s3.exception.FileUploadException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.time.Duration
import java.util.Locale
import java.util.UUID

@Service
class S3Util(
    private val s3Client: S3Client,
) {

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucketName: String

    @Value("\${cloud.aws.s3.exp-time}")
    lateinit var s3Exp: String

    private val s3Presigner = S3Presigner.create()


    fun upload(file: MultipartFile): String {
        val name = UUID.randomUUID()
        val ext = verificationFile(file)
        val fileName = "$name.$ext"

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(MediaType.ALL_VALUE)
            .acl(ObjectCannedACL.PRIVATE)
            .contentLength(file.size)
            .build()

        try {
            s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(file.inputStream, file.size)
            )
        } catch (e: Exception) {
            throw FileUploadException
        }

        return fileName
    }

    private fun verificationFile(file: MultipartFile): String {
        if (file.isEmpty || file.originalFilename == null) throw EmptyFileException
        val originalFilename = file.originalFilename!!
        val ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).lowercase(Locale.getDefault())

        if (!(ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "heic")) {
            throw BadFileExtException
        }

        return ext
    }

    fun delete(fileName: String) {
        try {
            s3Client.deleteObject(
                DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build()
            )
        } catch (e: Exception) {
            throw FileDeleteException
        }
    }

    fun generateUrl(fileName: String): String {
        val exp = Duration.ofSeconds(s3Exp.toLong())

        val imgRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        val freshUrl = s3Presigner.presignGetObject { builder ->
            builder.getObjectRequest(imgRequest)
                .signatureDuration(exp)
        }

        return freshUrl.url().toString()
    }
}
