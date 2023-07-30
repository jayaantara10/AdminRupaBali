package id.jayaantara.adminrupabali.view.ui.data.dummy

import androidx.compose.ui.graphics.painter.Painter

data class ArtworkCategoryItem (
    val category: String,
    val imageUrl: String,
    val description: String,
    val history: String,
    val function: String,
    val fineArtCategory: String,
    val validationStatus: String,
    val createdAt: String,
    val updatedAt: String,
)

val artworkCategoryItems = listOf(
    ArtworkCategoryItem(
        category = "Kawisesan",
        imageUrl = "https://denpasarnow.com/wp-content/uploads/2021/08/kekereb2.jpg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ut turpis quis tortor luctus euismod vitae aliquam massa. Nullam lobortis vitae est non dictum. Morbi tempor nunc aliquam sapien convallis, nec luctus nunc bibendum. Vivamus eu elit dolor. Etiam id velit pharetra, placerat justo nec, rutrum libero. Mauris vitae venenatis augue, ut interdum sapien. Suspendisse a facilisis velit, eget dapibus quam. Cras facilisis tincidunt massa sit amet suscipit. Nunc volutpat ipsum tincidunt mauris hendrerit sodales.\n" +
                "\n" +
                "Praesent sit amet vulputate metus. Nulla id cursus quam. Quisque in cursus mi. Aliquam gravida diam diam, vitae eleifend lorem pharetra nec. Morbi turpis augue, sagittis in feugiat quis, tempor cursus diam. Suspendisse eget enim ut dolor sagittis efficitur a et orci. Duis egestas ultricies metus non condimentum. Praesent eu sapien eget ligula volutpat pharetra et posuere arcu. Aliquam auctor sem nulla, ut lobortis nunc condimentum ut. Vestibulum a condimentum tellus, id facilisis neque. Fusce vestibulum mauris vel est ornare, semper congue arcu vestibulum. Donec facilisis purus hendrerit ligula dapibus dignissim. Phasellus et suscipit augue.\n" +
                "\n" +
                "Aliquam erat volutpat. Sed auctor purus pharetra, scelerisque justo vitae, ullamcorper purus. Cras sit amet tellus mollis, facilisis nulla quis, varius sapien. Aliquam erat volutpat. Praesent vel dapibus nibh. Proin sit amet ante et sem porttitor iaculis non nec libero. Sed id mollis justo. Duis posuere eros nunc, eu rhoncus leo cursus sit amet. Aliquam fermentum purus eu nisl mattis, sit amet fermentum diam vestibulum. Quisque vitae maximus velit, vitae feugiat ex. Ut eget consequat leo.\n" +
                "\n" +
                "Cras posuere pellentesque nulla, id vulputate neque tempor a. Phasellus nec ipsum risus. Morbi eget erat magna. Phasellus ut nibh vel turpis dapibus tempus. Etiam a ipsum nec sapien facilisis fermentum in quis ligula. Etiam et lorem et sem dapibus mattis eu vitae nulla. Nulla aliquet eu urna quis pharetra. Maecenas porta, quam eu blandit leo.",
        history = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ut turpis quis tortor luctus euismod vitae aliquam massa. Nullam lobortis vitae est non dictum. Morbi tempor nunc aliquam sapien convallis, nec luctus nunc bibendum. Vivamus eu elit dolor. Etiam id velit pharetra, placerat justo nec, rutrum libero. Mauris vitae venenatis augue, ut interdum sapien. Suspendisse a facilisis velit, eget dapibus quam. Cras facilisis tincidunt massa sit amet suscipit. Nunc volutpat ipsum tincidunt mauris hendrerit sodales.\n" +
                "\n" +
                "Praesent sit amet vulputate metus. Nulla id cursus quam. Quisque in cursus mi. Aliquam gravida diam diam, vitae eleifend lorem pharetra nec. Morbi turpis augue, sagittis in feugiat quis, tempor cursus diam. Suspendisse eget enim ut dolor sagittis efficitur a et orci. Duis egestas ultricies metus non condimentum. Praesent eu sapien eget ligula volutpat pharetra et posuere arcu. Aliquam auctor sem nulla, ut lobortis nunc condimentum ut. Vestibulum a condimentum tellus, id facilisis neque. Fusce vestibulum mauris vel est ornare, semper congue arcu vestibulum. Donec facilisis purus hendrerit ligula dapibus dignissim. Phasellus et suscipit augue.\n" +
                "\n" +
                "Aliquam erat volutpat. Sed auctor purus pharetra, scelerisque justo vitae, ullamcorper purus. Cras sit amet tellus mollis, facilisis nulla quis, varius sapien. Aliquam erat volutpat. Praesent vel dapibus nibh. Proin sit amet ante et sem porttitor iaculis non nec libero. Sed id mollis justo. Duis posuere eros nunc, eu rhoncus leo cursus sit amet. Aliquam fermentum purus eu nisl mattis, sit amet fermentum diam vestibulum. Quisque vitae maximus velit, vitae feugiat ex. Ut eget consequat leo.\n" +
                "\n" +
                "Cras posuere pellentesque nulla, id vulputate neque tempor a. Phasellus nec ipsum risus. Morbi eget erat magna. Phasellus ut nibh vel turpis dapibus tempus. Etiam a ipsum nec sapien facilisis fermentum in quis ligula. Etiam et lorem et sem dapibus mattis eu vitae nulla. Nulla aliquet eu urna quis pharetra. Maecenas porta, quam eu blandit leo.",
        function = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dictum dapibus scelerisque. Etiam et leo a ante porttitor tristique. Mauris feugiat tempor interdum. Nunc consequat massa nisi, eu volutpat neque tempor vel. Maecenas ultricies, tellus non venenatis rutrum, sapien tellus posuere metus, at lacinia nisl magna sit amet diam. Vivamus malesuada nunc et dui ultricies, suscipit congue elit sollicitudin. In elementum porta metus at euismod. Nam posuere in justo vel sollicitudin.\n" +
                "\n" +
                "Sed placerat ante sed urna feugiat feugiat. Maecenas turpis lectus, suscipit et elit quis, cursus efficitur erat. Fusce rutrum justo sit amet nisl interdum euismod. Cras congue mollis lobortis. Nam semper ac elit sit amet sagittis. Integer finibus enim nisi, non rhoncus quam accumsan sit amet. Duis ut nisl fringilla, luctus leo quis, rutrum dui. Duis consequat eu neque sit amet blandit. Vivamus vitae interdum diam. Fusce ipsum nisl, dictum id facilisis et, mollis vel justo. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aenean facilisis facilisis mauris, sit amet eleifend lectus volutpat lacinia. Donec vel dolor eget sem pharetra tempus sed a sem. Vestibulum est sapien, pretium nec enim eget, aliquam suscipit odio. Maecenas a semper leo, id pretium nulla. Donec maximus vulputate tristique.\n" +
                "\n" +
                "Suspendisse posuere consectetur lectus nec hendrerit. Aenean nibh tellus, tempor vel posuere nec, porta vel enim. Nam eu mi rutrum, malesuada augue id, facilisis elit. Sed pharetra vel sapien ac ac.",
        fineArtCategory = "Rerajahan",
        validationStatus = "VALID",
        createdAt = "20-12-2022",
        updatedAt = "20-12-2022"),
)
