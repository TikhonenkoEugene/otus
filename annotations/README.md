## Тестовый фреймворк
### Задание
* Написать свой тестовый фреймворк;
* Поддержать свои аннотации @Test, @Before, @After;
* Запускать вызовом статического метода с именем класса с тестами.
#### Описание что нужно сделать:
* Создать три аннотации - @Test, @Before, @After
* Создать класс-тест, в котором будут методы, отмеченные аннотациями
* Создать "запускалку теста". На вход она должна получать имя класса с тестами, в котором следует найти и запустить методы отмеченные аннотациями и пункта 1
* Алгоритм запуска должен быть следующий:
  * метод(ы) Before
  * текущий метод Test
  * метод(ы) After
    - для каждой такой "тройки" надо создать СВОЙ экземпляр класса-теста
* Исключение в одном тесте не должно прерывать весь процесс тестирования
* На основании возникших во время тестирования исключений вывести статистику выполнения тестов (сколько прошло успешно, сколько упало, сколько было всего)
* "Запускалка теста" не должна иметь состояние, но при этом весь функционал должен быть разбит на приватные методы. Надо придумать, как передавать информацию между методами

### Результат вывода:
#### --------------------------------------
#### Suit #1: Test products cart
#### Before test: Open browser and create a cart
#### Start: Open browser
#### Test #1: Check user can add one item into the cart
#### Result: Success
#### Test #2: Check user can remove an item from the cart
#### Result: Filed
#### Test #3: Check user can make an order
#### Result: Success
#### After test: Close browser and delete a cart
#### Finish: Close browser
#### --------------------------------------
#### Suit #2: Select products from the catalog
#### Before test: Open browser and find catalog
#### Start: Open browser
#### Test #4: Find and open a product of potato
#### Result: Success
#### Test #5: Find and open a product of carrot
#### Result: Filed
#### Test #6: Find and open a product of apple
#### Result: Success
#### Test #7: Find and open a product of orange
#### Result: Success
#### Test #8: Find and open a product of strawberry
#### Result: Filed
#### After test: Close browser
#### Finish: Close browser
#### --------------------------------------
#### Suit #3: Pay items from the cart
#### Before test: Open browser and setup payment cart
#### Start: Open browser
#### Test #9: Pay when the balance less than sum
#### Result: Success
#### Test #10: Pay when the balance equals than sum
#### Result: Filed
#### Test #11: Pay when the balance more than sum
#### Result: Success
#### Test #12: Pay if card is blocked
#### Result: Success
#### After test: Close browser
#### Finish: Close browser
#### --------------------------------------
#### Total test:   12
#### Passed test:  8
#### Failed tests: 4
#### --------------------------------------