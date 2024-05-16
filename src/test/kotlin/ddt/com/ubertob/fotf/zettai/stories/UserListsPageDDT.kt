package ddt.com.ubertob.fotf.zettai.stories

import com.ubertob.pesticide.core.DDT
import com.ubertob.pesticide.core.DdtScenario
import ddt.com.ubertob.fotf.zettai.tooling.ToDoListOwner
import ddt.com.ubertob.fotf.zettai.tooling.ZettaiDDT
import ddt.com.ubertob.fotf.zettai.tooling.allActions
import java.time.LocalDate

class UserListsPageDDT: ZettaiDDT(allActions()) {
    val carol by NamedActor(::ToDoListOwner)
    val emma by NamedActor(::ToDoListOwner)

    @DDT
    fun `no users have no lists`() = ddtScenario {
        play(emma.`cannot see any list`()
        ).wip(LocalDate.of(2024,12,31))
    }
}