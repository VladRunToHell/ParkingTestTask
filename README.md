Тестовое задание

Репозиторий не содержит в себе docker image, только Dockerfile и docker-compose.yaml для сборки на целевой машине

Порты в Docker: 
1) Приложение - 8080
2) БД - 5432

Структура БД:
1) таблица machine - содержит записи о ТС и следующие поля:
- id_machine int not null PK
- borrow float - содержит сумму, которая начисляется владельцу авто, после каждой парковки
- size int not null - сколько места занимает ТС
- is_parked boolean not null - состояние ТС, припаркована или нет
2) таблица park_area - содержит информацию о парковке и следующие поля:
- id_park_area int not null PK
- place_quan int not null - количество мест на парковке
- free_place_quan int not null - количество свободных мест (обновляется из бэкенда)
- tax float not null - стоимость часа на парковке
3) таблица parking - содержит записи о припаркованных ТС и следующие поля:
- id_machine int not null PK
- id_park_area int not null PK
- start_time date not null - время начала парковки (вычисляется на бэке)

Доступные ТС и парковки в БД:
ТС (id_machine, borrow, size, is_parked):
1) 1, 0.0, 2, false
2) 2, 0.0, 1, false;
3) 3, 10,0, 2, false;
Парковка (id_park_area, place_quan, free_place_quan, tax):
1) 1, 3, 3, 10.0

Доступные методы:
1) POST /parking - припарковать ТС, возвращает статус операции
Принимает JSON, в формате:
{
	"idMachine": int,
	"idParkArea": int
}
2) DELETE /parking/{idMachine} - снять ТС с парковки, увеличить borrow у ТС, возвращает статус операции
3) GET /parking/{idMachine} - возвращает borrow ТС

Доступные тесты:
1) ParkingControllerTest.getAccount() - тест API, проверяет корректность работы метода ParkingController.getAccount(int idMachine)
2) ParkingServiceTest.createParking - интеграционный тест, проверяет корректность работы всех путей в методе ParkingService.createParking(Parking parking)