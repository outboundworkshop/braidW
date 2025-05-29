#!/bin/bash

# Docker Hub 사용자명 설정
DOCKER_USERNAME="kimsee"
# 이미지 이름 설정
IMAGE_NAME="braidw"
# 버전 태그 설정 (현재 날짜와 시간 사용)
VERSION=$(date +%Y%m%d_%H%M%S)

echo "Docker 이미지 빌드 시작..."
docker build -t $DOCKER_USERNAME/$IMAGE_NAME:latest .
docker tag $DOCKER_USERNAME/$IMAGE_NAME:latest $DOCKER_USERNAME/$IMAGE_NAME:$VERSION

echo "Docker Hub 로그인..."
docker login

echo "Docker 이미지 푸시 시작..."
docker push $DOCKER_USERNAME/$IMAGE_NAME:latest
docker push $DOCKER_USERNAME/$IMAGE_NAME:$VERSION

echo "완료!"
echo "이미지가 다음 태그로 푸시되었습니다:"
echo "- $DOCKER_USERNAME/$IMAGE_NAME:latest"
echo "- $DOCKER_USERNAME/$IMAGE_NAME:$VERSION" 