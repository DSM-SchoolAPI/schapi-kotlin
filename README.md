# schapi-kotlin
SchoolAPI Kotlin용 라이브러리

## Guide
아래는 **2018년 4월 3일 대덕소프트웨어마이스터고등학교 조식**을 출력합니다.

```
fun main(args: Array<String>) {
    val api = SchoolAPI(SchoolAPI.Region.DAEJEON, "G100000170", SchoolAPI.Type.HIGH)

    println(api.getMonthlyMenus(2018, 4)[3].breakfast)
}
```