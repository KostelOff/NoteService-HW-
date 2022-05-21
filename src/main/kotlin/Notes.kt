data class Notes(
    val id: Int,
    val title: String,
    val text: String,
    val comments: MutableList<Comments> = mutableListOf(),
    val isDelete: Boolean = false

) 