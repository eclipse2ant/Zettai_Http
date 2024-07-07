package com.ubertob.fotf.zettai.events

import com.ubertob.fotf.zettai.domain.randomItem
import com.ubertob.fotf.zettai.domain.randomListName
import com.ubertob.fotf.zettai.domain.randomUser
import com.ubertob.fotf.zettai.evants.ListCreated
import com.ubertob.fotf.zettai.evants.ToDoListEvent
import com.ubertob.fotf.zettai.evants.fold
import com.ubertob.fotf.zettai.fp.ToDoListId
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class ToDoListEventTest {

    val id = ToDoListId.mint()
    val name = randomListName()
    val user = randomUser()
    val item1 = randomItem()
    val item2 = randomItem()
    val item3 = randomItem()

    @Test
    fun `the first event create a list`() {

        val events: List<ToDoListEvent> = listOf(
            ListCreated(id, user, name)
        )

        val list = events.fold()

        expectThat(list).isEqualTo(ActiveToDoList(id, user, name, emptyList()))
    }
