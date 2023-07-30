package id.jayaantara.adminrupabali.view.ui.data.dummy

data class ChatItem(
    val name: String,
    val profilePicture: String,
    val chat: String,
    val date: String,
    val unreadCounter: Int
)

val chatItems = listOf<ChatItem>(
    ChatItem(
        name = "Gusti Made",
        profilePicture = "https://www.beritabali.com/public/uploads/berita/Berita_221509090919_seniman-i-gusti-made-deblog-melukis-wayang-dari-sudut-yang-berbeda.webp",
        chat = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in egestas ligula. Nullam sed consectetur urna, vitae lobortis risus. Aliquam massa nunc.",
        date = "21/11/2022",
        unreadCounter = 2
    )
)
