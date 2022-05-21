class NoteNotFoundException(message: String) : RuntimeException(message)

object NoteService : CrudService<Notes> {
    val notes = mutableListOf<Notes>()
    var lastId = 0

    fun clean() {
        notes.clear()
        lastId = 0
    }

    override fun add(item: Notes): Boolean {
        lastId++
        val updatedNote = item.copy(id = lastId)
        notes.add(updatedNote)
        return true
    }

    fun createComment(comment: Comments): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == comment.noteId) {
                notes[index] = note.copy(comments = (note.comments + comment) as MutableList<Comments>)
                return true
            }
        }
        throw NoteNotFoundException("not note with id: ${comment.noteId}")
    }

    override fun delete(id: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (id == note.id && !note.isDelete) {
                val updateNote = note.copy(isDelete = true)
                notes[index] = updateNote
                return true
            }
        }
        throw NoteNotFoundException("not note with id: $id")
    }

    override fun edit(item: Notes): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == item.id && !note.isDelete) {
                notes[index] = item
                return true
            }
        }
        throw NoteNotFoundException("not not with id: ${item.id}") // можно просто false
    }

    override fun read(): MutableList<Notes> {
        val notesForGet = mutableListOf<Notes>()
        for (note in notes) {
            if (!note.isDelete) {
                notesForGet += note
            }
        }
        return notesForGet
    }

    override fun getById(id: Int): Notes {
        for (note in notes) {
            if (note.id == id && !note.isDelete) return note
        }
        throw NoteNotFoundException("not note with id: $id")
    }

    override fun restore(id: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (id == note.id && note.isDelete) {
                val updateNote = note.copy(isDelete = false)
                notes[index] = updateNote
                return true
            }
        }
        return false
    }
}