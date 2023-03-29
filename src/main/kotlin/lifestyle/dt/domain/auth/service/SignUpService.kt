package lifestyle.dt.domain.auth.service

import lifestyle.dt.domain.auth.presentation.data.dto.SignUpDto
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface SignUpService {

    fun execute(signUpDto: SignUpDto, file: MultipartFile): UUID

}