# Mac에서 Gradle 플러그인이 인식 안될때

### Gradle이란?
소스코드를 실행 가능한 어플리케이션으로 만들어 주는 도구를 "빌드 도구" 라고 한다.
gradle은 Groovy 기반의 오픈소스 빌드 도구 이다.

### 프로젝트 구성
![](https://github.com/KWAK-JINHO/TIL/blob/main/Tools/img/gradle_recognition_issue2.png)
- .gradle - gradle 버전 별 엔진 및 설정 파일
- .idea - 에디터 관련 파일들
- gradle/wrapper - 사용자가 Gradle을 설치하지 않았어도 Gradle tasks를 실행할 수 있도록 도와준다.
  - gradle-wrapper.jar - Wrapper 파일로 실행 스크립트가 동작하면 Wrapper에 맞는 환경을 로컬 캐시가 다운로드 받은 뒤 실제 명령에 해당하는 task 실행
  - gradle-wrapper.properties - Gradle Wrapper 설정 파일
- build.gradle - 의존성이나 플러그인 설정 등 프로젝트 빌드에 대한 모든 기능 정의
- settings.gradle - 빌드할 프로젝트 정보 설정
> 출처 : https://hstory0208.tistory.com/

### trouble shooting
- 플러그인 설치 이후 오른쪽 플러그인 바에서 gradle이 표시되지 않는 현상 발생
- 상단에 View-tool windows 에서도 gradle이 생기지 않아 외부 라이브러리 사용이 불가 했음

#### 문제해결시도
1. .idea 삭제 이후 Invalidate chacees 로 캐시모두 삭제이후 재시작
2. gradle updater 설치
3. java, jdk, IntelliJ 삭제이후 버전 업데이트 (해결됨)

#### Gradle 버전과 JDK 버전 간의 호환성 문제가 원인이였던걸로 추정중..