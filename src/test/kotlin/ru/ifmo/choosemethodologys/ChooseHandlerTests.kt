package ru.ifmo.choosemethodologys

import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.exception.ValidationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

enum class Steps {
    SIZE_PROJECT, EXT_PROJECT, COUNT_DEV, USE_PREPROCESSING, USE_PREPROCESSING_BIG
}

class ChooseHandlerTests : HandlerTests("/choose") {

    @Test
    fun stepsTest() {
        assertThat(handler.getStepByKey(Steps.SIZE_PROJECT.toString())).isNotNull
        assertThat(handler.getStepByKey(Steps.EXT_PROJECT.toString())).isNotNull
        assertThat(handler.getStepByKey(Steps.COUNT_DEV.toString())).isNotNull
        assertThat(handler.getStepByKey(Steps.USE_PREPROCESSING.toString())).isNotNull
        assertThat(handler.getStepByKey(Steps.USE_PREPROCESSING_BIG.toString())).isNotNull
        assertThat(handler.getStepByKey("123")).isNull()
    }

    @Test
    fun sizeProjectQuestionTest() {
        val step = getStep<String>(Steps.SIZE_PROJECT.toString())

        val question = step.question(state)

        assertThat(question).isEqualTo(MarkdownMessage("Давай определимся с масштабом проекта?", "Маленький", "Средний", "Крупный"))
    }

    @Test(expected = ValidationException::class)
    fun extProjectValidationWithExceptionTest() {
        val step = getStep<Any>(Steps.EXT_PROJECT.toString())

        step.validation("Exception")
    }

    @Test
    fun useProcessingValidationTest() {
        val step = getStep<Any>(Steps.USE_PREPROCESSING.toString())

        val answer = step.validation("нет")
        assertThat(answer).isNotNull
        assertThat(answer).isInstanceOf(Enum::class.java)
    }

    @Test
    fun processTest() {
        val sizeProject = getStep<Any>(Steps.SIZE_PROJECT.toString()).validation("маленький")
        val countDev = getStep<Any>(Steps.COUNT_DEV.toString()).validation("один")
        val extProject = getStep<Any>(Steps.EXT_PROJECT.toString()).validation("нет")
        val usePreprocessing = getStep<Any>(Steps.USE_PREPROCESSING.toString()).validation("да")
        val usePreprocessingBig = getStep<Any>(Steps.USE_PREPROCESSING_BIG.toString()).validation("нет")

        val choosePreprocessing = "*MCSS, SMACSS и OOCSS*\n" +
                "Наилучшим образом подходит *MCSS*, " +
                "так как данный подход имеет наименьшее количество кода, " +
                "что имеет большое значение для разработки мобильной версии сайта или адаптивного веб-сайта, " +
                "однако по данному подходу меньше всего документации, что усложнит изучение данного метода.\n" +
                "*SMACSS и OOCSS* имеют одинаковое количество кода, но подход *SMACSS* имеет самую полную документацию и " +
                "множество статей и примеров использования, что способствует более быстрому обучению.\n" +
                "Все перечисленные методы не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
                "в один и компиляции с языков препроцессоров может использоваться любая система сборки."

        val response = handler.process(state, mapOf(
                Steps.SIZE_PROJECT.toString() to sizeProject,
                Steps.COUNT_DEV.toString() to countDev,
                Steps.EXT_PROJECT.toString() to extProject,
                Steps.USE_PREPROCESSING.toString() to usePreprocessing,
                Steps.USE_PREPROCESSING_BIG.toString() to usePreprocessingBig
        ))

        assertThat(response).isEqualTo(MarkdownMessage("Рекомендация:\n${choosePreprocessing}"))
    }

}