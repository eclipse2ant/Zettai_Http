package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.evants.ToDoListEvent
import com.ubertob.fotf.zettai.evants.ToDoListState


typealias CommandHandler<CMD, EVENT> = (CMD) -> List<EVENT>?
class ToDoListCommandHandler:
    CommandHandler<ToDoListCommand, ToDoListEvent> {
    override fun invoke(command: ToDoListCommand) = TODO()
}

typealias ToDoListRetriever =
            (user: User, listName: ListName) -> ToDoListState?