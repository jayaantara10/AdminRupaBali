package id.jayaantara.adminrupabali.view.ui.data.dummy

data class MessageItem(
    val message: String,
    val date: String,
    val status: String,
    val isSender: Boolean
)

val dummyMessageItems = listOf(
    MessageItem(
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in egestas ligula. Nullam sed consectetur urna, vitae lobortis risus. Aliquam massa nunc.",
        date = "12.00",
        status = "READ",
        isSender = true
    ),

    MessageItem(
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in egestas ligula. Nullam sed consectetur urna, vitae lobortis risus. Aliquam massa nunc.",
        date = "12.13",
        status = "READ",
        isSender = false
    ),
)
