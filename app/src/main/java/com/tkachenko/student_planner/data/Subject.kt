package com.tkachenko.student_planner.data

data class Subject(
    val id: String,
    val name: String,
    val professor: String,
    val credits: Int,
    val currentGrade: String,
    val description: String,

)
val samlpeSubjects = listOf(
    Subject(
        id = "1",
        name = "Разработка мобильных приложений",
        professor = "Леонтьев Д.А.",
        credits = 91,
        currentGrade = "Отлично",
        description = "Разработка нативных Android-приложений с использованием Kotlin и современных компонентов Jetpack Compose. Изучение архитектурных паттернов MVVM, работы с локальными базами данных и сетевыми API."
    ),
    Subject(
        id = "3",
        name = "Системное программирование",
        professor = "Токаев Т.И.",
        credits = 75,
        currentGrade = "Отлично",
        description = "Низкоуровневая разработка на языке Rust: управление памятью без сборщика мусора, многопоточность, работа с операционной системой и создание высокопроизводительных системных компонентов"
    ),Subject(
        id = "4",
        name = "Математическое моделирование",
        professor = "Трошина О.В.",
        credits = 75,
        currentGrade = "Отлично",
        description = "математика это круто"
    ),Subject(
        id = "5",
        name = "Управление проектами",
        professor = "Трошина О.В.",
        credits = 75,
        currentGrade = "Отлично",
        description = "разработка своих собственных проектынх идей и их реализация"
    ),Subject(
        id = "6",
        name = "ИСРСПО",
        professor = "Леонтьев Д.А.",
        credits = 75,
        currentGrade = "Отлично",
        description = "Низкоуровневая разработка на языке Rust: управление памятью без сборщика мусора, многопоточность, работа с операционной системой и создание высокопроизводительных системных компонентов"
    ),
)
