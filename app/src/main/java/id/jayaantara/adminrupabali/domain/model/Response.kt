package id.jayaantara.adminrupabali.domain.model

import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto
import id.jayaantara.adminrupabali.data.remote.dto.ResponseDto

data class Response(
    val message: String
)

fun ResponseDto.toResponse(): Response {
    return Response(
        message = message
    )
}
