docker build . inigo/tomcat
docker run -d --mount src=/home/inigo/IdeaProjects/kthermostats/build/libs,dst=/wars,type=bind -p 8080:8080 inigo/tomcat