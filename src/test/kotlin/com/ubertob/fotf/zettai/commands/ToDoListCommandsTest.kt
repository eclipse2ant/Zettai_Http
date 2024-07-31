package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.*
import com.ubertob.fotf.zettai.events.*
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

internal class ToDoListCommandsTest {


    @Test
    fun `CreateToDoList generate the correct event`() {
        val cmd = CreateToDoList(randomUser(), randomListName())
        val entityRetriever: ToDoListRetriever = object : ToDoListRetriever {
           override fun retrieveByName(user: User, listName: ListName) = InitialState
        }
        val handler = ToDoListCommandHandler(entityRetriever)
        val res = handler(cmd)?.single()
        expectThat(res).isEqualTo(
            ListCreated(cmd.id, cmd.user, cmd.name)
        )
    }
}