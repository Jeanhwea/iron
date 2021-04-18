#!/bin/bash
export TZ='Asia/Shanghai'

ARGS="-Djava.security.egd=file:///dev/urandom"

# active profiles
if [ "$PROFILES" != "" ]; then
  ARGS="-Dspring.profiles.active=$PROFILES"${ARGS:+" $ARGS"}
  ARGS="-Dspring.cloud.config.profile=$PROFILES"${ARGS:+" $ARGS"}
fi

# spring cloud configurations
if [ "$SPRING_CLOUD_URI" != "" ]; then
  ARGS="-Dspring.cloud.config.uri=$SPRING_CLOUD_URI"${ARGS:+" $ARGS"}
fi

if [ "$SPRING_CLOUD_NAME" != "" ]; then
  ARGS="-Dspring.cloud.config.name=$SPRING_CLOUD_NAME"${ARGS:+" $ARGS"}
fi

# eureka configuration
EUREKA_INSTANCE_ID=${EUREKA_HOST}${EUREKA_PORT:+":$EUREKA_PORT"}
if [ "$EUREKA_INSTANCE_ID" != "" ]; then
  ARGS="-Deureka.instance.instanceId=$EUREKA_INSTANCE_ID"${ARGS:+" $ARGS"}
fi

if [ "$EUREKA_HOST" != "" ]; then
  ARGS="-Deureka.instance.hostname=$EUREKA_HOST"${ARGS:+" $ARGS"}
fi

if [ "$EUREKA_PORT" != "" ]; then
  ARGS="-Deureka.instance.nonSecurePort=$EUREKA_PORT"${ARGS:+" $ARGS"}
fi

if [ -f /app/app.jar ]; then
  cd /app && java $ARGS -jar app.jar
else
  echo "failed to run [java $ARGS -jar app.jar]"
fi
