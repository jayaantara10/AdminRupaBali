package id.jayaantara.adminrupabali.view.ui.data.dummy

data class OwnershipHistoryItem(
    val owner: String,
    val dateRange: String,
)

val dummyOwnershipHistoryItems = listOf(
    OwnershipHistoryItem(
        owner = "Gusti Made",
        dateRange = "19 September 1995 - Sekarang"
    )
)
