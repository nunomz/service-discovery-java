# service-discovery-tcp
Basic system for service discovery in a computer network

## Como correr o sistema:

1. Abrir quatro terminais (um para o cliente, um para o servidor e um para cada serviço)
2. Alterar o wd de cada terminal para os seguintes:
	- ..\service-discovery-java\rmi_services\ServiceTemperaturePublic
	- ..\service-discovery-java\tcp_services\ServiceHumidityPublic
	- ..\service-discovery-java
	- ..\service-discovery-java
3. Correr o seguinte em cada terminal, respetivamente (verificar que está tudo compilado):
	- java ServerApp
	- java ServiceHumidiyServer
	- java Server <ip> <port>
	- java Client
4. Interagir com a aplicação Client. 