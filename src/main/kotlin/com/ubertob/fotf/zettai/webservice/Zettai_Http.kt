package com.ubertob.fotf.zettai.webservice

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

const val htmlPage = """
<html>
<body>
<h1 style="text-align:center; font-size:3em;" >
Hello Functional World!
</h1>
</body>
</html>"""
fun main() {
    val app: HttpHandler = routes(
        "/todo/{user}/{list}" bind Method.GET to ::showList
    )
    app.asServer(Jetty(8080)).start()
}

fun showList(req: Request): Response {
    val user: String? = req.path("user")
    val list: String? = req.path("list")
    val htmlPage = """
<html>
    <body>
        <h1>Zettai</h1>
            <p>Here is the list <b>$list</b> of user <b>$user</b></p>
    </body>
</html>"""
    return Response(OK).body(htmlPage)
}