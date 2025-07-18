# RHConge

Application de gestion des demandes de congés et autorisations.

## 🛠 Technologies utilisées

- Backend : Spring Boot
- Frontend : Angular
- Base de données : MySQL

## 📌 Fonctionnalités

- Création de demandes de congé et d’autorisation
- Génération automatique des entités de validation pour les managers
- Système de validation multiple (boss1, boss2)
- **Envoi automatique d’email** aux managers concernés (Boss1, Boss2) à chaque nouvelle demande
- **Notification par email** au demandeur une fois la demande traitée (acceptée ou refusée)
- **Notification affichée dans l’application** pour informer les validateurs (boss1, boss2) des demandes en attente de traitement
- Mise à jour automatique du statut des demandes
- Gestion des utilisateurs et rôles (employé, manager, admin)


