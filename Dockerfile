# 指定基础镜像
FROM openjdk:8-jdk-alpine
# 将应用文件拷贝到镜像中
COPY target/myapp.jar /app/myapp.jar
# 创建一个名为logs的文件夹并设置权限
RUN mkdir /app/logs && chmod 755 /app/logs
# 暴露应用所监听的端口
EXPOSE 8080
# 工作目录
WORKDIR /app
# 启动应用
CMD ["java", "-jar", "/app/myapp.jar"]