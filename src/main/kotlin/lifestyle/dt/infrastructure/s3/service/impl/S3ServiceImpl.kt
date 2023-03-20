package lifestyle.dt.infrastructure.s3.service.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import lifestyle.dt.infrastructure.s3.config.S3BucketProperties
import lifestyle.dt.infrastructure.s3.service.S3Service
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Component
class S3ServiceImpl(
    private val amazonS3: AmazonS3,
    private val s3BucketProperties: S3BucketProperties
): S3Service {

    override fun uploadImage(file: MultipartFile, dirName: String): String {
        val fileName = fileNameToUUID(file.originalFilename.toString())
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType

        runCatching {
            amazonS3.putObject(
                PutObjectRequest(s3BucketProperties.bucket, dirName + fileName, file.inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
        }.onFailure {
            throw java.lang.IllegalArgumentException("파일 업로드에 실패했습니다.")
        }

        return fileName
    }

    override fun deleteImage(fileName: String) =
        amazonS3.deleteObject(DeleteObjectRequest(s3BucketProperties.bucket, fileName))

    private fun fileNameToUUID(fileName: String): String =
        UUID.randomUUID().toString().plus(getFileExtension(fileName))

    private fun getFileExtension(fileName: String): String =
        try {
            fileName.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw IllegalArgumentException()
        }

}