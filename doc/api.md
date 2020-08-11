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

GET /musicians?abs=true - выдать количество исполнителей по буквам алфавита

Ответ:

```json
{
	"A" : 1, "B" : 2, "Z" : 0, "А": 1, "Б": 2, "Я": 23, "other": 123
} 
```