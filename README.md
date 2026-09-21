# TP Nuxt & Spring Boot

Projet d'entraînement ayant pour objectif de mettre en pratique **Nuxt.js**, **Spring Boot** et **Docker**.

> ⚠️ **Projet non destiné à être utilisé en production.**
>
> Ce projet est avant tout un exercice. Il présente notamment :
>
> * aucune sécurité applicative réelle ;
> * pas de gestion avancée des erreurs, notamment en cas de problème d'accès à la base de données ;
> * les données ne sont pas persistantes dans une véritable base de données ;
> * utilisation de **H2 / mémoire RAM** ;
> * une seule liste de données est actuellement disponible.

---

## Technologies utilisées

* **Frontend** : Node.js / Nuxt.js
* **Backend** : Java / Spring Boot
* **Base de données** : H2
* **Conteneurisation** : Docker / Docker Compose
* **Environnement de développement** : Dev Container

---

# 🚀 Lancer le projet

Trois méthodes sont disponibles.

## 1. Docker Compose — recommandé pour un démarrage rapide

### Prérequis

* Docker

### Installation

Cloner le repository :

```bash
git clone https://github.com/Delbyss/tp_nuxt_spring-boot.git
```

Se placer à la racine du projet :

```bash
cd tp_nuxt_spring-boot
```

Le dossier racine doit contenir :

```text
tp_nuxt_spring-boot/
├── docker-compose.yml
├── backend/
└── frontend/
...
```

Lancer les services :

```bash
docker compose up --build -d
```

L'option `--build` reconstruit les images si nécessaire et `-d` lance les conteneurs en arrière-plan.

### Commandes utiles

Voir les conteneurs :

```bash
docker compose ps
```

Voir les logs :

```bash
docker compose logs
```

Voir les logs en temps réel :

```bash
docker compose logs -f
```

Arrêter les services :

```bash
docker compose down
```

Arrêter et reconstruire les services :

```bash
docker compose down
docker compose up --build -d
```

---

# 2. Dev Container — VS Code

Le projet contient une configuration `.devcontainer` permettant de lancer l'environnement de développement directement dans un conteneur.

### Prérequis

* Docker
* VS Code
* Extension **Dev Containers**

### Démarrage

1. Cloner le repository.
2. Ouvrir le projet avec VS Code.
3. VS Code détecte automatiquement le dossier `.devcontainer`.
4. Lorsque VS Code propose de rouvrir le projet dans le conteneur, choisir **Reopen in Container**.

Si aucune proposition n'apparaît :

1. Ouvrir la palette de commandes avec `Ctrl + Shift + P`.
2. Rechercher :

```text
Dev Containers: Reopen in Container
```

3. Valider.

Une fois dans le conteneur, les outils nécessaires à l'environnement de développement sont disponibles.

### Rebuild du Dev Container

Si la configuration du `.devcontainer` est modifiée :

```text
Ctrl + Shift + P
→ Dev Containers: Rebuild Container
```

---

# 3. Installation manuelle

Cette méthode permet de lancer séparément le frontend et le backend sans Docker.

## Prérequis

* **Node.js**
* **npm**
* **Java**
* **Maven** ou le wrapper Maven fourni avec le projet

Les versions nécessaires sont indiquées dans les fichiers de configuration du frontend et du backend.

---

## Frontend

Se placer dans le dossier frontend et sous-dossier front-tp:

```bash
cd frontend/front-tp/
```

Installer les dépendances :

```bash
npm install
```

### Mode développement

```bash
npm run dev
```

### Mode production

Construire le projet :

```bash
npm run build
```

Puis lancer la version de production :

```bash
npm run start
```

---

## Backend

Se placer dans le dossier backend et sous-dossier tp :

```bash
cd backend/tp/
```

### Mode développement

Avec Maven :

```bash
# Maven Wrapper du projet (télécharge la version de Maven prévue par le projet)
./mvnw spring-boot:run

# ou Maven installé sur la machine
mvn spring-boot:run
```

Sous Windows :

```powershell
mvnw.cmd spring-boot:run
```

### Mode production

Construire le projet :

```bash
./mvnw clean package
```

Puis lancer le fichier JAR généré :

```bash
java -jar target/*.jar
```

Sous Windows, utiliser :

```powershell
mvnw.cmd clean package
```

puis :

```powershell
java -jar target/*.jar
```

---

# 📁 Structure du projet

```text
tp_nuxt_spring-boot/
│
├── backend/
│   └── tp/                      # Spring Boot
│       ├── src/
│       ├── pom.xml
│       ├── Dockerfile
│       ├── .dockerignore
│       ├── .gitignore
│       └── .gitattributes
│
├── frontend/
│   └── front-tp/                # Nuxt.js
│       ├── nuxt.config.ts
│       ├── package.json
│       ├── Dockerfile
│       ├── .dockerignore
│       └── .gitignore
│
├── .devcontainer/               # Configuration Dev Container
│   ├── devcontainer.json
│   └── devcontainer-lock.json
│
├── docker-compose.yml
└── README.md
```

---

## ⚠️ À propos des données

La base de données utilisée actuellement est **H2 en mémoire**.

Les données sont donc perdues lorsque l'application ou le conteneur est arrêté/recréé.

Ce comportement est volontaire dans le cadre de ce projet d'entraînement. 
