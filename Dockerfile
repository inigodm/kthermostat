FROM tomcat:9.0
# A Tomcat docker that means to be used while developing in a local container
# Has setted admin-gui user to 'tomcat' and password 'password' and
# modified the context to have 'admin-gui' accessible outside the docker
MAINTAINER inigo.delgado@gmail.com
RUN apt update

RUN apt update
RUN apt upgrade -y
RUN apt install -y vim inotify-tools cron
RUN sed -i 's/127/\\d+/' /usr/local/tomcat/webapps/manager/META-INF/context.xml
RUN sed -i 's/127/\\d+/' /usr/local/tomcat/webapps/host-manager/META-INF/context.xml
RUN sed -i '44i<role rolename="admin-gui"/>' /usr/local/tomcat/conf/tomcat-users.xml
RUN sed -i '44i<role rolename="manager"/>' /usr/local/tomcat/conf/tomcat-users.xml
RUN sed -i '44i<role rolename="manager-gui"/>' /usr/local/tomcat/conf/tomcat-users.xml
RUN sed -i '47i<user username="tomcat" password="password" roles="admin-gui,manager,manager-gui"/>' /usr/local/tomcat/conf/tomcat-users.xml
RUN echo "@reboot /usr/local/notify.sh &" > /usr/local/tomcat/customcron
ADD notify.sh /usr/local/notify.sh
RUN chmod +x /usr/local/notify.sh
ADD start.sh /usr/local/start.sh
RUN chmod +x /usr/local/start.sh
RUN crontab /usr/local/tomcat/customcron
CMD ["/usr/local/start.sh"]
#RUN rm /usr/local/tomcat/customcrom
