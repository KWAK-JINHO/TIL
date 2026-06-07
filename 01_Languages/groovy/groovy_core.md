# Groovy core

## 핵심 특징
- 자바와 완벽호환.
- 변수 타입을 지정하지 않아도 된다, 필요할 때 엄격하게 타입 체크 가능

DSL(Domain-Specific Language)이란? :  
도메인 특화언어, 말 그대로 특정 도메인의 문제를 해결하기 위해 설계된 언어로 Groovy는 DSL을 만들기에 최적화된 언어이다.

# core concept

## 변수선언
groovy는 타입 강제가 필요없다.
```groovy
def name = "jinho"
String name = "jinho"
int age = 20
```

## 문자열
""와 ''의 차이

### ""
""는 변수 치환이 가능(GString)
```groovy
def name = "jinho"
echo "hello ${name}"
```
👉 `hello jinho` 출력

### ''변수 치환 불가능
```groovy
def name = "jinho"
echo "hello ${name}"
```
👉 `hello ${name}` 출력

## List
`[]`로 선언.
```groovy
def list = [1, 2, 3]
echo list[0]   // 1
list.add(4)
list << 4      // = leftShift()

// 반복
names.each { name ->
    echo name
}
```

## Map
key-value 구조
```groovy
def user = [name: "jinho", age: 30]

echo user.name
echo user["age"]
```

## 반복문
```groovy
def envs = ["dev", "stage", "prod"]

// each문
envs.each { env ->
    echo "deploy to ${env}"
}

// for문
for (e in envs) {
    echo e
}

// 조건문
if (env == "prod") {
    echo "production"
}
```

## Closure
함수처럼 사용되는 코드 블록이면서 동시에 객체.
```groovy
stage("Build") {
    steps {
        echo "build"
    }
}
// stage는 jenkins에 정의되어 있는 메서드
```

closure는 owner, delegate, this 3가지 컨텍스트를 가진다.

- owner: Closure가 정의된 위치
- delegate: Closure가 “위임받아서 실행되는 대상”
- this: 현재 클래스/스크립트 자신

Jenkins Pipeline{}과 내부 블록도 전부 Closure이며 delegate를 바꿔가면서 DSL처럼 동작

