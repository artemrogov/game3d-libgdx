# TestLearnLibGdx

Проект направлен на изучение основных возможностей [libGDX](https://libgdx.com/): 
- моделирование игровых миров, 
- анимация персонажей, 
- физический движок, 
- обработка вводимых команд и сетевые взаимодействия.

## Механика игры (основная идея)

### Анимация и программирование модели:
- ходьба
- бег
- прыжок через препятствие

### Управление игроком:
- Клавиатура
- Мышь

### Карта игры
- Закрытое помещение с выходом на открытое пространство
- Небо + освещение, смена дня и ночи + погодные условия.

### Объекты карты
- Игрок - здоровье, урон 
- Примитивный враг, что может нанести урон игроку.
- Простое оружие

### Игра
- Базовые настройки игры
- Мультиплеер
- Чат

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Import 3d model in Windows 10 OS 

```shell
fbx-conv.exe -f C:\Users\user\game_model_expirement\swat-operator\source\test3.fbx

fbx-conv.exe -f C:\Users\user\game_model_expirement\swat-operator\source\test3.fbx -o G3DJ

```
