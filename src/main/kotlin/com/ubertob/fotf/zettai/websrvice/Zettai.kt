 package com.ubertob.fotf.zettai.websrvice

import com.ubertob.fotf.zettai.domain.*
import com.ubertob.fotf.zettai.ui.HtmlPage
import com.ubertob.fotf.zettai.ui.renderHtml
import org.http4k.core.*
import org.http4k.core.body.form
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes


data class Zettai(val hub : ZettaiHub): HttpHandler {
    val routes = routes(
        "/todo/{user}/{list}" bind Method.GET to ::getToDoList,
        "/todo/{user}/{listname}" bind Method.POST to ::addNewItem
    )

    override fun invoke(request: Request): Response =
        routes(request)

    private fun getToDoList(request: Request): Response =
        request.let(::extractListData)
            .let(::fetchListContent)
            ?.let(::renderHtml)
            ?.let(::createResponse)
            ?: Response(Status.NOT_FOUND)

    private fun addNewItem(request: Request): Response {
        val user = request.path("user")
            ?.let(::User)
            ?: return Response(Status.BAD_REQUEST)
        val listName = request.path("listname")
            ?.let(::ListName)
            ?: return Response(Status.BAD_REQUEST)
        val item = request.form("itemname")
            ?.let(::ToDoItem)
            ?: return Response(Status.BAD_REQUEST)
        return hub.addItemToList(user, listName, item)
            ?.let { Response(Status.SEE_OTHER)
                .header("Location", "/todo/${user.name}/${listName.name}") }
            ?: Response(Status.NOT_FOUND)
    }
    fun extractListData(request: Request): Pair<User, ListName> {
        val user = request.path("user") ?: error("User missing")
        val list = request.path("list") ?: error("List missing")
        return User(user) to ListName(list)
    }
    fun fetchListContent(listId: Pair<User, ListName>): ToDoList? {
        var tmp =   hub.getList(listId.first, listId.second)
        return hub.getList(listId.first, listId.second)
//            ?: error("List unknown")
    }
    fun createResponse(html: HtmlPage): Response = Response(Status.OK).body(html.raw)

}



