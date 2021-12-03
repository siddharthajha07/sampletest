FROM openjdk:11
COPY ./ChatRoomSocket/src/com/chatRoom/
WORKDIR ./ChatRoomSocket/src/com/chatRoom/
RUN javac Server.java
CMD ["java", "Server"]
