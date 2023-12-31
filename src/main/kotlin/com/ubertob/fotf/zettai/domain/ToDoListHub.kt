package com.ubertob.fotf.zettai.domain

interface ZettaiHub {
    fun getList(user: User, listName: ListName): ToDoList?
//    fun addItemToList(user: User, listName: ListName, item: ToDoItem): ToDoList?
}


class ToDoListHub(private val fetcher: ToDoListUpdatableFetcher) : ZettaiHub {

    override fun getList(user: User, listName: ListName): ToDoList? =
        fetcher(user, listName)

}

    private fun List<ToDoItem>.replaceItem(item: ToDoItem)
            = filterNot { it.description == item.description } + item

