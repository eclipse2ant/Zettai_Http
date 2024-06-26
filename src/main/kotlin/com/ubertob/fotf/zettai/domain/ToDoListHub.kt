package com.ubertob.fotf.zettai.domain

interface ZettaiHub {
    fun getList(user: User, listName: ListName): ToDoList?
    fun addItemToList(user: User, listName: ListName, item: ToDoItem): ToDoList?
    fun getLists(user: User): List<ListName>?
}


class ToDoListHub(val fetcher: ToDoListUpdatableFetcher) : ZettaiHub {

    override fun getList(user: User, listName: ListName): ToDoList? =
        fetcher(user, listName)

    override fun addItemToList(user: User,
                               listName: ListName, item: ToDoItem): ToDoList? =
        fetcher(user, listName)?.run {
            val newList = copy(items = items
                .filterNot { it.description == item.description } + item)
            fetcher.assignListToUser (user, newList)
        }
    override fun getLists(user: User): List<ListName>? =
        fetcher.getAll(user)
}

    private fun List<ToDoItem>.replaceItem(item: ToDoItem)
            = filterNot { it.description == item.description } + item

