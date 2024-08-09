## <h1>Hadoop : Apache Hive</h1>
***
<table><tr>
  <td><img src="https://github.com/user-attachments/assets/d4bcd836-0a1f-48e7-b971-3c7afdc02335" alt="drawing" height="240px"/></td>
</tr></table>
## Informations Générales
***
Apache HIVE fait partie de l'écosytème BigData HADOOP.<br/>
Le sytème Apache HIVE permet de pouvoir effectuer directement des requete de type SQL.<br/>
Il offre une altérnative au Logiciel Sqoop.<br/>
Vous trouverez ici une solution compléte vous permettant de déployer un système HADOOP via docker.<br/>
Ainsi qu'un applicatif SpringBoot permettant de faire des requête Apache Hive

## Déploiement Docker
***
Déploiement du cluster Hadoop-Spark-Hive:<br/>
```
docker-compose up
```
`* Puce`
Pour accèder aux interfaces des différents composants:<br>
*Namenode: http://<dockerhadoop_IP_address>:9870/dfshealth.html#tab-overview
*History server: http://<dockerhadoop_IP_address>:8188/applicationhistory
*Datanode: http://<dockerhadoop_IP_address>:9864/
*Nodemanager: http://<dockerhadoop_IP_address>:8042/node
*Resource manager: http://<dockerhadoop_IP_address>:8088/
*Spark master: http://<dockerhadoop_IP_address>:8080/
*Spark worker: http://<dockerhadoop_IP_address>:8081/
*Hive: http://<dockerhadoop_IP_address>:10000








Run Image Docker:<br/>
```
docker run -it ghcr.io/kasipavankumar/sqoop-docker:latest
```
Cette image permet de deployer :
* Une Infrastructure Apache Hadoop avec son système de fichier
* Une base de données Mysql ainsi que les données associées

## Import
***
Exemple d'exportation de la base de données Mysql vers HDFS de Hadoop
```
Exportation du fichier vers hdfs
sqoop import 
    --connect jdbc:mysql://localhost/employees 
    --table employees 
    --username bda 
    --password 123456
```
## Import avec création de Base de données
***
Exemple d'exportation de la base de données Mysql vers HDFS de Hadoop
```
#Creation des données dans la base
CREATE DATABASE IF NOT EXISTS test;

GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `test`.* TO 'bda'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE client (
 id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nom VARCHAR(100),
  email VARCHAR(100)
);
INSERT INTO `client` (`nom`, `email`) VALUES ('Paul', 'paul@example.com');
INSERT INTO `client` (`nom`, `email`) VALUES ('Sandra', 'sandra@example.com');
```
importation vers hdfs
```
sqoop import --connect jdbc:mysql://localhost/test
	     --table client
	     --username bda
              --password 123456
```
Test de présence du fichier dans HDFS
```
hadoop fs -ls /user/root/client
```
Afficher les données
```
hadoop fs -cat /user/root/client/part-m-00000
1,Paul,paul@example.com
hadoop fs -cat /user/root/client/part-m-00001
2,Sandra,sandra@example.com
```
## Exemple d'Export
***
**Exportation d un fichier directement dans une base de données**
Copier le fichier sur le pod
```
docker cp people.txt agitated_goldstine:people.txt
```
Créer un dossier dans hdfs
```
hdfs dfs -mkdir -p /fichier
```
Pousser le fichier dans le cluster Hdfs
```
hdfs dfs -put people.txt /fichier/people.txt
```
Dans mysql créer la base de données ainsi que la table associé
```
CREATE DATABASE IF NOT EXISTS people;
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `people`.* TO 'bda'@'localhost';
CREATE TABLE people (
  prenom VARCHAR(100),
  age VARCHAR(100)
);
```
Faire un export du foicher vers la base de données
```
sqoop export --connect jdbc:mysql://localhost/people
	     --username bda
             --password 123456
             --table people
             --export-dir /fichier/people.txt
```
