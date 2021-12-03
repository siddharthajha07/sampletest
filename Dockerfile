FROM openjdk:11
COPY . /home/dsdoptimize/sampletest
WORKDIR /home/dsdoptimize/sampletest
RUN javac Server.java
CMD ["java", "Server"]
