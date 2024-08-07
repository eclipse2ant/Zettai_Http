package com.ubertob.fotf.zettai.events

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.ToDoListRetriever
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.fp.EntityId
import com.ubertob.fotf.zettai.fp.EventPersister
import com.ubertob.fotf.zettai.fp.ToDoListId




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
    override fun  invoke(events: Iterable<ToDoListEvent>): List<ToDoListEvent> =
        eventStreamer.store(events)
}