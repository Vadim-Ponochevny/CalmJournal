# CalmJournal

CalmJournal — это Android-приложение для ведения дневника, использующее современные технологии для анализа состояния пользователя и обеспечения плавного пользовательского опыта.

## Окружение (Environment)

Для успешной сборки проекта вам потребуется следующее окружение:

*   JDK: Version 11
*   Android SDK: Compile SDK 36, Target SDK 36, Min SDK 24
*   Gradle: 8.11.1
*   Android Gradle Plugin (AGP): 8.10.1
*   Kotlin: 2.1.21
*   Compose: Использован Compose BOM 2025.12.01
*   Основные библиотеки:
    *   DI: Hilt 2.58
    *   Database: Room 2.7.2
    *   Network: Retrofit 2.9.0, OkHttp 4.12.0
    *   AI/ML: ONNX Runtime 1.17.1
    *   UI/Animation: Lottie 6.7.1, Coil 3.3.0
    *   Data Storage: DataStore Preferences 1.2.0

## Конфигурация

Приложение требует настройки API-ключей перед запуском.

1.  В корневом каталоге проекта найдите или создайте файл local.properties.
2.  Добавьте в него ваш ключ для интеграции с GigaChat:

    ```properties
    GIGACHAT_AUTH_KEY="ВАШ_КЛЮЧ_АВТОРИЗАЦИИ"
    
    *Примечание: Ключ используется в app/build.gradle.kts для генерации поля BuildConfig.GIGACHAT_AUTH_KEY.*

## Инструкция по сборке

Для компиляции и сборки приложения выполните следующие шаги:

1.  Клонирование репозитория:

    ```bash
    git clone https://github.com/Vadim-Ponochevny/CalmJournal.git
    cd CalmJournal

2.  Настройка окружения:
    Убедитесь, что у вас установлена Android Studio (рекомендуется Ladybug или новее) и настроен JDK 11.

3.  Конфигурация ключей:
    Выполните шаги, описанные в разделе Конфигурация выше.

4.  Синхронизация Gradle:
    Откройте проект в Android Studio. Дождитесь завершения процесса "Gradle Sync". Если вы используете терминал, выполните:

    ```bash
    ./gradlew tasks


5.  Сборка проекта:
    *   Через Android Studio: Выберите Build > Make Project.
    *   Через терминал (Debug APK):

    ```bash
    ./gradlew assembleDebug


6.  Запуск:
    *   Подключите физическое устройство или запустите эмулятор (API 24+).
    *   Нажмите Run в Android Studio или установите APK вручную из app/build/outputs/apk/debug/.