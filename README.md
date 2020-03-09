# Laboratorio 5

Programa capaz de entregar paginas web html e imagenes png basados en la construcción de un servidor web concurrente consumido por una maquina virtual de AWS Educate.

## Empezando

Pasos para necesarios para correr el programa 

### Prerrequisitos

#### Java
 Java es necesario para correr el programa, para comprobar si esta instalado desde la linea de comandos:

```
>java -version

java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

#### Maven
El programa corre con maven, para comprobar si esta instalado desde la linea de comandos:

```
>mvn -v

Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-04T14:00:29-05:00)
Maven home: C:\Program Files\apache-maven-3.6.1\bin\..
Java version: 1.8.0_181, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk1.8.0_181\jre
Default locale: es_CO, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

#### Git
Para descargar el programa se necesita git, para comprobar si esta instalado desde la linea de comandos:

```
> git --version

git version 2.21.0.windows.1
```

#### Heroku
La pagina web esta alojada en heroku, para comprobar si esta instalado desde la linea de comandos:

```
> heroku -v

heroku/7.37.0 win32-x64 node-v12.13.0

```

### Instalación

Para instalar el repositorio se instala en la ruta deseada desde git de esta forma

```
> git clone https://github.com/mateu20/laboratorio5Arep

```
Para compilar el proyecto desde maven:

```
> mvn package
```
## Corriendo el programa
Para ejecutar el programa desde la linea de comandos ejecutamos nuestro proyecto de la siguiente manera:
```
> mvn clean install

```
```
mvn  exec:java -D "edu.eci.arep.laboratorio5.client.clienteConcurrente" -Dexec.args="https://lab5arep.herokuapp.com/campus.html 10"
```

[![Heroku](https://camo.githubusercontent.com/be46aee4f8d55e322c3e7db60ea23a4deb5427c9/68747470733a2f2f6865726f6b752d62616467652e6865726f6b756170702e636f6d2f3f6170703d6865726f6b752d6261646765)](https://lab5arep.herokuapp.com/campus.html)

[![CircleCI](https://circleci.com/gh/ARSW-Project-2020-think/modeler.svg?style=svg)](https://circleci.com/gh/mateu20/laboratorio5Arep)

## Workshop
El siguiente documento muestra la guia de trabajo desarrolada [Workshop](https://github.com/mateu20/laboratorio5Arep/blob/master/lab5Arep.docx)

## Design Document
El siguiente documento muestra el articulo del laboratorio [Design](https://github.com/mateu20/laboratorio5Arep/blob/master/Laboratorio5Arep.pdf)

## Authors

* **Mateo González**  - [mateu20](https://github.com/mateu20)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details

