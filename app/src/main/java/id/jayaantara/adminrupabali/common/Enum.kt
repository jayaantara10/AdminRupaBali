package id.jayaantara.adminrupabali.common

enum class AdminRole(val role: String){
    SUPER_ADMIN("super_admin"),
    ADMIN("admin"),
    VALIDATOR("validator"),
    UNDEFINE("undefine")
}

enum class UserRole(val role: String){
    VISITOR("visitor"),
    COLLECTOR("collector"),
    ARTIST("artist"),
    UNDEFINE("undefine")
}