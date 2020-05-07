import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage

handler("/start") {
    process { state, _ ->
        MarkdownMessage("""
            Привет, ${state.chat.firstName}! 
            Я помогу тебе определится с методологией CSS для твоего проекта.
            Начнем?
        """.trimIndent(), "Определить методологию")
    }
}