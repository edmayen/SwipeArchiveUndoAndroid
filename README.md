# ReversibleList 📱

Una aplicación ejemplo en **Android** desarrollada con **Jetpack Compose** y **Material 3** que demuestra cómo implementar una lista con elementos archivables mediante gestos de deslizamiento (*Swipe-to-Dismiss*) y botones de acción, incluyendo la funcionalidad de deshacer (*Undo*) mediante `Snackbar`.

---

## ✨ Características

- 📩 **Archivado por Deslizamiento (Swipe-to-Dismiss):** Permite archivar elementos deslizando la tarjeta hacia la izquierda utilizando `SwipeToDismissBox`.
- 🔘 **Botonera Directa:** Opción para archivar notificaciones directamente mediante un `TextButton`.
- ↩️ **Acción Deshacer (Undo):** Integración con `SnackbarHost` para ofrecer una acción inmediata de des-archivado que restaura el elemento en su posición original.
- 🎨 **Material 3 & Jetpack Compose:** Diseño adaptado con soporte para tema Claro y Oscuro (*Light/Dark Theme*).
- 🔄 **Manejo de Estado Reactivo:** Gestión de estado de lista reactivo usando `mutableStateListOf` y preservación de estado de UI con `remember`.

---

## 🛠️ Tecnologías y Librerías

- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Componentes Compose Destacados:**
  - `SwipeToDismissBox` / `SwipeToDismissBoxState`
  - `Scaffold` & `TopAppBar`
  - `SnackbarHost` & `SnackbarHostState`
  - `LazyColumn`
- **Requisitos de Sistema:**
  - **Min SDK:** 24 (Android 7.0)
  - **Target SDK:** 37
  - **Java Version:** 11

---

## 📄 Licencia

Este proyecto está distribuido bajo la licencia MIT. Siéntete libre de utilizarlo y modificarlo según tus necesidades.
