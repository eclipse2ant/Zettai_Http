package ddt.com.ubertob.fotf.zettai.tooling

import com.ubertob.pesticide.core.DdtActor
import com.ubertob.fotf.zettai.domain.ListName
import com.ubertob.fotf.zettai.domain.ToDoItem
import com.ubertob.fotf.zettai.domain.ToDoList
import com.ubertob.fotf.zettai.domain.User
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isNotNull
import strikt.assertions.isNull

data class ToDoListOwner(override val name: String) : DdtActor<ZettaiActions>() {

    val user = User(name)

    fun `can see #listname with #itemnames`(
                listName: String,
                expectedItems: List<String>) =

        step(listName, expectedItems) {
                val list = getToDoList(user, ListName(listName))
                expectThat(list)
                    .isNotNull()
                    .get { items.map { it.description } }
                    .containsExactlyInAnyOrder(expectedItems)
        }

    private val Assertion.Builder<ToDoList>.itemNames
        get() = get { items.map { it.description } }
}