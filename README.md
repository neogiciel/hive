## <h1>Hadoop : Apache Hive</h1>
***
<table><tr>
  <td><img src="https://github.com/user-attachments/assets/d4bcd836-0a1f-48e7-b971-3c7afdc02335" alt="drawing" height="240px"/></td>
</tr></table>
## Informations Générales
***
Apache HIVE fait partie de l'écosytème BigData HADOOP.<br/>
Le sytème Apache HIVE permet de pouvoir effectuer directement des requete de type SQL.<br/>
Il offre une altérnative au Logiciel Sqoop.<br/><br/>
Vous trouverez ici une solution compléte vous permettant de déployer un système HADOOP via docker.<br/>
Ainsi qu'un applicatif SpringBoot permettant de faire des requête Apache Hive

## Déploiement Docker
***
Déploiement du cluster Hadoop-Spark-Hive:<br/>
```
docker-compose up
```
Pour accèder aux interfaces des différents composants:<br>
* Namenode: http://<dockerhadoop_IP_address>:9870/dfshealth.html#tab-overview
* History server: http://<dockerhadoop_IP_address>:8188/applicationhistory
* Datanode: http://<dockerhadoop_IP_address>:9864/
* Nodemanager: http://<dockerhadoop_IP_address>:8042/node
* Resource manager: http://<dockerhadoop_IP_address>:8088/
* Spark master: http://<dockerhadoop_IP_address>:8080/
* Spark worker: http://<dockerhadoop_IP_address>:8081/
* Hive: http://<dockerhadoop_IP_address>:10000

## Transfert du Fichier dans le cluster HDFS
Copie du fichier dans le pod:<br/>
```
docker cp breweries.csv namenode:breweries.csv
```
Accès au shell du pod:<br/>
```
docker exec -it namenode bash
```
Création du dossier dans HDFS:<br/>
```
hdfs dfs -mkdir -p /data/openbeer/breweries
```
Transfert du fichier dans HDFS:<br/>
```
hdfs dfs -put breweries.csv /data/openbeer/breweries/breweries.csv
```
## Quick Start Hive
***
Démmarrer le serveur Hive
```
 docker exec -it hive-server bash

  hiveserver2
```
Utiliser les lignes de commande pour vous connecter au serveur<br>
```
beeline -u jdbc:hive2://localhost:10000 -n root
```
Voir la base de données
```
show databases;
+----------------+
| database_name  |
+----------------+
| default        |
+----------------+
1 row selected (0.335 seconds)
```
Créer une base de données
```
create database openbeer;
  use openbeer;
```
Créer la Table associé qui va permettre de définir le modéle de données
```
CREATE EXTERNAL TABLE IF NOT EXISTS breweries(
    NUM INT,
    NAME CHAR(100),
    CITY CHAR(100),
    STATE CHAR(100),
    ID INT )
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
location '/data/openbeer/breweries';
```
Nous pouvons maintenant faire la Requête suivante

```
select name from breweries limit 10;
+----------------------------------------------------+
|                        name                        |
+----------------------------------------------------+
| name                                               |
| NorthGate Brewing                                  |
| Against the Grain Brewery                          |
| Jack's Abby Craft Lagers                           |
| Mike Hess Brewing Company                          |
| Fort Point Beer Company                            |
| COAST Brewing Company                              |
| Great Divide Brewing Company                       |
| Tapistry Brewing                                   |
| Big Lake Brewing                                   |
+----------------------------------------------------+
10 rows selected (0.113 seconds
```


## Application Spring-Boot
***
Vous trouverez ici le code source d'une application Spring-Boot permettant d'éffectuer des requêtes sur Apache Hive
```
mvn clean
mvn spring-boot:run
```

