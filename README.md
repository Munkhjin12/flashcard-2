# Flashcard System – CSA311 Homework 1


## Хэрэглэгчийн гарын авлага

### Суулгах

```bash
mvn package
```

### Ажиллуулах

```bash
java -jar target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar <cards-file> [options]
```

Эсвэл Maven-ээр:

```bash
mvn exec:java -Dexec.args="sample_cards.txt --order random --repetitions 2"
```

alias flashcard="java -jar $(pwd)/target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar"

### Тохируулгууд

| Тохируулга | Тайлбар | Default |
|---|---|---|
| `--help` | Тусламжийн мэдээлэл харуулах | - |
| `--order <order>` | Картын дараалал | `random` |
| `--repetitions <n>` | Зөв хариулт шаардах тоо | `1` |
| `--invertCards` | Асуулт, хариулт солих | `false` |

**Дараалалын сонголтууд:**
- `random` – Санамсаргүй
- `worst-first` – Хамгийн их алдаатай карт эхэнд
- `` – Өмнөх тойрогт буруу хариулсан карт эхэнд

### Cards файлын формат

```
# Тайлбар мөр
Q: Асуулт
A: Хариулт

Q: Дараагийн асуулт
A: Дараагийн хариулт
```

### Жишээ

```bash
# Энгийн горим
java -jar target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar sample_cards.txt

# 3 удаа давтах, хамгийн муу карт эхэнд
java -jar target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar sample_cards.txt --order worst-first --repetitions 3

# Урвуу горим
java -jar target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar sample_cards.txt --invertCards

# Хамгийн сүүлд алдсан картыг эхэнд гаргах горим
java -jar target/flashcard-1.0-SNAPSHOT-jar-with-dependencies.jar sample_cards.txt --order recent-mistakes-first
```

## Амжилтууд

| Амжилт | Нөхцөл |
|---|---|
| `SPEEDY` | Дундаж 5 секундээс доош хугацаанд хариулсан |
| `CORRECT` | Сүүлийн тойрогт бүх карт зөв |
| `REPEAT` | Нэг картад 5+ удаа хариулсан |
| `CONFIDENT` | Нэг картад 3+ удаа зөв хариулсан |

## Архитектур

```
flashcard/
├── CardOrganizer.java          # Interface
├── RandomSorter.java           # CardOrganizer хэрэгжүүлэлт
├── WorstFirstSorter.java       # CardOrganizer хэрэгжүүлэлт
├── RecentMistakesFirstSorter.java  # CardOrganizer хэрэгжүүлэлт
├── FlashCard.java              # Картын модель
├── SessionConfig.java          # Тохиргооны модель
├── CardFileParser.java         # Файл унших
├── AchievementManager.java     # Амжилт удирдах
├── FlashCardSession.java       # Сессийн логик
└── Main.java                   # Цонх (CLI)
```

## Тест ажиллуулах

```bash
mvn test
```

## Site үүсгэх

```bash
mvn site
```
