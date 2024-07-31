package com.ubertob.fotf.zettai.commands

import com.ubertob.fotf.zettai.domain.*
import com.ubertob.fotf.zettai.events.*
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

internal class ToDoListCommandsTest {

    val noopFetcher = object : ToDoListUpdatableFetcher {
        override fun assignListToUser(user: User, list: ToDoList): ToDoList? = null //do nothing
        override fun get(user: User, listName: ListName): ToDoList? = TODO("not implemented")
        override fun getAll(user: User): List<ListName>? = TODO("not implemented")
    }
    @Test
    fun `CreateToDoList generate the correct event`() {
        val cmd = CreateToDoList(randomUser(), randomListName())
        val entityRetriever: ToDoListRetriever = object : ToDoListRetriever {
           override fun retrieveByName(user: User, listName: ListName) = InitialState
        }
        val handler = ToDoListCommandHandler(entityRetriever, noopFetcher)
        val res = handler(cmd)?.single()
        expectThat(res).isEqualTo(
            ListCreated(cmd.id, cmd.user, cmd.name)
        )
    }
}