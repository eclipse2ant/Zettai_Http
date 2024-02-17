package com.ubertob.fotf.zettai.domain

import org.junit.jupiter.api.Test

class ToDoListHubTest {
    fun emptyStore(): ToDoListStore = mutableMapOf()
    val fetcher = ToDoListFetcherFromMap(emptyStore())
    val hub = ToDoListHub(fetcher)
    @Test
    fun `get list by user and name`() {
        repeat(10) {
            val user = randomUser()
            val list = randomToDoList()
            fetcher.assignListToUser(user, list)
            val myList = hub.getList(user, list.listName)
            expectThat(myList).isEqualTo(list)
        }
    }
}
