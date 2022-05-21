import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @After
    fun clean() {
        NoteService.clean()
        CommentsService.clean()
    }

    @Test
    fun addNote() {
        val resultNote = NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        assertTrue(resultNote)
    }

    @Test
    fun createCommentIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        val resultComment = CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"

            )
        )
        assertTrue(resultComment)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentIsFalse() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                5,
                1,
                "TextComment"

            )
        )
    }

    @Test
    fun deleteNoteIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        val resultDelete = NoteService.delete(1)
        assertTrue(resultDelete)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteIsFalseWithOtherIdNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        NoteService.delete(2)

    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteIsFalseWithDeleteStatusNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )
        NoteService.delete(1)

    }

    @Test
    fun deleteCommentIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.createComment(
            Comments(
                1,
                1,
                "TextComment",

                )
        )
        val resultComment = CommentsService.delete(1)
        assertTrue(resultComment)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentIsFalseWithOtherIdComm() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.createComment(
            Comments(
                1,
                1,
                "TextComment",

                )
        )
        CommentsService.delete(2)

    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentIsFalseWithDeleteStatusComm() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.createComment(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true

            )
        )
        CommentsService.delete(1)

    }


    @Test
    fun editNoteIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        val resultEdit = NoteService.edit(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        assertTrue(resultEdit)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNoteIsFalseWithDeleteStatusNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )
        NoteService.edit(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNoteIsFalseWithOtherIdNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        NoteService.edit(
            Notes(
                3,
                "TitleNote",
                "TextNote",
            )
        )
    }

    @Test
    fun editCommentIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextCommentEdit"
            )
        )

        val resultEdit = CommentsService.edit(
            Comments(
                1,
                1,
                "TextCommentEdit"
            )
        )

        assertTrue(resultEdit)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentIsFalseWithOtherIdNotes() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextCommentEdit"
            )
        )

        CommentsService.edit(
            Comments(
                2,
                1,
                "TextCommentEdit"
            )
        )
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentIsFalseWithOtherIdComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextCommentEdit"
            )
        )

        CommentsService.edit(
            Comments(
                1,
                2,
                "TextCommentEdit"
            )
        )
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentIsFalseWithDeleteStatusOfComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextCommentEdit",
                true
            )
        )

        CommentsService.edit(
            Comments(
                1,
                1,
                "TextCommentEdit"
            )
        )
    }

    @Test
    fun getNotesReturnMutableList() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote2",
            )
        )

        val actualResult = NoteService.read()
        val expectedResult = mutableListOf(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            ),
            Notes(
                2,
                "TitleNote2",
                "TextNote2",
            )
        )

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun getNotesWithDeleteStatusNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )
        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote2",
                isDelete = true
            )
        )

        val actualResult = NoteService.read()
        val expectedResult = mutableListOf(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun getNoteByIdReturnNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        val actualResult = NoteService.getById(1)
        val expectedResult = Notes(
            1,
            "TitleNote",
            "TextNote",
        )
        assertEquals(expectedResult, actualResult)

    }

    @Test(expected = NoteNotFoundException::class)
    fun getNoteByIdWithOtherIdNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.getById(2)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNoteByIdWithDeleteStatusNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )

        NoteService.getById(1)
    }

    @Test
    fun getComments() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )

        val actualResult = CommentsService.getByIdNote(1)
        val expectedResult = mutableListOf(
            Comments(
                1,
                1,
                "TextComment"
            ), Comments(
                1,
                2,
                "TextComment2"
            )
        )

        assertEquals(expectedResult, actualResult)

    }

    @Test(expected = NoteNotFoundException::class)
    fun getCommentsIsFalseWithOtherIdNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )

        CommentsService.getByIdNote(6)


    }

    @Test(expected = NoteNotFoundException::class)
    fun getCommentsIsFalseWithDeleteStatusNote() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )

        CommentsService.getByIdNote(1)

    }

    @Test
    fun getCommentsIsTrueWithDeleteStatusComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2",
                isDelete = false
            )
        )

        val actualResult = CommentsService.getByIdNote(1)
        val expectedResult = mutableListOf(
            Comments(
                1,
                2,
                "TextComment2",
            )
        )


        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun getCommentIsTrueByIdComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote",
                isDelete = true
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2",
            )
        )
        CommentsService.add(
            Comments(
                1,
                3,
                "TextComment3",
            )
        )

        val actualResult = CommentsService.getById(3)
        val expectedResult =
            Comments(
                1,
                3,
                "TextComment3",
            )


        assertEquals(expectedResult, actualResult)

    }


    @Test(expected = CommentNotFoundException::class)
    fun getCommentIsFalseByIdComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote",
                isDelete = true
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2",
            )
        )
        CommentsService.add(
            Comments(
                1,
                3,
                "TextComment2",
            )
        )

        CommentsService.getById(4)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getCommentIsFalseByStatusComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote",
                isDelete = true
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2",
            )
        )

        CommentsService.getById(1)
    }

    @Test
    fun getAllComments() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        NoteService.add(
            Notes(
                2,
                "TitleNote2",
                "TextNote",
                isDelete = true
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment",
                isDelete = true
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2",
            )
        )

        val actualResult = CommentsService.read()
        val expectedResult = mutableListOf(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )


        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun restoreCommentIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )
        CommentsService.delete(2)
        val result = CommentsService.restore(2)

        assertTrue(result)
    }

    @Test
    fun restoreCommentIsFalseWithOtherIdComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )
        CommentsService.delete(1)
        val result = CommentsService.restore(3)

        assertFalse(result)
    }

    @Test
    fun restoreCommentIsFalseWithNoDeleteStatusComment() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
            )
        )

        CommentsService.add(
            Comments(
                1,
                1,
                "TextComment"
            )
        )
        CommentsService.add(
            Comments(
                1,
                2,
                "TextComment2"
            )
        )

        val result = CommentsService.restore(2)

        assertFalse(result)
    }

    @Test
    fun restoreNoteIsTrue() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )

        val result = NoteService.restore(1)
        assertTrue(result)
    }

    @Test
    fun restoreNoteIsFalseWithOtherId() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = true
            )
        )

        val result = NoteService.restore(2)
        assertFalse(result)
    }

    @Test
    fun restoreNoteIsFalse() {
        NoteService.add(
            Notes(
                1,
                "TitleNote",
                "TextNote",
                isDelete = false
            )
        )

        val result = NoteService.restore(1)
        assertFalse(result)
    }
}