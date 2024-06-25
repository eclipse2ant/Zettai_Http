package com.ubertob.fotf.zettai.evants

typealias ToDoListId = EntityId

data class EntityId(val raw: UUID) {
    companion object {
        fun mint() = EntityId(UUID.randomUUID())
    }
}
interface EntityEvent {
    val id: EntityId
}
