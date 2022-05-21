interface CrudService<T> {
    fun add(item: T): Boolean
    fun delete(id: Int): Boolean
    fun edit(item: T): Boolean
    fun read(): List<T>
    fun getById(id: Int): T
    fun restore(id: Int): Boolean
}