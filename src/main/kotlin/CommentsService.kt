import NoteService.createComment
import NoteService.notes

class CommentNotFoundException(message: String) : RuntimeException(message)


object CommentsService : CrudService<Comments> {
    private var lastIdComment = 0

    fun clean() {
        lastIdComment = 0
    }

    override fun add(item: Comments): Boolean {
        lastIdComment++
        val updateComm = item.copy(id = lastIdComment)
        createComment(updateComm)
        return true
    }

    override fun delete(id: Int): Boolean {
        for (note in notes) {
            for ((index, comment) in note.comments.withIndex()) {
                if (comment.id == id && !comment.isDelete) {
                    val updateComm = comment.copy(isDelete = true)
                    note.comments[index] = updateComm
                    return true
                }
            }
        }
        throw CommentNotFoundException("not comments with id: $id")
    }

    override fun edit(item: Comments): Boolean {
        for (note in notes) {
            if (note.id == item.noteId) {
                for ((index, comment) in note.comments.withIndex()) {
                    if (comment.id == item.id && !comment.isDelete) {
                        note.comments[index] = item
                        return true
                    }
                }
            }
        }
        throw CommentNotFoundException("not comment with id: ${item.id}")
    }

    override fun read(): MutableList<Comments> {
        val commentsForGet = mutableListOf<Comments>()
        for (note in notes) {
            if (!note.isDelete) {
                for (comment in note.comments) {
                    if (!comment.isDelete) {
                        commentsForGet += comment
                    }
                }
            }
        }
        return commentsForGet
    }

    override fun getById(id: Int): Comments {
        for (note in notes) {
            if (!note.isDelete) {
                for (comment in note.comments) {
                    if (comment.id == id && !comment.isDelete) {
                        return comment
                    }
                }
            }
        }
        throw CommentNotFoundException("not comment with id: $id")
    }

    fun getByIdNote(noteId: Int): MutableList<Comments> {
        val commentsForGet = mutableListOf<Comments>()
        for (note in notes) {
            if (noteId == note.id && !note.isDelete) {
                for (comment in note.comments) {
                    if (!comment.isDelete) {
                        commentsForGet += comment
                    }
                }
                return commentsForGet
            }
        }
        throw NoteNotFoundException("not note with id: $noteId")
    }

    override fun restore(id: Int): Boolean {
        for (note in notes) {
            for ((index, comment) in note.comments.withIndex()) {
                if (comment.id == id && comment.isDelete) {
                    val updateComm = comment.copy(isDelete = false)
                    note.comments[index] = updateComm
                    return true
                }
            }
        }
        return false
    }

}