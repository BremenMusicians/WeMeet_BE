package dsm.wemeet.global.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import dsm.wemeet.global.s3.exception.BadFileExtException
import dsm.wemeet.global.s3.exception.EmptyFileException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.Locale
import java.util.UUID

@Service
class S3Util(
    private val amazonS3: AmazonS3
) {

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucketName: String

    @Value("\${cloud.aws.s3.exp-time}")
    lateinit var s3Exp: String

    fun upload(file: MultipartFile): String {
        val name = UUID.randomUUID()
        val ext = verificationFile(file)
        val fileName = "$name.$ext"

        val metadata = ObjectMetadata().apply {
            contentType = MediaType.IMAGE_PNG_VALUE
            contentLength = file.size
        }

        file.inputStream.use {
            amazonS3.putObject(
                PutObjectRequest(bucketName, "$fileName", it, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
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
        amazonS3.deleteObject(bucketName, fileName)
    }
}
