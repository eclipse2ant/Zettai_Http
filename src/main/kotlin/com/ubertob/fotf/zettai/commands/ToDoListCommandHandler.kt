package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.domain.ToDoListRetriever
import com.ubertob.fotf.zettai.domain.ToDoListUpdatableFetcher
import com.ubertob.fotf.zettai.events.InitialState
import com.ubertob.fotf.zettai.events.ListCreated
import com.ubertob.fotf.zettai.events.ToDoListEvent
import com.ubertob.fotf.zettai.events.ToDoListState


class ToDoListCommandHandler(
    private val entityRetriever: ToDoListRetriever,
    private val readModel: ToDoListUpdatableFetcher //temporary needed to update the read model
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
                        listOf(ListCreated(id, user, name))
                    }
                    else -> null //command fail
                }
            }
}

