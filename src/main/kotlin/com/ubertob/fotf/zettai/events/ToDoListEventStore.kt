package com.ubertob.fotf.zettai.events

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.ToDoListRetriever
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.fp.EntityId
import com.ubertob.fotf.zettai.fp.ToDoListId


typealias EventStreamer<E> = (EntityId) -> List<E>?
typealias EventPersister<E> = (List<E>) -> List<E>

class ToDoListEventStore(private val eventStreamer: ToDoListEventStreamer
):  ToDoListRetriever, EventPersister<ToDoListEvent> {
    private fun retrieveById(id: ToDoListId): ToDoListState? =
        eventStreamer(id)
            ?.fold()
    override fun retrieveByName(user: User,
                                listName: ListName): ToDoListState? =
        eventStreamer.retrieveIdFromName(user, listName)
            ?.let(::retrieveById)
            ?: InitialState
    fun invoke(events: Iterable<ToDoListEvent>) {
        eventStreamer.store(events)
    }

    override fun invoke(p1: List<ToDoListEvent>): List<ToDoListEvent> {
        TODO("Not yet implemented")
    }
}