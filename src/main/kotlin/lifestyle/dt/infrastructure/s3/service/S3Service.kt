package lifestyle.dt.infrastructure.s3.service

import org.springframework.web.multipart.MultipartFile

interface S3Service {

    fun uploadImage(file: MultipartFile, dirName: String): String
    fun deleteImage(fileName: String)

}