# Проблематика

# Варианты Решений

 ## Вариант 1
    Описание: Approach1.jpg. Магазины сами передают данные в нашу систему. Используем кластеризованный монолит по классической схеме с балансировщиком.
    
    Плюсы:
    - Вполне работоспособный вариант. По такой схеме у нас работала система, обслуживающая 800 поликлиник и стационаров Москвы.
    - Достаточно простая в реализации.
    - Не требует каких-либо особых компетенций в команде.
    
    Минусы:
    - Узлы нашего приложения следует обновлять по очереди, давая во время обновляния определённые инструкции балансировщику, чтобы не использовать отключаемые узлы во время обновления. Процесс обновления относительно "опасный" и может вызвать недоступность системы.
    - БД в такой схеме может оказаться "бутылочным горлышком".
    - Размер БД может быть огромным. Повышенные требования к оборудованию.
    - Сложно осуществлять горизонтальное масштабирование.
    
 ## Вариант 2
    Описание: Approach2.jpg. Здесь мы также используем схему, когда магазины пересылают нам свои данные, но используем маршрутизацию от источника по принципу некоего hash'а, который определяет к какому сервису будет обращаться тот или иной магазин-клиент. Используем шардирование. Можем динамически добавлять или удалять узлы. Запросы клиентов будут перераспределяться между сервисами на основе близости hash'а. Используем database-per-service подход. Сервисы должны хранить некоторое количество данных соседних узлов для обеспечения fail-tolerance, реплицируя их. Приведу пример. Есть узел 30, есть, например, 2 или более узла с наиболее близким hash'ом к данному узлу (соседние по hash'у), пусть это будут узлы 31 и 32. Эти узлы должны хранить некоторую часть данных узла 30 для обеспечения устойчивости к сбоям. Если узла 2, то каждый по половине, если больше, то соответственно меньшую часть.
    
    Плюсы:
    - Устойчивость к сбоям.
    - Хорошая горизонтальная масштабируемость системы.
    - Устойчивость к нагрузкам. Можно быстро и просто добавить узлов в систему.
    
    Минусы:
    - Такую систему сложнее обслуживать.
    - Дублирование данных.
    - Нужно использовать какие-то инструменты сбора распределённых log'ов, например, ELK.
    - Уровень требований к компетенциям команды выше, чем в предыдущем случае.
    
 ## Вариант 3
    Описание: Approach3.jpg. В данном случае может быть несколько сервисов, обслуживающих какую-то группу магазинов. Сервисы сами опрашивают с какой-то периодичностью магазины. Если магазин не доступен, то запрос повторяется.
    
    Плюсы:
    - Достаточно простая схема.
    - Можно горизонтально масштабировать за счёт добавления новых сервисов при появлении новых магазинов, либо увеличения сервисов и уменьшения количество магазинов, которые данный сервис обслуживает.
    
    Минусы:
    - Такую систему может быть не очень просто обновлять. Обновления ПО магазинов должны быть синхронны с обновлениями сервисов.
    - Такую систему трудно мониторить, либо нужны какие-то инструменты распределённого мониторинга.
    - Распределённое log'ирование.
    
 ## Вариант 4
    Описание: Approach4.jpg. Что-то подобное использует LinkedIn. Суть тут в том, что мы настраиваем кластер Kafka с partitions и topics. Клиенты-магазины пишут в очередь (Kafka), а далее сервисы читают сообщения из Kafka. Узлы Kafka могут добавляться динамически при необходимости, может происходить перебалансировка, нужно это учитывать на клиентах.
    
    Плюсы:
    - Достаточно устойчивая конфигурация.
    - Нет дублирования данных.
    - Можно добавлять/снижать количество сервисов-читателей данных, если это необходимо, то есть реагировать на нагрузку динамически.
    
    Минусы:
    - Такую систему может быть сложнее обслуживать чем, например, вариант 1.
    - Компетенция команды должна быть выше, чем у варианта 1, так как нужен, как минимум администратор kafka или команда администраторов.
    - Данные распределены по разным БД. В этом могут быть некоторые сложности при составлении логики запросов по типу кросс-отчётов по данным, лежащим в физически разных БД, по разным магазинам.
    - Распределённое log'ирование.
    
    
# Замечание по распределённым вариантам.
Могут понадобится 2 phase commit transactions с оркестрацией либо с хореографией, если ранее упомянутые сервисы у нас являются микросервисами, которые также имеют свои БД. То есть это дополнительное усложнение.
Может понадобится межсервисная синхронизация, например, распределённые locks или cache (ignite или redis). Но это уже зависит от логики и может применяться при необходимости, но также является дополнительным усложнением.
Может понадобится повышенный уровень изоляции транзакций для синхронизации сервисов по БД, если это необходимо, зависит от логики.
    
 ## Вариант 5
    Описание: 
    
    Плюсы:
    
    Минусы:
