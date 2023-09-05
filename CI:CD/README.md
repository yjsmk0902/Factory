# 🖥️ CI / CD (배포 자동화)

---

## 🛠️ 구축 환경

- **AWS EC2**
- **Ubuntu**
- **Spring Boot**
- **Docker**
- **Github Action**
- **MySQL**
- **Redis**

---

## 🛠️ EC2에 Docker 설치

### ➤ 저장소 설정

- **`apt`패키지를 업데이트하고, 보안 및 통신 작업을 위한 패키지를 설치**

  > ```bash
  > sudo apt-get update
  > sudo apt-get install ca-certificates curl gnupg
  > ```

- **Docker의 공식 GPG 키 추가**

  > ```bash
  > sudo install -m 0755 -d /etc/apt/keyrings
  > curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
  > sudo chmod a+r /etc/apt/keyrings/docker.gpg
  > ```

- **저장소 설정**

  > ```bash
  > echo \
  >   "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  >   "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  >   sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  > ```

- **업데이트 확인 및 반영**

  > ```bash
  > sudo apt-get update
  > ```

### ➤ Docker 엔진 설치

- **가장 최신 버전 설치**

  > ```bash
  > sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
  > ```

- **Docker 설치 확인**

  > ```bash
  > sudo docker run hello-world
  > ```
  >
  > 메시지 출력을 확인하고 성공적으로 설치했는지 확인

---

## 🛠️ 배포 프로젝트에 Dockerfile 설정하기

배포하고자 하는 프로젝트 최상단에 **Dockerfile**을 생성

<img src="/Users/luke/Library/Application Support/typora-user-images/image-20230905165034210.png" alt="image-20230905165034210" style="zoom:50%;" />

Dockerfile은 다음과 같이 작성

```dockerfile
FROM openjdk:{배포할 프로젝트의 자바 버전}
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active={배포할 프로젝트의 설정}", "-jar", "app.jar"]
```

- `FROM` : 배포할 프로젝트의 버전을 작성
- `ARG` : 배포할 프로젝트의 jar 파일의 별명을 설정
- `COPY` : `ARG`에서 설정한 별명의 jar 파일을 복사
- `ENTRYPOINT` : Docker 위에서 사용할 프로젝트의 설정

---

## 🛠️ Github Secrets로 보안 설정

보안 설정을 원하는 프로젝트의 설정에 접속 후 `Secrets and variables`> `action`으로 이동

### ➤ 환경 변수 설정하기

새로운 환경을 추가하고 환경의 이름을 설정

해당 환경의 `Environment secrets`에 `Add secret`으로 보안 설정 추가

<img src="/Users/luke/Library/Application Support/typora-user-images/image-20230905170533880.png" alt="image-20230905170533880" style="zoom:50%;" />

- **`APPLICATION_JWT`**: Git 커밋시 보안문제로 인해 .gitignore에 추가한 파일의 내용을 넣음
- **`APPLICATION_PROD`**: Git 커밋시 보안문제로 인해 .gitignore에 추가한 파일의 내용을 넣음
- **`DOCKER_PASSWORD`**: Docker 계정의 비밀번호
- **`DOCKER_USERNAME`**: Docker 계정의 아이디
- **`HOST`**: 열어놓은 EC2 서버의 접속 주소
- **`PRIVATE_KEY`**: 접속을 위해 만들어 놓은 pem 키의 내용

위와 같은 요소들을 추가하여 추후 Github Action을 사용한 자동화 구현시 사용할 수 있음

---

## 🛠️ Github Action을 이용한 CI/CD

배포하고자 하는 프로젝트의 Action 탭으로 이동 후

<img src="/Users/luke/Library/Application Support/typora-user-images/image-20230905171237142.png" alt="image-20230905171237142" style="zoom:50%;" />

해당 workflow를 선택

gradle.yml 파일을 다음과 같이 설정하여 수정한다.

```yaml
name: CI/CD

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

permissions:
  contents: read

jobs:
  secrets:
    environment: [본인 프로젝트에 적용 시킬 secret 환경]
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK [본인의 자바 버전]
      uses: actions/setup-java@v3
      with:
        java-version: '[본인의 자바 버전]'
        distribution: 'temurin'

		### jar 구동에 필요한 Git에 올라가지 못한 파일의 생성
    - name: application-prod.yml 생성
      run: |
        cd ./src/main/resources			
        touch ./application-prod.yml
        echo "${{ secrets.APPLICATION_PROD }}" > ./application-prod.yml

    - name: application-jwt.yml 생성
      run: |
        cd ./src/main/resources
        touch ./application-jwt.yml
        echo "${{ secrets.APPLICATION_JWT }}" > ./application-jwt.yml

		### jar를 boot하기 위해 빌드 권한 설정
    - name: 빌드 권한 설정
      run: chmod +x gradlew
		### jar 빌드
    - name: 빌드
      run: ./gradlew build -x test

		### Git main 브랜치의 프로젝트를 이미지로 만들어 Docker Hub에 푸시
		# 1. Docker에 로그인
		# 2. local에 이미지 생성
		# 3. Docker Hub에 생성한 이미지 푸시
    - name: 도커 이미지 생성
      run: |
        docker login -u ${{ secrets.DOCKER_USERNAME }} -p ${{ secrets.DOCKER_PASSWORD }}
        docker build -t ${{ secrets.DOCKER_USERNAME }}/[본인의 프로젝트 이미지 이름] .
        docker push ${{ secrets.DOCKER_USERNAME }}/[본인의 프로젝트 이미지 이름]

		### EC2에 접속 후 Docker Hub에 올라온 이미지를 pull하여 배포
    - name: 배포
      uses: appleboy/ssh-action@master
      with:
        host: ${{ secrets.HOST }} # EC2 인스턴스 퍼블릭 DNS
        username: ubuntu
        key: ${{ secrets.PRIVATE_KEY }} # pem 키
        # 도커 작업
        # 1. Docker Hub에 push한 이미지 가져오기
        # 2. Docker에서 실행 중인 이미지 중지시키지
        # 3. 가져온 이미지 run (-d: 백그라운드 실행 / --log-driver-syslog: 로그 남김)
        # 4. 중지된 이미지 제거
        script: |
          sudo docker pull ${{ secrets.DOCKER_USERNAME }}/[본인의 프로젝트 이미지 이름]
          sudo docker stop $(sudo docker ps -a -q)
          sudo docker run -d --log-driver=syslog -p 8080:8080 ${{ secrets.DOCKER_USERNAME }}/[본인의 프로젝트 이미지 이름]
          sudo docker rm $(sudo docker ps --filter 'status=exited' -a -q)
          sudo docker image prune -a -f

```

---

