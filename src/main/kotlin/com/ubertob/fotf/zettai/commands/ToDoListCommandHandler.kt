package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.evants.ToDoListEvent


typealias CommandHandler<CMD, EVENT> = (CMD) -> List<EVENT>?
class ToDoListCommandHandler:
    CommandHandler<ToDoListCommand, ToDoListEvent> {
    override fun invoke(command: ToDoListCommand) = TODO()
}