import { Component } from '@angular/core';

@Component({
  selector: 'app-validation',
  templateUrl: './validation.component.html',
  styleUrls: ['./validation.component.css']
})
export class ValidationComponent {
  demandes = [
    { id: 1, nom: 'Ali', type: 'Congé', duree: '5J', statut: 'En cours' },
    { id: 2, nom: 'Sami', type: 'Autorisation', duree: '2J', statut: 'Acceptée' },
    { id: 3, nom: 'Leila', type: 'Congé', duree: '3J', statut: 'En cours' }
  ];

  selectedDemande: any = null;
  description: string = '';

  openDetails(demande: any) {
    this.selectedDemande = { ...demande };
    this.description = '';
  }

  validerDemande() {
    this.updateStatut('Acceptée');
  }

  rejeterDemande() {
    this.updateStatut('Rejetée');
  }

  updateStatut(newStatut: string) {
    const index = this.demandes.findIndex(d => d.id === this.selectedDemande.id);
    if (index !== -1) {
      this.demandes[index].statut = newStatut;
      // tu peux enregistrer `description` aussi ici
    }
    this.selectedDemande = null;
  }
}
