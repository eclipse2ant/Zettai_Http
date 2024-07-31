package com.ubertob.fotf.zettai.domain

import com.ubertob.fotf.zettai.commands.ToDoListCommand
import com.ubertob.fotf.zettai.commands.ToDoListCommandHandler
import com.ubertob.fotf.zettai.events.ToDoListEvent
import com.ubertob.fotf.zettai.fp.EventPersister

interface ZettaiHub {
    fun getList(user: User, listName: ListName): ToDoList?
//    fun addItemToList(user: User, listName: ListName, item: ToDoItem): ToDoList?
    fun getLists(user: User): List<ListName>?
    fun handle(command: ToDoListCommand): ToDoListCommand?
}

interface ToDoListFetcher {

    fun get(user: User, listName: ListName): ToDoList?

    fun getAll(user: User): List<ListName>?

}

class ToDoListHub(val fetcher: ToDoListUpdatableFetcher,
                  val commandHandler: ToDoListCommandHandler,
                  val persistEvents: EventPersister<ToDoListEvent>
    ) : ZettaiHub {

    override fun handle(command: ToDoListCommand): ToDoListCommand? =
        commandHandler(command)
            ?.let( persistEvents )
            ?.let{ command } //returning the command
    override fun getList(user: User, listName: ListName): ToDoList? =
        fetcher.get(user, listName)


    override fun getLists(user: User): List<ListName>? =
        fetcher.getAll(user)
}

    private fun List<ToDoItem>.replaceItem(item: ToDoItem)
            = filterNot { it.description == item.description } + item

