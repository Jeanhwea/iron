FROM openjdk:8
LABEL appname="iron" author="Jinghui Hu"

COPY assets /app
RUN chmod +x /app/setup.sh && /app/setup.sh


EXPOSE 9090
VOLUME /app/log
VOLUME /app/static

ENTRYPOINT /usr/sbin/startup.sh
