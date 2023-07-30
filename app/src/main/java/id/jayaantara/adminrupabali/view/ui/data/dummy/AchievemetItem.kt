package id.jayaantara.adminrupabali.view.ui.data.dummy

data class AchievementItem(
    val title: String,
    val date: String,
    val documentation: String,
    val certificate: String,
    val description: String,
)

val dummyAchievementItems = listOf(
    AchievementItem(
        title = "Juara I Lomba Karya Seni Rupa",
        date = "20 Oktober 2001",
        documentation = "https://akcdn.detik.net.id/community/media/visual/2022/01/03/penjualan-karya-seniman-bali-lewat-nft_169.jpeg?w=700&q=90",
        certificate = "",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras at libero nec tellus tempor tempus fringilla at ipsum. Ut nec fermentum dolor. Mauris vel lobortis nunc, ac sodales libero. Curabitur vestibulum auctor nulla, ac semper dolor dictum eget. Quisque euismod, quam id laoreet pretium, augue mauris varius odio, eu bibendum enim nunc id augue. Nunc id dui quis nunc consectetur vehicula vitae eget nisi. Quisque convallis sit amet justo vel faucibus. Phasellus dignissim tellus diam, vel nulla."
    )
)
