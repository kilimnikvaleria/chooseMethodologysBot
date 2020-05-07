import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.exception.ValidationException

enum class SizeProject {
    SMALL, MEDIUM, BIG
}
enum class ExtProject {
    NO, BIG, NONE
}
enum class CountDev {
    ONE, MORE_ONE, NONE
}
enum class UsePreprocessingBig {
    SASS, EXCEPT_SASS, NO, NONE
}
enum class UsePreprocessing {
    YES, NO, NONE
}
enum class Steps {
    SIZE_PROJECT, EXT_PROJECT, COUNT_DEV, USE_PREPROCESSING, USE_PREPROCESSING_BIG
}

enum class Preprossessing(val value: String) {
    S0("Ничего не выбрано"),
    S1("*MCSS, SMACSS и OOCSS*\n" +
            "Наилучшим образом подходит *MCSS*, " +
            "так как данный подход имеет наименьшее количество кода, " +
            "что имеет большое значение для разработки мобильной версии сайта или адаптивного веб-сайта, " +
            "однако по данному подходу меньше всего документации, что усложнит изучение данного метода.\n" +
            "*SMACSS и OOCSS* имеют одинаковое количество кода, но подход *SMACSS* имеет самую полную документацию и " +
            "множество статей и примеров использования, что способствует более быстрому обучению.\n" +
            "Все перечисленные методы не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки."),
    S2("*MCSS, SMACSS, OOCSS и Atomic CSS*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, " +
            "что имеет большое значение для разработки мобильной версии сайта или адаптивного веб-сайта, " +
            "однако по данному подходу меньше документации, чем по *SMACSS и OOCSS*, что усложнит изучение данного метода.\n" +
            "*SMACSS и OOCSS* имеют одинаковое количество кода, но подход *SMACSS* имеет самую полную документацию и " +
            "множество статей и примеров использования, что способствует более быстрому обучению.\n" +
            "*MCSS, SMACSS, OOCSS* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*Atomic CSS* имеет наибольшее количество кода и самую неполную документацию, также необходимо учесть, " +
            "что используя *Atomic CSS* необходимо будет делать сборку проекта и подключать специальный модуль для генерации кода CSS."),
    S3("*MCSS, SMACSS*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше документации, что " +
            "усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, " +
            "что способствует более быстрому обучению.\n" +
            "*MCSS и SMACSS* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки."),
    S4("*MCSS, SMACSS и Atomic CSS*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, что способствует " +
            "более быстрому обучению.\n" +
            "*MCSS и SMACSS* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*Atomic CSS* имеет наибольшее количество кода и самую неполную документацию, также необходимо учесть, " +
            "что используя *Atomic CSS* необходимо будет делать сборку проекта и подключать специальный модуль для генерации кода CSS."),
    S5("*MCSS, SMACSS, OOCSS и БЭМ*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше всего документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS и OOCSS* имеют одинаковое количество кода, но подход *SMACSS* имеет более полную документацию и множество " +
            "статей и примеров использования, что способствует более быстрому обучению.\n" +
            "Все перечисленные методы не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов " +
            "стилей в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*БЭМ* имеет наибольшее количество кода, однако следует учесть, что данных подход проще всего в изучении так как имеет " +
            "больше всего документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, " +
            "так как код будет всегда понятным. "),
    S6("*MCSS, SMACSS, OOCSS, Atomic CSS и БЭМ*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше всего документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS и OOCSS* имеют одинаковое количество кода, но подход *SMACSS* имеет более полную документацию и множество статей " +
            "и примеров использования, что способствует более быстрому обучению.\n" +
            "*MCSS, SMACSS, OOCSS и БЭМ* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов " +
            "стилей в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*Atomic CSS* имеет самую неполную документацию, также необходимо учесть, что используя *Atomic CSS* необходимо будет " +
            "делать сборку проекта и подключать специальный модуль для генерации кода CSS. " +
            "*БЭМ* имеет наибольшее количество кода, однако следует учесть, что данных подход проще всего в изучении так как имеет " +
            "больше всего документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, " +
            "так как код будет всегда понятным."),
    S7("*MCSS, SMACSS и БЭМ*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, " +
            "что способствует более быстрому обучению.\n" +
            "*БЭМ* имеет наибольшее количество кода, однако данных подход проще всего в изучении так как имеет больше всего " +
            "документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, так как код будет " +
            "всегда понятным.\n" +
            "Все перечисленные методы не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов " +
            "стилей в один и компиляции с языков препроцессоров может использоваться любая система сборки."),
    S8("*MCSS, SMACSS, Atomic CSS и БЭМ*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше всего документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, " +
            "что способствует более быстрому обучению.\n" +
            "*MCSS, SMACSS и БЭМ* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*Atomic CSS* имеет самую неполную документацию, также необходимо учесть, что используя *Atomic CSS* необходимо " +
            "будет делать сборку проекта и подключать специальный модуль для генерации кода CSS.\n" +
            "*БЭМ* имеет наибольшее количество кода, однако данных подход проще всего в изучении так как имеет больше всего " +
            "документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, " +
            "так как код будет всегда понятным."),
    S9("*MCSS, SMACSS и БЭМ*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше документации, " +
            "что усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, " +
            "что способствует более быстрому обучению.\n" +
            "*БЭМ* имеет наибольшее количество кода, однако данных подход проще всего в изучении так как имеет больше всего " +
            "документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, так как код будет " +
            "всегда понятным.\n" +
            "Все перечисленные методы не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов " +
            "стилей в один и компиляции с языков препроцессоров может использоваться любая система сборки."),
    S10("*MCSS, SMACSS, БЭМ и DoCSSa*\n" +
            "Наилучшим образом подходит *MCSS*, так как данный подход имеет наименьшее количество кода, что имеет большое значение " +
            "для разработки мобильной версии сайта или адаптивного веб-сайта, однако по данному подходу меньше документации, что " +
            "усложнит изучение данного метода.\n" +
            "*SMACSS* имеет более полную документацию и множество статей и примеров использования, " +
            "что способствует более быстрому обучению.\n" +
            "*MCSS и SMACSS и БЭМ* не требуют сборки проекта, но при использовании препроцессоров для объединения всех файлов стилей " +
            "в один и компиляции с языков препроцессоров может использоваться любая система сборки.\n" +
            "*БЭМ* имеет еще большее количество кода, однако следует учесть, что данных подход проще всего в изучении так как " +
            "имеет больше всего документации на разных языках.\n" +
            "Так же при использовании *БЭМ* не нужно будет создавать сопроводительную документации к проекту, " +
            "так как код будет всегда понятным.\n" +
            "*DoCSSa* имеет наибольшее количество кода, и у данного подхода не очень большая документация и только на английском языке.");
}

