import { Component, OnInit } from '@angular/core';
import { ValidationService } from '../services/validation.service';
import { AuthService } from '../services/auth.service';  // Import AuthService
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-validation',
  templateUrl: './validation.component.html',
  styleUrls: ['./validation.component.css']
})
export class ValidationComponent implements OnInit {
  validations: any[] = [];
  selectedDemande: any = null;
  description: string = '';
  currentUserMatricule: number | null = null;

  // Injecter les deux services dans le constructeur
  constructor(
    private validationService: ValidationService,
    private authService: AuthService,
  private route: ActivatedRoute 
  ) {}

  ngOnInit(): void {
  this.currentUserMatricule = this.authService.getUserMatricule();

  if (this.currentUserMatricule) {
    this.loadValidations(() => {
      // 👇 après le chargement, vérifier s'il y a un ID dans l'URL
      this.route.queryParams.subscribe(params => {
        const validationId = +params['validationId'];
        if (validationId) {
          const v = this.validations.find(val => val.id === validationId);
          if (v) {
            this.openDetails(v); // ✅ ouvrir la modale
          }
        }
      });
    });
  } else {
    console.warn('Aucun utilisateur connecté détecté.');
    this.validations = [];
  }
}


  loadValidations(afterLoad?: () => void): void {
  if (!this.currentUserMatricule) return;

  this.validationService.getValidationsByMatricule(this.currentUserMatricule).subscribe({
    next: (data) => {
      this.validations = data.map((v: any) => {
        const demandeur = v.idDemande?.matriculeDemandeur;
        const validateur = v.matriculeValidateur;
        const isConge = v.idDemande?.conge !== null;
        const conge = v.idDemande?.conge;
        const autorisation = v.idDemande?.autorisation;

        return {
          id: v.id,
          original: v,
          nomDemandeur: `${demandeur?.nom || ''} ${demandeur?.prenom || ''}`,
          validateurNom: `${validateur?.nom || ''} ${validateur?.prenom || ''}`,
          type: isConge ? 'Congé' : 'Autorisation',
          duree: isConge ? `${conge?.nbJour || 0}J` : autorisation?.duree || '',
          statut: v.approuve === null ? 'EN_ATTENTE' : v.approuve ? 'APPROUVE' : 'REJETE'
        };
      });

      if (afterLoad) afterLoad(); // ✅ exécute le callback après chargement
    },
    error: (err) => {
      console.error('Erreur lors du chargement des validations :', err);
    }
  });
}


  openDetails(demande: any): void {
    this.selectedDemande = { ...demande };
    this.description = '';
  }

  validerDemande(): void {
    this.updateStatut(true);
  }

  rejeterDemande(): void {
  console.log('Rejeter demandé');
  this.updateStatut(false);
}

updateStatut(accept: boolean): void {
  console.log('updateStatut appelé avec accept =', accept);
  const validationToUpdate = {
  id: this.selectedDemande.original.id,
  idDemande: this.selectedDemande.original.idDemande,
  matriculeValidateur: this.selectedDemande.original.matriculeValidateur,
  approuve: accept,
  description: this.description
};

  console.log('Validation à mettre à jour:', validationToUpdate);

  this.validationService.updateValidation(validationToUpdate).subscribe({
    next: () => {
      console.log('Mise à jour réussie');
      this.selectedDemande = null;
      this.loadValidations();
    },
    error: (err) => console.error('Erreur lors de la mise à jour de la validation :', err)
  });
}

getStatutClass(statut: string): string {
  switch (statut) {
    case 'APPROUVE': return 'bg-success text-white';
    case 'REJETE': return 'bg-danger text-white';
    case 'EN_ATTENTE': return 'bg-warning text-dark';
    default: return 'bg-secondary text-white';
  }
}


}
