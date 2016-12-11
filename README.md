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
