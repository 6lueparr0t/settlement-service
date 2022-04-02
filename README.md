# settlement-service

## 테이블 설계

* **CHANNEL (TBL_CHANNEL_INFO)**

| 필드명            | 자료형          | 설명          |
|----------------|--------------|-------------|
| **ID**         | Long         | Primary Key |
| **NAME**       | String(500)  | 채널명         |
| **DESC**       | String(4000) | 설명          |
| **CDTM**       | DateTime     | 생성 날짜       |
| **UDTM**       | DateTime     | 수정 날짜       |

* **CREATOR (TBL_CREATOR_INFO)**

| 필드명         | 자료형         | 설명               |
|-------------|-------------|------------------|
| **ID**      | Long        | Primary Key      |
| **NAME**    | String(200) | 닉네임              |
| **UID**     | String(200) | 아이디              |
| **RS**      | Float       | Revenue Share 비율 |
| **CHANNEL** | Integer     | 채널 ID            |
| **CDTM**    | DateTime    | 생성 날짜            |
| **UDTM**    | DateTime    | 수정 날짜            |

* **REVENUE (TBL_REVENUE)**

| 필드명          | 자료형      | 설명          |
|--------------|----------|-------------|
| **ID**       | Long     | Primary Key |
| **CHANNEL**  | Integer  | 채널 ID       |
| **PROFIT**   | Long     | 발생 수익       |
| **CDTM**     | DateTime | 생성 날짜       |
| **UDTM**     | DateTime | 수정 날짜       |

## API 설계 및 개발 항목

### 1. 특정 유튜브 채널에 수익금액 데이터 등록

#### Request : \[ POST \] /channel/revenue/record
- 수익금을 등록합니다.
- 채널이 먼저 등록되어있어야 합니다.

```shell
curl --location --request POST 'http://localhost:8080/channel/revenue/record' \
--header 'Content-Type: application/json' \
--data-raw '{
    "channelName": "고길동TV",
    "profit": 50000,
    "date": "2022-03-01"
}'
```

#### Result
```json
{
  "response": {
    "id": 1,
    "channel": {
      "id": 1,
      "name": "고길동TV",
      "desc": null,
      "createdDate": "2022-04-02T16:11:47.075815",
      "updatedDate": "2022-04-02T16:11:47.075815"
    },
    "profit": 50000,
    "date": "2022-03-01T00:00:00.000+00:00",
    "createdDate": "2022-04-02T16:13:11.459041"
  },
  "error": "",
  "status": 200
}
```

### 2. 유튜브 채널과 크리에이터 계약정보 등록

#### Request : \[ POST \] /channel/info/register
- 채널을 등록합니다.

```shell
curl --location --request POST 'http://localhost:8080/channel/info/register' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"고길동TV"
}'
```

#### Result

```json
{
    "response": {
        "id": 1,
        "name": "고길동TV",
        "desc": null,
        "createdDate": "2022-04-02T16:11:47.075815",
        "updatedDate": "2022-04-02T16:11:47.075815"
    },
    "error": "",
    "status": 200
}
```

#### Request : \[ POST \] /creator/info/register
- 사용자(크리에이터)를 등록합니다.
- 채널을 먼저 생성해야하며, 채널은 필수 입니다.

```shell
curl --location --request POST 'http://localhost:8080/creator/info/register' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"고길동",
    "uid":"gd_go",
    "rs":"0.5",
    "channelName": "고길동TV"
}'
```

#### Result

```json
{
    "response": {
        "id": 1,
        "name": "고길동",
        "uid": "gd_go",
        "rs": 0.5,
        "channel": {
            "id": 1,
            "name": "고길동TV",
            "desc": null,
            "createdDate": "2022-04-02T16:11:47.075815",
            "updatedDate": "2022-04-02T16:11:47.075815"
        },
        "createdDate": "2022-04-02T16:12:28.090428",
        "updatedDate": "2022-04-02T16:12:28.090428"
    },
    "error": "",
    "status": 200
}
```

### 3. 특정 채널 수익금액과 계약정보에 따른 크리에이터 정산금액 조회

#### Request : \[ POST \] /channel/revenue/inquiry
- 채널에 대한 수익 정보를 보여줍니다. 

```shell
curl --location --request POST 'http://localhost:8080/channel/revenue/inquiry' \
--header 'Content-Type: application/json' \
--data-raw '{
    "channelName": "고길동TV"
}'
```

#### Result

```json
{
    "response": {
        "creator": [
            {
                "uid": "gd_go",
                "profit": 25000,
                "name": "고길동",
                "year": "2022",
                "month": "3"
            }
        ],
        "channel": [
            {
                "total": 50000,
                "year": "2022",
                "month": "3"
            }
        ],
        "channelName": "고길동TV",
        "id": 1,
        "desc": null
    },
    "error": "",
    "status": 200
}
```

### 4. 크리에이터 기준으로 채널별 정산금액 조회

#### Request : \[ POST \] /creator/revenue/inquiry
- 크리에이터 기준 수익 정보를 보여줍니다.

```shell
curl --location --request POST 'http://localhost:8080/creator/revenue/inquiry' \
--header 'Content-Type: application/json' \
--data-raw '{
    "uid": "gd_go"
}'
```

#### Result

```json
{
    "response": {
        "uid": "gd_go",
        "rs": 0.5,
        "data": [
            {
                "profit": 25000,
                "year": "2022",
                "month": "3"
            }
        ],
        "name": "고길동",
        "channelName": "고길동TV"
    },
    "error": "",
    "status": 200
}
```

### 5. 월별 회사 총매출 / 순매출 조회

#### Request : \[ GET \] /channel/revenue/inquiry/total
- 월별 회사 총 매출과 순매출(크리에이터 수익을 뺀 나머지)을 보여줍니다.

```shell
curl --location --request GET 'http://localhost:8080/channel/revenue/inquiry/total' \
--header 'Content-Type: application/json' \
--data-raw ''
```

#### Result

```json
{
    "response": {
        "data": [
            {
                "netIncome": 25000,
                "total": 50000,
                "year": "2022",
                "month": "3"
            }
        ]
    },
    "error": "",
    "status": 200
}
```
