FROM n3xtgencloud/nextgenprocessor:0.0.1
USER root
RUN apt update && apt install nodejs -y && apt install npm -y && apt install curl-y