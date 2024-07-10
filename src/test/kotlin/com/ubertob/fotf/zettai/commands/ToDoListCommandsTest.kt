package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.domain.randomListName
import com.ubertob.fotf.zettai.domain.randomUser
import com.ubertob.fotf.zettai.events.InitialState
import com.ubertob.fotf.zettai.events.ListCreated
import org.junit.jupiter.api.Test
import strikt.api.expectThat

internal class ToDoListCommandsTest {
    val fakeRetriever: ToDoListRetriever = {
            (user: User, listName: ListName) -> InitialState
    }
    @Test
    fun `CreateToDoList generate the correct event`() {
        val cmd = CreateToDoList(randomUser(), randomListName())
        val handler = ToDoListCommandHandler(fakeRetriever)
        val res = handler(cmd)?.single()
        expectThat(res).isEqualTo(
            ListCreated(cmd.id, cmd.user, cmd.name)
        )
    }
}