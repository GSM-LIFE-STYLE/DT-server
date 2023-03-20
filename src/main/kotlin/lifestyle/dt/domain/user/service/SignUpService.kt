package lifestyle.dt.domain.user.service

import lifestyle.dt.domain.user.presentation.data.dto.SignUpDto
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface SignUpService {

    fun execute(signUpDto: SignUpDto, file: MultipartFile): UUID

}