handler("/choose", "Определить методологию") {

    step<SizeProject>(Steps.SIZE_PROJECT.toString()) {
        question {
            MarkdownMessage("Давай определимся с масштабом проекта?", "Маленький", "Средний", "Крупный")
        }

        validation {
            when (it.toLowerCase()) {
                "маленький" -> SizeProject.SMALL
                "средний" -> SizeProject.MEDIUM
                "крупный" -> SizeProject.BIG
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }

        next {
            if(it.answers[Steps.SIZE_PROJECT.toString()] as SizeProject == SizeProject.BIG) {
                it.answers[Steps.EXT_PROJECT.toString()] = ExtProject.NONE
                it.answers[Steps.COUNT_DEV.toString()] = CountDev.NONE
                it.answers[Steps.USE_PREPROCESSING.toString()] = UsePreprocessing.NONE
                Steps.USE_PREPROCESSING_BIG.toString()
            } else {
                it.answers[Steps.USE_PREPROCESSING_BIG.toString()] = UsePreprocessingBig.NONE
                Steps.EXT_PROJECT.toString()
            }
        }
    }

    step<ExtProject>(Steps.EXT_PROJECT.toString()) {

        question {
            MarkdownMessage("Предполагается ли расширение проекта?", "Нет", "До крупного")
        }

        validation {
            when (it.toLowerCase()) {
                "нет" -> ExtProject.NO
                "до крупного" -> ExtProject.BIG
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }

        next {
            if(it.answers[Steps.EXT_PROJECT.toString()] as ExtProject == ExtProject.NO) {
                Steps.COUNT_DEV.toString()
            } else {
                it.answers[Steps.COUNT_DEV.toString()] = CountDev.NONE
                Steps.USE_PREPROCESSING.toString()
            }
        }
    }

    step<CountDev>(Steps.COUNT_DEV.toString()) {

        question {
            MarkdownMessage("Какое количество разработчиков планируется на проекте?", "Один", "Больше одного")
        }

        validation {
            when (it.toLowerCase()) {
                "один" -> CountDev.ONE
                "больше одного" -> CountDev.MORE_ONE
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }
    }

    step<UsePreprocessing>(Steps.USE_PREPROCESSING.toString()) {

        question {
            MarkdownMessage("Будут ли использоваться препроцессоры на проекте?", "Да", "Нет")
        }

        validation {
            when (it.toLowerCase()) {
                "да" -> UsePreprocessing.YES
                "нет" -> UsePreprocessing.NO
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }
        next {
            null
        }
    }

    step<UsePreprocessingBig>(Steps.USE_PREPROCESSING_BIG.toString()) {

        question {
            MarkdownMessage("Будут ли использоваться препроцессоры на проекте?", "Да, кроме SASS", "SASS", "Нет")
        }

        validation {
            when (it.toLowerCase()) {
                "sass" -> UsePreprocessingBig.SASS
                "да, кроме sass" -> UsePreprocessingBig.EXCEPT_SASS
                "нет" -> UsePreprocessingBig.NO
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }
    }

    process { state, answers ->
        val sizeProject = answers[Steps.SIZE_PROJECT.toString()] as SizeProject
        val countDev = answers[Steps.COUNT_DEV.toString()] as CountDev
        val extProject = answers[Steps.EXT_PROJECT.toString()] as ExtProject
        val usePreprocessing = answers[Steps.USE_PREPROCESSING.toString()] as UsePreprocessing
        val usePreprocessingBig = answers[Steps.USE_PREPROCESSING_BIG.toString()] as UsePreprocessingBig

        var choosePreprocessing = Preprossessing.S0

        when (sizeProject) {
            SizeProject.SMALL -> {
                when (extProject) {
                    ExtProject.NO -> {
                        when (countDev) {
                            CountDev.ONE -> {
                                when (usePreprocessing) {
                                    UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S1 }
                                    UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S2 }
                                }
                            }
                            CountDev.MORE_ONE -> {
                                when (usePreprocessing) {
                                    UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S3 }
                                    UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S4 }
                                }
                            }
                        }
                    }
                    ExtProject.BIG -> {
                        when (usePreprocessing) {
                            UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S3 }
                            UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S4 }
                        }
                    }
                }
            }
            SizeProject.MEDIUM -> {
                when (extProject) {
                    ExtProject.NO -> {
                        when (countDev) {
                            CountDev.ONE -> {
                                when (usePreprocessing) {
                                    UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S5 }
                                    UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S6 }
                                }
                            }
                            CountDev.MORE_ONE -> {
                                when (usePreprocessing) {
                                    UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S7 }
                                    UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S8 }
                                }
                            }
                        }
                    }
                    ExtProject.BIG -> {
                        when (usePreprocessing) {
                            UsePreprocessing.YES -> { choosePreprocessing = Preprossessing.S7 }
                            UsePreprocessing.NO -> { choosePreprocessing = Preprossessing.S8 }
                        }
                    }
                }
            }
            SizeProject.BIG -> {
                when (usePreprocessingBig) {
                    UsePreprocessingBig.NO -> { choosePreprocessing = Preprossessing.S8 }
                    UsePreprocessingBig.EXCEPT_SASS -> { choosePreprocessing = Preprossessing.S9 }
                    UsePreprocessingBig.SASS -> { choosePreprocessing = Preprossessing.S10 }
                }
            }
        }

        MarkdownMessage("Рекомендация:\n${choosePreprocessing.value}")
    }
}