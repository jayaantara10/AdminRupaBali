package id.jayaantara.adminrupabali.view.ui.data.dummy

data class Profile(
    val username: String,
    val nickname: String,
    val role: String,
    val artworkCount: String,
    val likeCount: String,
    val description: String,
    val profilePicture: String,
    val backgroudPicture: String,
)

val dummyProfile = Profile (
    username = "jhondoe",
    nickname = "Jhon Doe",
    role = "Seniman",
    artworkCount = "100",
    likeCount = "10m",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in egestas ligula. Nullam sed consectetur urna, vitae lobortis risus. Aliquam massa nunc.",
    profilePicture = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/John_Doe%2C_born_John_Nommensen_Duchac.jpg/800px-John_Doe%2C_born_John_Nommensen_Duchac.jpg",
    backgroudPicture = "https://www.dictio.id/uploads/db3342/original/3X/7/f/7f5e20719e3d85982c2c4793173e56468bd3977d.jpg",
        )