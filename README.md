# Student Planner - Студенческий планер

## Описание приложения

**Student Planner** — это многоэкранное мобильное приложение для студентов, разработанное на Jetpack Compose. Приложение позволяет просматривать список изучаемых дисциплин, подробную информацию о каждой дисциплине, а также предоставляет доступ к профилю студента и настройкам приложения. Проект демонстрирует современные подходы к навигации в Android-приложениях с использованием библиотеки Navigation Compose.

## Реализованные экраны

| Экран | Описание |
|-------|----------|
| **HomeScreen** | Главный экран со списком всех дисциплин, изучаемых в текущем семестре. Каждая дисциплина представлена в виде карточки с названием, преподавателем, количеством баллов и текущей оценкой. |
| **DetailsScreen** | Экран с подробной информацией о выбранной дисциплине: полное описание, преподаватель, баллы, оценка и другая информация. |
| **ProfileScreen** | Экран профиля студента с персональной информацией: ФИО, группа, факультет, email, а также статистика (средний балл, количество набранных кредитов). |
| **SettingsScreen** | Экран настроек приложения с возможностью включения/выключения push-уведомлений и темной темы. |

## Используемые технологии

- **Kotlin** — основной язык разработки
- **Jetpack Compose** — современный UI-фреймворк для построения интерфейсов
- **Navigation Compose** — библиотека для навигации между экранами
- **Material 3** — дизайн-система для визуального оформления
- **Git** — система контроля версий

## Схема навигации
┌─────────────────┐
│ │
▼ │
┌─────────────────────────────┐ │
│ HomeScreen │ │
│ (список дисциплин) │ │
└──────────────┬──────────────┘ │
│ │
┌──────────────┼──────────────┬────┴──────┐
│ │ │ │
▼ ▼ ▼ ▼
┌────────┐ ┌────────────┐ ┌─────────┐ ┌──────────┐
│Details │ │ProfileScreen│ │Settings │ │Schedule │
│Screen │ │ │ │Screen │ │Screen │
└────────┘ └────────────┘ └─────────┘ └──────────┘
(будущее расширение)


**Маршруты навигации:**
- `home` → Главный экран (startDestination)
- `details/{subjectId}` → Детали дисциплины (с параметром)
- `profile` → Профиль студента
- `settings` → Настройки приложения

## Скриншоты экранов

> *Скриншоты будут добавлены после выполнения лабораторной работы*

1. **Главный экран** — `step7_navigation_working_FIO_1.png`
2. **Экран деталей** — `step7_navigation_working_FIO_2.png`
3. **Экран профиля** — `step7_navigation_working_FIO_3.png`
4. **Экран настроек** — `step7_navigation_working_FIO_4.png`

---

## Контрольные вопросы

### 1. Что такое NavController и для чего он используется?

**Роль NavController в навигации:**

`NavController` — это центральный компонент библиотеки Navigation Compose, который управляет перемещением между экранами (destinations) приложения. Он выполняет следующие функции:
- Управляет back stack'ом (стеком посещенных экранов)
- Обеспечивает переходы между экранами через метод `navigate()`
- Позволяет возвращаться на предыдущие экраны через `popBackStack()`
- Хранит текущее состояние навигации

**Почему важно создавать его через `rememberNavController()`?**

`rememberNavController()` используется для сохранения экземпляра `NavController` при рекомпозициях Compose. Без этого при каждом перерисовке UI создавался бы новый контроллер, что приводило бы к:
- Потере истории навигации (back stack)
- Невозможности корректно вернуться на предыдущий экран
- Сбросу текущего состояния навигации

Пример правильного использования:
```kotlin
@Composable
fun MyApp() {
    val navController = rememberNavController() // сохраняется между рекомпозициями
    NavHost(navController = navController, startDestination = "home") {
        // ...
    }
}
```
### 2. Как передать параметр в маршрут навигации?
Процесс передачи параметра (от определения до извлечения):

Определение маршрута с параметром:

```kotlin
sealed class Screen(val route: String) {
    object Details : Screen("details/{subjectId}") {
        fun createRoute(subjectId: String) = "details/$subjectId"
    }
}
```
Настройка composable с извлечением параметра:

```kotlin
composable(
    route = Screen.Details.route,
    arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
) { backStackEntry ->
    val subjectId = backStackEntry.arguments?.getString("subjectId")
    DetailsScreen(subjectId = subjectId ?: "", onNavigateBack = { /* ... */ })
}
```
Переход с передачей параметра:

```kotlin
navController.navigate(Screen.Details.createRoute(subject.id))
```
**Разница между обязательными и опциональными параметрами:**

| Характеристика | Обязательные параметры | Опциональные параметры |
|----------------|------------------------|------------------------|
| **Синтаксис** | `"details/{subjectId}"` | `"search?query={query}"` |
| **Наличие в маршруте** | Всегда присутствуют | Могут отсутствовать |
| **Значение по умолчанию** | Не имеют | Можно задать через `defaultValue` |
| **Объявление** | Только имя параметра | Требуется `navArgument` с конфигурацией |
| **Пример** | `navigate("details/123")` | `navigate("search?query=kotlin")` |

### 3. Зачем использовать sealed class для маршрутов?

**Преимущества sealed class перед обычными строками:**

| Преимущество | Объяснение | Пример |
|--------------|------------|--------|
| **Type-Safety** | Компилятор проверяет существование маршрута | Ошибка при `navigate("detals")` вместо `navigate("details")` |
| **Единое место изменений** | Все маршруты определены в одном месте | Легко найти и изменить все использования |
| **Автодополнение (IDE)** | IDE подсказывает доступные маршруты | При вводе `Screen.` появляются все варианты |
| **Предотвращение дублирования** | Исключает опечатки и несоответствия | Нельзя случайно написать "hom" вместо "home" |
| **Поддержка рефакторинга** | При переименовании все использования обновляются автоматически | Rename в IDE меняет везде |

