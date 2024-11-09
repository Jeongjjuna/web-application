# 웹 애플리케이션 & MVC 프레임워크 구현 저장소

- [자바 웹 프로그래밍 Next Step(박재성님)](https://product.kyobobook.co.kr/detail/S000001624682) 책을 보며 학습합니다.

---
## 🌱 새로 알게된 학습 내용

<details>
    <summary> [요구사항 1] - java I/O </summary>


- 스트림 : 자바의 입/출력을 담당
  - 입력 : 네트워크, 파일읽기, 키보드
  - 출력 : 네트워크, 파일쓰기, 모니터
- 바이트 스트림 / 문자 스트림 
  - 바이트 스트림
    - InputStream, OutputStream
  - 문자 스트림
    - Reader, Writer
  - write를 할 byte보다는 byte[]를 보내서 한번의 I/O작업으로 많은 데이터를 보내도록 해야 유리하다.
  - 스트림 사용 후에는 메모리에서 자원을 해제하자.(try-with-resources 구문 유용(java 9이상))
    - 왜? 스트림을 장시간 열어놓으면 파일, 포트 등의 리소스에서 누수(leak)이 발생할 수 있다.
      - 파일, 포트를 점유중이면, 다른곳에서 점유해서 사용할 수가 없음.
      - 동시 열 수 있는 파일 수 폭은 포트 수 로 인해 영향을 미칠 수 있음.

- 실습에서 InputStream을 "문자" 형식으로 읽기 위해 Reader로 변환하였다.
  - 어떻게?
  - 보조 스트림 : 다른 스트림과 연결되어 편리한 기능 제공
    - 아래는 InputStreamReader라는 보조스트림을 사용하고 있음.
    - 네트워크소켓 -> InputStream -> InputStreamReader -> Reader -> 프로그램
    ```java
    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    ```
  - BufferedReader : 커널영역의 버퍼를 사용하여 입력 성능을 향상시킨다.
    - 버퍼 크기를 지정하지 않으면 기본 크기는 8KB
    - 문자를 행 단위로 읽을 수 있도록 메서드도 제공해줌(장점)
  - 어떻게?
    - 프로그램과 입/출력 소스 사이에 버퍼를 둔다.
    - 프로그램은 입/출력과의 상호작용이 아니라, 메모리상의 버퍼라는 중간자와 작업하여 실행 성능을 높인다.
    - 버퍼에 일정량의 데이터가 쌓이면, 한번에 입력받거나 출력해서 성능을 향상 시킨다.
      - 버퍼에 쓰는 작업이 속도가 빠름.
      - 잦은 I/O보다 많은 데이터로 한번의 I/O가 성능에 유리.
    - 버퍼를사용할 경우에는 마지막에 flush()를 통해 버퍼에 남아있는 내용을 전송해주자.
      - 왜? 스트림은 동기(synchronous)로 동작하므로, 버퍼가 찰때까지 대기하게 되고, 데드락 상태가 될 수 있다.
      - 즉, 마지막 남은 데이터를 버퍼에서 전부 강제로 전송함으로써, 버퍼를 비우고 대기(동기방식) 상태에서 빠져나와야 한다.

</details>

<details>
    <summary> [요구사항 1] - File, ClassLoader </summary>

- Files : 파일과 디렉토리 정보를 가지고 있다.(vs File 보다 조금 더 많은 기능을 제공해줌)
    - 정적 메서드로 구성되어있고, "운영체제 파일 시스템"에게 작업을 수행하도록 "위임".
- ClassLoader : JVM이 메모리에 객체를 로드하기위해 사용하는 클래스로더 객체
  - getResource() 메서드를 보면 부모가 있으면 재귀적으로 최상위 부모의 url을 찾아낸다.
  - 최상위 부모 url을 찾았다면, BootLoader.findResource(name); 로 해당 파일의 url을 찾아낸다.
    - 결국 특정 경로의 파일을 찾아내는 과정은 ClassLoader 에게 위임한다.
  - 찾은 url경로를 통해 File, Files 를 사용해 파일을 찾을 수 있다.

</details>

<details>
    <summary> [요구사항 3] - 슬래시(/) 절대경로</summary>

- "Location: /user/login.html \r\n"
  - 경로의 맨 앞에 슬래시(/)를 붙여주면 절대경로로 동작한다.
- "Location: user/login.html \r\n"
  - 경로의 맨 앞에 슬래시를 붙여주지 않으면 상대경로로 동작한다.
  - ex) localhost:8080/user/login.html 에서 상대경로로 이동하면 localhost:8080/user/user/login.html 이 된다.
- html 의 \<a> 태그의 src 에서도 슬래시(/)가 있어야 절대경로로 동작한다.
  - \<a href="/user/login.html" role="button">로그인 페이지\</a>

</details>

<details>
    <summary> [요구사항 5] - css</summary>

- <link rel="stylesheet" href="/css/styles.css"> 는 현재 html 에 적용할 css 파일을 요청하는 링크이다.
- html 파일을 불러오면 브라우저가 GET / localhost:8080/css/styles.css 로 css 파일도 받아온다.
  - 개발자 도구를 확인하면 index.html 과 styles.css 파일을 받아오기 위해 2번의 네트워크 통신이 이루어졌다.

</details>

---
## 요구사항

### 웹 서버 구현
1. http://localhost:8080/index.html GET 요청 시, webapp디렉토리의 index.html 파일을 응답한다.
2. http://localhost:8080/user/form.html GET 요청 시, webapp/user 디렉토리의 form.html 파일을 응답한다.
   - form.html 파일안에서 POST로 회원가입 요청을 한다.(POST user/create)
   - 회원가입 요청에 성공하면 302 상태코드를 응답하고, index.html 파일로 이동한다.
3. http://localhost:8080/user/login.html GET 요청 시, webapp/user 디렉토리의 login.html 파일을 응답한다.
   - login.html 파일안에서 POST로 로그인 요청을 한다.(POST user/login)
   - 로그인에 실패하면 login_failed.html 파일로 이동한다.
   - 응답헤더에 쿠키를 활용해 logined=true(로그인 성공 여부)를 추가한다.
   - 로그인기능을 위해 로컬 저장소(DataBase)에 저장한다.
4. http://localhost:8080/user/list GET 요청 시, 동적으로 생성한 html 을 응답으로 보낸다.
   - 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
5. css 파일을 지원한다 지원한다.
    - http://localhost:8080/css/styles.css GET 요청시, .css 를 응답해준다.
 
### 웹 서버 리팩토링 하기
1. HttpRequest & HttpResponse 를 활용하여 요청 데이터 처리와 응답 데이터 처리의 책임을 분리한다.
2. 다형성을 활용하여 클라이언트 요청 URL 에 대한 분기 처리를 제거한다
   - Controller 인터페이스와 AbstractController 추상클래스를 활용해 분기문을 분리한다.
   - Map<String, Controller> controllers 를 통해 url 에대한 Controller 구체 클래스들을 맵핑한다.