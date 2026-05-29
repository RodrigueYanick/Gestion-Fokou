# Gestion-Fokou
Application de gestion multi-magasins pour l'entreprise FOKOU permettant la gestions des produits, stocks, ventes, approvisionnements, client et fournisseur. Projet academique realise avec l'aide du frame work scrum avec Spring Boot et MySQL

# 🏗️ Système de Gestion des Magasins FOKOU

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.14-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/fr/)
[![License](https://img.shields.io/badge/licence-MIT-lightgrey)](LICENSE)

**Backend REST API** pour l’automatisation et la centralisation des activités commerciales des magasins FOKOU, spécialiste de la vente de matériaux de construction.

---

## 📖 Contexte

L’entreprise FOKOU gère plusieurs points de vente avec des outils manuels (registres, tableurs), entraînant des erreurs, des ruptures de stock et un manque de visibilité. Ce projet fournit une **application backend robuste** couvrant l’ensemble du processus commercial : gestion des ventes, des stocks, des produits, des clients, des fournisseurs et des statistiques, avec une **sécurité JWT** et une **traçabilité complète**.

Le développement a été mené avec la méthodologie **Scrum** (4 sprints de 4 jours) par une équipe de 5 étudiants en Licence 3 Informatique.

---

## ✨ Fonctionnalités principales

- 🔐 **Authentification & autorisations** : connexion par JWT, 4 rôles (ADMIN, GESTIONNAIRE, VENDEUR, STOCK)
- 👥 **Gestion des utilisateurs** : CRUD, soft delete, restauration, listes actifs/inactifs
- 📦 **Catalogue produits** : catégories, produits avec références, prix, seuils d’alerte, recherche
- 🏬 **Gestion des stocks** : suivi en temps réel par magasin, alertes de seuil, mouvements historisés
- 🛒 **Ventes** : enregistrement d’une vente, vérification du stock, calcul automatique, facturation, historique
- 🚚 **Approvisionnements** : réception fournisseur, mise à jour du stock, historique
- 👤 **Clients & fournisseurs** : fiches détaillées, historique, soft delete
- 📊 **Statistiques** : chiffre d’affaires par période, produits les plus vendus
- 📝 **Traçabilité** : journal des actions, mouvements de stock horodatés
- 🗑️ **Soft delete** : suppression logique avec restauration sur toutes les entités principales

---

## 🛠️ Technologies

| Technologie | Usage |
|------------|-------|
| Java 17 | Langage principal |
| Spring Boot 3.5.14 | Framework backend |
| Spring Data JPA / Hibernate | ORM, gestion des entités |
| Spring Security | Authentification et autorisations |
| JSON Web Token (JJWT) | Génération et validation des tokens |
| MySQL 8.0 | Base de données relationnelle |
| Maven | Build et gestion des dépendances |
| Lombok | Réduction du code boilerplate |
| Swagger (springdoc) | Documentation interactive de l’API |
| Postman | Tests manuels des endpoints |
| JUnit 5 | Tests unitaires |

