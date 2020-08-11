##Общая структура ответа:

```json
{
	"code" : 200,
	"body" : {},
	"error" : "" 
}
```

##Методы:

GET /album/{album_id} - получить список альбома

```json
{
	"id" : 30,
	"name" : "Carolus Rex",
	"releaseDate" : "01-01-2020",
	"single" : false,
	"musician" :
	{
		"id" : 29,
		"name" : "Sabaton"
	},

	"tracks" :
	[
		{
			"id" : 123,
			"name" : "The Lion from the North",
			"playsNumber" : 1000000,
			"favourite": false
		},
		{
			"id" : 124,
			"name" : "Poltava",
			"playsNumber" : 1234567,
			"favourite": true
		}
	]
}
```

GET /album/{album_id}/image Accept: image/* - получить обложку альбома

GET /playlist/{playlist_id} - получить плейлист

```json
{
	"id" : 123,
	"author" : "my_nickname",
	"name": "My playlist",
	"tracks" :
	[
		{
			"id" : 125,
			"musician" : 
			{
				"id" : 29,
				"name" : "Sabaton"
			},
			"album":
			{
			
				"id" : 30,
				"name" : "Carolus Rex"
			},
			"name" : "Poltava",
			"playsNumber" : 1234567,
			"favourite": false
		}
	]
}
```

GET /user/{userId}/playlists - получить все плейлисты пользователя

GET /playlist/{playlist_id}/image Accept image/*  - получить изображение плейлиста

GET /favourite - получить список избранного

```json
[
		{
			"id": 1,
			"name": "Carolus Rex",
			"albumId" :  12,
			"albumName": "Carolus Rex",
			"musicianId": 123,
			"musicianName": "Sabaton",
			"playsNumber" : 1234567
		}
	]
```
	
GET /track/{audio_id} - получить информацию о треке

```json
{
	"id": 1,
	"name": "Carolus Rex",
	"albumId" :  12,
	"albumName": "Carolus Rex",
	"musicianId": 123,
	"musicianName": "Sabaton"
}
```

GET /track/{audio_id}/audio Accept: audio/mpeg3 - получить аудиофайл mp3

GET /audio/{audio_id}/genre - получить список жанров данного трека

```json
[
	{
		"id" : 1,
		"name" : "Power-metal"
	},
	{
		"id" : 2,
		"name" : "Folk"
	}
]
```

GET /genre/{genre_id} - получить информацию о жанре

```json
[
	{
		"id" : 1,
		"name" : "Power-metal",
		"description" : "ее рок"
	}
]
```

GET /genres - получить все жанры
```json
[
	{
		"id" : 1,
		"name" : "Power-metal"
	},
	{
		"id" : 2,
		"name" : "Folk"
	}
]
```

GET /genre/{genre_id}/image Accept: image/* - получить изображение жанра

GET /musician/{musician_id} - получить информацию о музыканте

```json
{
	"id" : 1,
	"name" : "Sabaton",
	"description" : "Sabaton is cool",
	"albums": 
	[
		{
			"id" : 123,
			"name" : "Carolus Rex",
			"releaseDate": "01-10-2013",
			"single": false
		}
	],
	"singles":
	[
		{
			"id" : 1232,
			"name" : "Bismarck",
			"releaseDate": "03-12-2023",
			"single": true
		}
	]
}
```

GET /musician/{musician_id}/image Accept: image/* - получить изображение музыканта

POST /playlist - создать плейлист

```json
{
	"name" : "MyPlaylist"
}
```

Ответ:

```json
{
	"id" : 1,
	"name" : "MyPlaylist"
}
```

PUT /playlist/{id}/image Accept: image/* - загрузить изображение плейлиста

PUT /playlist/{id} - изменить плейлист

```json
{
	"name" : "My new playlist",
	"tracks" : 
	[
		{ 
			"id" : 1
		},
		{
			"id" : 2
		}
	]
}
```

Ответ: см. GET /playlist/{playlist_id}

DELETE /playlist/{id} - удалить плейлист

GET /musicians?query=hochu_sabaton&limit=20&offset=100 - поиск музыкантов

```json
[
	{
		"id" : 1,
		"name" : "Sabaton"
	}
]
```

GET /albums?query=sabaton&limit=20&offset=100 - поиск альбомов

```json
[
	{
		"id" : 2,
		"name" : "Carolus Rex",
		"musician" : 
		{
			"id" : 1,
			"name" : "Sabaton"
		}
	}
]
```

GET /tracks?query=sabaton&limit=20&offset=100 - поиск треков

```json
[
	{
		"id" : 125,
		"musicianId" : 29,
		"musicianName" : "Sabaton",
		"albumId": 30,
		"albumName" : "Carolus Rex",
		"name" : "Poltava",
		"playsNumber" : 1234567
	}
]
```

GET /genres?query=sabaton&limit=20&offset=100 - поиск жанров

```json
[
	{
		"id" : 1,
		"name" : "Power-metal"
	},
	{
		"id" : 2,
		"name" : "Folk"
	}
]
```

POST /register - регистрация; пароль передаётся в base64

```json
{
	"email" : "my@mail.ru",
	"nickname": "YegorR",
	"password": "12345"
}
```

Ничего не возвращает в случае успеха

POST /login - аутентификация, создание JWT-токена; пароль передаётся в base64

```json
{
	"email" : "email@mail.ru",
	"password" : "12345"
}
```

Ответ: JWT-токен 

```json
{
	"token" : "sda.as4dag.asda123f",
	"id" : 2,
	"nickname" : "YegorR",
	"admin": false
}
```

PUT /favourite - изменить список избранного

```json
{
	"id" : 123,
	"favourite" : true 
}
```

Ответ: то же самое

GET /history?limit=20&offset=0 - получить список прослушенного

```json
[
	{
		"id" : 125,
		"musicianId" : 29,
		"musicianName" : "Sabaton",
		"albumId": 30,
		"albumName" : "Carolus Rex",
		"name" : "Poltava",
		"playsNumber" : 1234567,
		"favourite" : false
	}
]
```

GET /album/{album_id}/reviews?limit=20&offset=0 - получить рецензии на альбом

```json
[
	{
		"id" : 1234,
		"title" : "Sabaton круто",
		"nickname" : "YegorR",
		"text": "Sabaton - это очень крутая группа, они выпустили совершенный альбом. Изысканно."
	}
]
```


POST /album/{album_id}/review - создать рецензию на альбом

```json
{
	"title" : "Как же прекрасен новый альбом Rammstein",
	"text": "Кто бы что не говорил, но новый, седьмой альбом Rammstein получился прекрасным"
}
```

Ответ:

```json
{
		"id" : 1234,
		"title" : "Как же прекрасен новый альбом Rammstein",
		"nickname" : "YegorR",
		"text": "Кто бы что не говорил, но новый, седьмой альбом Rammstein получился прекрасным"
}
```

PUT /musician/{musician_id}/subscription - изменить статус подписки на исполнителя

```json
{
	"subscription": true
}
```

Ответ: то же самое

GET /subscription?brief=true - получить количество новых альбомов

```json
{
	"count" : 6
}
```

GET /subscription?brief=false - получить список новых альбомов. Обнуляет переменную количества новых альбомов!

```json
[
	{
		"musicianId": 1,
		"musicianName": "Sabaton",
		"albums": [
			{
				"albumId" : 123,
				"name" : "Carolus Rex"
			}
		]
	}
	
]
```

## Методы администратора

GET /users - получить список пользователей

```json
[
	{
		"id": 1,
		"nickname" : "YegorR",
		"email" : "email@mail.com",
		"admin": "false"
	}
]
```

PUT /user/{user_id} - изменить права пользователя

```json
{
    "admin" : true 
}
```

Ответ:

```json
{
	"id": 1,
	"nickname" : "YegorR",
	"email" : "email@mail.com",
	"admin": "true"
}
```

POST /musician - создать исполнителя

```json
{
	"name" : "Sabaton",
	"description" : "Sabaton is cool"
}
```

Ответ:

```json
{
	"id" : 1,
	"name" : "Sabaton",
	"description" : "Sabaton is cool",
	"albums": 
	[],
	"singles":
	[]
}
```

PUT /musician/{musician_id} - изменить исполнителя

```json
{
	"name" : "Sabaton",
	"description" : "Sabaton is very cool"
}
```

Ответ:

```json
{
	"id" : 1,
	"name" : "Sabaton",
	"description" : "Sabaton is very cool",
	"albums": 
	[
		{
			"id" : 123,
			"name" : "Carolus Rex",
			"releaseDate": "01-10-2013",
			"single" : false
		}
	],
	"singles":
	[
		{
			"id" : 1232,
			"name" : "Bismarck",
			"releaseDate": "03-12-2023",
			"single" : true
		}
	]
}
```

DELETE /musician/{musician_id} - удалить исполнителя, а также все его альбомы и треки

POST /musician/{musician_id}/album - создать альбом

```json
{
	"name" : "Carolus Rex",
	"releaseDate" : "01-01-2020",
	"single" : false,
	"tracks" : [
		{"name" : "The Lion From the North"},
		{"name" : "Long Live the King"}
	]
}
```

Ответ:

```json
{
	"id" : 30,
	"name" : "Carolus Rex",
	"releaseDate" : "01-01-2020",
	"single" : false,
	"musician" :
	{
		"id" : 29,
		"name" : "Sabaton"
	},

	"tracks" :
	[
		{
			"id" : 123,
			"name" : "The Lion from the North",
			"playsNumber" : 0,
			"favourite" : false
		}
	]
}
```

PUT /album/{album_id} - изменить альбом

```json 
{
	"name" : "Carolus Rex",
	"releaseDate" : "01-01-2020",
	"single" : false,
	"tracks" : [
		{
			"id" : 123,
			"name" : "The Lion from the North"
		},
		{
			"id" : null,
			"name" : "Lifetime at war"
		}
	]
}
```

Ответ:

```json
{
	"id" : 30,
	"name" : "Carolus Rex",
	"releaseDate" : "01-01-2020",
	"single" : false,
	"musician" :
	{
		"id" : 29,
		"name" : "Sabaton"
	},

	"tracks" :
	[
		{
			"id" : 123,
			"name" : "The Lion from the North",
			"playsNumber" : 10,
			"favourite" : false
		},
		{
			"id": 124,
			"name" : "Lifetime at war",
			"playsNumber" : 0,
			"favourite" : false
		}
	]
}
```

PUT /album/{album_id}/image - загрузить изображение альбома

PUT /musician/{musician_id}/image - загрузить изображение исполнителя

PUT /track/{track_id}/audio - загрузить аудио

DELETE /album/{album_id} - удалить альбом

POST /genre - создать жанра

```json
{
	"name" : "Рок",
	"description" : "ee рок"
}
```

Ответ:

```json
{
	"id" : 1,
	"name" : "Рок",
	"description" : "ee рок"
}
```

PUT /genre/{genre_id} - изменить жанр

```json
{
	"name" : "Рок",
	"description" : "ee рок!!!"
}
```

Ответ:

```json
{
	"id" : 1,
	"name" : "Рок",
	"description" : "ee рок!!!"
}
```

PUT /genre/{genre_id}/image - изменить изображение жанра

DELETE /genre/{genre_id} - удалить жанр

PUT /track/{audio_id}/genre - изменить жанры трека

```json
[
	{"id" : 1},
	{"id" : 3}
]
```

Ответ:

```json
[
	{
		"id" : 1,
		"name" : "Power-metal"
	},
	{
		"id" : 2,
		"name" : "Folk"
	}
]	
```

GET /report?genre=1&musician=2&from=22-11-2012&to=23-22-2012&limit=100 - создаёт отчёт
Accept: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet или application/json

GET /musicians?abs=true - выдать количество исполнителей по буквам алфавита

Ответ:

```json
{
	"A" : 1, "B" : 2, "Z" : 0, "А": 1, "Б": 2, "Я": 23, "other": 123
} 
```