**Пример ошибки, которую sealed class помогает избежать:**

```
//  Без sealed class (строки) — ошибка обнаружится только во время выполнения
navController.navigate("detals/123")  // Опечатка! Экран не откроется

//  С sealed class — ошибка на этапе компиляции
navController.navigate(Screen.Detalis.createRoute("123"))  
// Error: Unresolved reference 'Detalis' (компилятор не даст собрать проект)
```
### 4. Что такое Back Stack и как им управлять?

**Схема back stack для последовательности Home → Profile → Settings:**
        Settings ← (текущий экран, вершина стека)
        Profile
        Home     ← (начальный экран, дно стека)


Back Stack работает по принципу LIFO (Last In — First Out): последний добавленный экран становится текущим, и при нажатии "Назад" он удаляется первым.

**Что произойдёт при вызове `popBackStack()` на экране Settings?**

При вызове `navController.popBackStack()` на экране Settings:

1. Экран Settings удаляется из стека
2. Пользователь возвращается на экран Profile
3. Back Stack становится: `Home → Profile`

```kotlin
// В SettingsScreen
IconButton(onClick = { 
    navController.popBackStack() // возврат на Profile
}) { ... }
```
### 5. Как работает startDestination в NavHost?

**Какой экран будет показан первым при запуске приложения?**

`startDestination` определяет маршрут (route), который будет отображен при первом запуске приложения. Это "входная точка" навигации:

```kotlin
NavHost(
    navController = navController,
    startDestination = Screen.Home.route  // "home" — первый экран
) {
    composable(Screen.Home.route) { HomeScreen(/* ... */) }
    composable(Screen.Profile.route) { ProfileScreen(/* ... */) }
}
```
**Можно ли изменить startDestination динамически?**

Да, но это требует дополнительной логики, так как NavHost не позволяет напрямую изменить startDestination после создания.

### 6. Что произойдёт, если навигировать на несуществующий маршрут?

**Как NavController обрабатывает неизвестные маршруты?**

При попытке перехода на маршрут, который не зарегистрирован в `NavHost`, происходит **ошибка времени выполнения (runtime crash)**:
java.lang.IllegalArgumentException: Navigation destination that matches request NavDeepLinkRequest
{ uri=android-app://.../unknown_route } cannot be found in the navigation graph

Приложение падает с `IllegalArgumentException`, потому что NavController не может найти соответствующий destination.

**Как можно обработать такую ситуацию?**

1. **Проверка перед навигацией (рекомендуемый способ):**
```kotlin
fun safeNavigate(navController: NavController, route: String) {
    try {
        navController.navigate(route)
    } catch (e: IllegalArgumentException) {
        Log.e("Navigation", "Route not found: $route", e)
        // Показать пользователю сообщение об ошибке
    }
}
```
2. **Использование sealed class для гарантии корректных маршрутов:**

```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{id}")
    // Все возможные маршруты определены здесь
}

// Компилятор не позволит передать несуществующий маршрут
navController.navigate(Screen.Home.route)  // ✅ безопасно
Global error handler для навигации:


class SafeNavigator {
    fun navigate(navController: NavController, route: String, onError: (String) -> Unit = {}) {
        try {
            navController.navigate(route)
        } catch (e: IllegalArgumentException) {
            onError("Destination not found: $route")
        }
    }
}
```
3. **Расширение для NavController:**

```kotlin
fun NavController.navigateSafe(route: String, onError: (String) -> Unit = {}) {
    try {
        navigate(route)
    } catch (e: IllegalArgumentException) {
        onError(route)
    }
}
```
### 7. Зачем нужен параметр launchSingleTop в навигации?

**Пример, когда без launchSingleTop может возникнуть проблема:**

Представьте экран профиля, на котором есть кнопка "Обновить профиль", которая вызывает `navController.navigate("profile")`:

```kotlin
// Без launchSingleTop — проблема!
Button(onClick = { 
    navController.navigate("profile")  // многократные нажатия создают дубликаты
}) {
    Text("Обновить профиль")
}
```
Если пользователь нажмет кнопку 3 раза, back stack будет выглядеть так:
Profile (3-й экземпляр) ← текущий
Profile (2-й экземпляр)
Profile (1-й экземпляр)
Home
При нажатии "Назад" пользователь будет "пролистывать" несколько одинаковых экранов Profile, что создает плохой UX.

С параметром launchSingleTop проблема решается:
```
Button(onClick = { 
    navController.navigate("profile") {
        launchSingleTop = true  // предотвращает создание дубликата
    }
}) {
    Text("Обновить профиль")
}
```

**Как launchSingleTop влияет на back stack:**

| Действие | Без launchSingleTop | С launchSingleTop |
|----------|---------------------|-------------------|
| 1-й переход на Profile | Profile добавляется | Profile добавляется |
| 2-й переход на Profile | **Добавляется новый** Profile | Profile **не добавляется** (используется существующий) |
| 3-й переход на Profile | **Добавляется еще один** Profile | Profile **не добавляется** |
| Размер back stack после 3 кликов | 4 элемента (Home + 3×Profile) | 2 элемента (Home + Profile) |
| Поведение кнопки "Назад" | Пользователь проходит через все Profile | Сразу возврат на Home |

**Другие полезные параметры навигации:**

```kotlin
navController.navigate("details/${subject.id}") {
    launchSingleTop = true              // предотвращает дублирование текущего экрана
    popUpTo("home") { inclusive = false } // очищает стек до home (не включая home)
    restoreState = true                 // восстанавливает состояние при возврате
}
```
