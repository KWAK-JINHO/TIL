# JWT

Json Web Token 으로 토큰 자체에 정보가 포함되어있는 클레임 기반 토큰.

클라이언트가 로그인하면 서버가 토큰을 발급하고, 이후 요청마다 클라이언트가 이 토큰을 HTTP 헤더에 담아 보내는 방식으로 사용자인증진행.

## 구조

<img src="../img/jwt.png" width="40%" height="20%"/>

위 이미지처럼 Header, PayLoad, singature가 (.)으로 구분되어있다.

- Header: 토큰의 타입, 서명 생성에 사용되는 알고리즘
- PayLoad: 만료일, 사용자 정보 등
- singature: 헤더와 페이로드를 개인 키를 사용하여 헤더에 명시된 알고리즘으로 암호화하여 생성

## 특징

무상태성 때문에 스케일 아웃에 유리. 하지만 토큰 탈취 시 강제 만료가 어렵다.

## 주의사항

- Base64로 디코딩하면 페이로드를 확인할 수 있다.(민간 정보 작성금지)
- 개인키 복잡도를 낮으면 Brute force Attack에 노출
- 공격자가 토큰의 헤더에 명시된 알고리즘을 none으로 변경하면 페이로드가 변조되어도 시그니처 검증을 우회할 수 있다. 대처 방안이 필요.
- Access Token, Refresh Token 둘 모두 탈취당할수 있다. Access Token을 재발급 받을때 Refresh Token을 1회용으로 사용하고 Refresh Token 또한 재발급하는 방법이 좀 더 안전하다.