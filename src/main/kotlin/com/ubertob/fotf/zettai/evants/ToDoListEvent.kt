package com.ubertob.fotf.zettai.evants

import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.ToDoItem
import com.ubertob.fotf.zettai.domain.User
import com.ubertob.fotf.zettai.fp.EntityEvent
import com.ubertob.fotf.zettai.fp.EntityState
import com.ubertob.fotf.zettai.fp.ToDoListId
import java.time.Instant

sealed class ToDoListEvent: EntityEvent
data class ListCreated(
    override val id: ToDoListId, val owner: User,
    val name: ListName
): ToDoListEvent()
data class ItemAdded(
    override val id: ToDoListId,
    val item: ToDoItem
): ToDoListEvent()
data class ItemRemoved(
    override val id: ToDoListId,
    val item: ToDoItem): ToDoListEvent()
data class ItemModified(
    override val id: ToDoListId,
    val prevItem: ToDoItem, val item: ToDoItem): ToDoListEvent()
data class ListPutOnHold(
    override val id: ToDoListId,
    val reason: String): ToDoListEvent()
data class ListReleased(override val id: ToDoListId): ToDoListEvent()
data class ListClosed(
    override val id: ToDoListId,
    val closedOn: Instant
): ToDoListEvent()

sealed class ToDoListState : EntityState<ToDoListEvent> {
    abstract override fun combine(event: ToDoListEvent): ToDoListState
}
object InitialState: ToDoListState() {
    override fun combine(event: ToDoListEvent) = this // for the moment
}
fun Iterable<ToDoListEvent>.fold(): ToDoListState =
    fold(InitialState as ToDoListState) { acc, e -> acc.combine(e) }