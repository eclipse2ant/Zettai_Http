package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.evants.InitialState
import com.ubertob.fotf.zettai.evants.ListCreated
import com.ubertob.fotf.zettai.evants.ToDoListEvent
import com.ubertob.fotf.zettai.evants.ToDoListState


class ToDoListCommandHandler(val entityRetriever: ToDoListRetriever
): (ToDoListCommand) -> List<ToDoListEvent>? {
    override fun invoke(command: ToDoListCommand) : List<ToDoListEvent>? =
        when (command) {
            is CreateToDoList -> command.execute()
            else -> null //ignore for the moment
        }
    private fun CreateToDoList.execute(): List<ToDoListEvent>? =
        entityRetriever.retrieveByName(user, name)
            ?.let { listState ->
                when (listState) {
                    InitialState -> {
                        ListCreated(id, user, name).toList()
                    }
                    else -> null //command fail
                }
            }
}

typealias ToDoListRetriever =
            (user: User, listName: ListName) -> ToDoListState?