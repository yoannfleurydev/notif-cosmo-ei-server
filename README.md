# Notification d'effets indésirables sur produits cosmétiques

## Technologies

Utilisation des technologies suivantes :

* Java 8
* Spring Boot
* Angular

## API

* https://geo.api.gouv.fr

## Routes

### Inscription et connexion

**POST /user/signup**

```json
{
    "userName" : "yoannfleurydev",
    "email" : "yoann.fleury@example.com",
    "firstName" : "Yoann",
    "lastName" : "Fleury",
    "password" : "azerty",
    "role" : "USER"
}
```

**POST /user/login**
```json
{
    "name" : "yoannfleurydev",
    "password" : "azerty"
}
```

### Produits

**GET /products** : retourne tous les produits disponible en base de données.

**GET /products/{id}** : retourne l'élément d'index `id`.

**POST /products** : crée un produit en base de données et le retourne si tout 
se passe bien. Il est possible de passer en même temps des index d'ingrédients 
existant déjà en base de données. Si un index d'ingrédient n'existe pas, l'ajout
du produit sera annulé.

```json
{
	"name" : "Name",
	"ingredients" : [
		1
	]
}
```
**PUT /products/{id}** : met à jour le produit d'index `id` et le retourne si 
tout se passe bien.

```json
{
	"name": "Name"
}
```

**DELETE /products/{id}** : supprime le produit d'index `id`.

### Ingrédients

**GET /ingredients** : retourne tous les ingrédients stockés en base de données.

**GET /ingredients/{id}** : retourne l'ingrédients d'index `id`.

**POST /ingredients** : crée un ingrédient en base de données et le retoune si 
tout se passe bien. 

```json
{
    "name": "Aqua"
}
```

**PUT /ingredients/{id}** : met à jour l'ingrédient d'index `id` et le retourne
si tout se passe bien.

```json
{
    "name": "Aqua"
}
```

**DELETE /ingredients/{id}** : supprime l'ingrédient d'index `id`.

### Recherche

Il est possible de rechercher des termes sur des champs précis des objets. 
Les recherches sont insensibles à la casse. En voici la liste :

> `recherche` étant le terme à rechercher.

* Recherche sur le **nom** d'un ingrédient : `GET /ingredients/search?value=recherche`,
* Recherche sur le **nom** d'un produit : `GET /products/search?value=recherche`
* Recherche sur la **description** d'un effet : `GET /effect/search?value=recherche`

## TODO

- [] Vérifier le code lors d'un dépôt de notification
- [] Améliorer la route de dépôt de notifications pour y ajouter l'utilisateur
qui fait le dépôt.
- [] Faire une route sur le classement des effets les plus reportés
- [] Faire une route sur le classement des effets les plus lourd
