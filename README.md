# Simplified prolog parser
## Сборка:
```
kotlinc Test.kt Parser.kt -include-runtime -d parser.jar
kotlinc Test.kt Parser.kt -include-runtime -d test.jar
```
Если нужно, как поставить kotlin можно найти тут:
https://kotlinlang.org/docs/tutorials/command-line.html
## Запуск:
### Сам парсер:
```
java -jar parser.jar
```
### Тесты:
```
java -jar test.jar
```
