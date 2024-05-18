package ddt.com.ubertob.fotf.zettai.stories

import com.ubertob.pesticide.core.DDT
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

    @DDT
    fun `only owners can see all their list`() = ddtScenario {
        val expectedLists = generateSomeToDoLists()
        setUp {
            carol.`starts with some lists`(expectedLists)
        }.thenPlay(
            carol.`can see the lists #listNames`(expectedLists.keys),
            emma.`cannot see any list`()
        ).wip(LocalDate.of(2024,12,31) )
    }

    private fun generateSomeToDoLists(): Map<String, List<String>> {
        return   mapOf(
            "work" to listOf("meeting", "spreadsheet"),
            "home" to listOf("but food"),
            "frends" to listOf("buy present", "book restaurant")
        )
    }

}