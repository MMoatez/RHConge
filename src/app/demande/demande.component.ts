import { Component, OnInit } from '@angular/core';
import { DemandeService, Demande } from '../services/demande.service';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-demande',
  templateUrl: './demande.component.html',
  styleUrls: ['./demande.component.css']
})
export class DemandeComponent implements OnInit {
  demandes: Demande[] = [];
  isLoading: boolean = false;
  errorMessage: string = '';
  selectedDemande: Demande | null = null;
  showDetails: boolean = false;
  

  constructor(
    private demandeService: DemandeService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadDemandes();
  }

  loadDemandes(): void {
    // Récupérer le matricule depuis le token
    const matricule = this.authService.getUserMatricule();
    
    if (!matricule) {
      Swal.fire('Erreur', 'Matricule utilisateur non trouvé. Veuillez vous reconnecter.', 'error');
return;

    }

    this.isLoading = true;
    this.errorMessage = '';

    this.demandeService.getDemandesByUser(matricule).subscribe({
      next: (demandesRaw) => {
        // Transformer les données pour l'affichage
        this.demandes = this.transformDemandes(demandesRaw);
        this.isLoading = false;
        console.log('Demandes chargées:', demandesRaw);
        console.log('Demandes transformées:', this.demandes);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des demandes:', error);
      this.isLoading = false;
      Swal.fire(
        'Erreur',
        error?.error?.message || 'Erreur lors du chargement des demandes. Veuillez réessayer.',
        'error'
      );

      }
    });
  }

  private transformDemandes(demandesRaw: any[]): Demande[] {
    return demandesRaw.map(demande => {
      const transformed: Demande = {
        id: demande.id,
        status: demande.status,
        nbValidateur: demande.nbValidateur,
        statut: demande.status?.nom || 'Inconnu'
      };

      if (demande.conge) {
        // C'est une demande de congé
        transformed.conge = demande.conge;
        transformed.type = demande.conge.type?.name || 'Congé';
        transformed.typeConge = demande.conge.type?.name || demande.conge.type;
        transformed.dateDebut = this.extractDate(demande.conge.dateDebut);
        transformed.dateFin = this.extractDate(demande.conge.dateFin);
        transformed.nombreJours = demande.conge.nbJour;
        transformed.motif = demande.conge.motif;
      } else if (demande.autorisation) {
        // C'est une demande d'autorisation
        transformed.autorisation = demande.autorisation;
        transformed.type = 'Autorisation de sortie';
        transformed.dateDebut = this.extractDate(demande.autorisation.dateSortie);
        transformed.dateFin = this.extractDate(demande.autorisation.dateSortie);
        transformed.motif = demande.autorisation.motif;
        transformed.description = demande.autorisation.description;
        transformed.duree = demande.autorisation.duree;
        transformed.dateSortie = demande.autorisation.dateSortie;
        transformed.heureSortie = this.extractTime(demande.autorisation.dateSortie);
      }

      return transformed;
    });
  }

  private extractDate(dateObj: any): string {
    if (!dateObj) return '';
    
    if (typeof dateObj === 'string') {
      return dateObj;
    }
    
    if (dateObj.date) {
      return dateObj.date.split('T')[0]; // Extraire juste la date sans l'heure
    }
    
    return '';
  }

  private extractTime(dateObj: any): string {
    if (!dateObj) return '';
    
    if (typeof dateObj === 'string') {
      // Si c'est une chaîne avec format datetime, extraire l'heure
      const parts = dateObj.split('T');
      if (parts.length > 1) {
        return parts[1].split('.')[0]; // Enlever les millisecondes si présentes
      }
      return dateObj;
    }
    
    if (dateObj.time) {
      return dateObj.time;
    }
    
    if (dateObj.date) {
      const parts = dateObj.date.split('T');
      if (parts.length > 1) {
        return parts[1].split('.')[0];
      }
    }
    
    return '';
  }

  voirDetails(demande: Demande): void {
    this.selectedDemande = demande;
    this.showDetails = true;
    
    // Scroll vers la card de détails après un court délai
    setTimeout(() => {
      const detailsCard = document.getElementById('details-card');
      if (detailsCard) {
        detailsCard.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    }, 100);
  }

  fermerDetails(): void {
    this.showDetails = false;
    this.selectedDemande = null;
  }

  isCongeType(type: string | undefined): boolean {
    if (!type) return false;
    const lowerType = type.toLowerCase();
    return lowerType.includes('congé') || 
           lowerType.includes('repos') ||
           lowerType.includes('mariage') ||
           lowerType.includes('circoncision') ||
           lowerType.includes('deces');
  }

  isAutorisationType(type: string | undefined): boolean {
    if (!type) return false;
    return type.toLowerCase().includes('autorisation');
  }

  private formatDate(dateString: string | undefined): string {
    if (!dateString) return 'Non spécifié';
    
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('fr-FR', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    } catch (error) {
      return dateString;
    }
  }
/*
  getStatutClass(statut: string | undefined): string {
    if (!statut) return 'badge bg-secondary';
    
    switch (statut.toLowerCase()) {
      case 'acceptée':
      case 'accepté':
      case 'approuvé':
        return 'badge bg-success';
      case 'en cours':
      case 'en attente':
      case 'pending':
        return 'badge bg-warning';
      case 'rejetée':
      case 'rejeté':
      case 'refusé':
        return 'badge bg-danger';
      default:
        return 'badge bg-secondary';
    }
  }*/

  refreshDemandes(): void {
    this.loadDemandes();
  }

  trackByDemande(index: number, demande: Demande): any {
    return demande.id || index;
  }

  

  supprimerDemande(demande: Demande): void {
  if (!demande || demande.id == null) return;

  Swal.fire({
    title: 'Êtes-vous sûr ?',
    text: "Cette action est irréversible !",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Oui, supprimer',
    cancelButtonText: 'Annuler'
  }).then((result) => {
    if (result.isConfirmed) {
      this.demandeService.deleteDemande(demande.id!).subscribe({
        next: () => {
          this.refreshDemandes();
          Swal.fire('Supprimé !', 'La demande a été supprimée.', 'success');
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = "Erreur lors de la suppression de la demande.";
          Swal.fire('Erreur !', 'Impossible de supprimer la demande.', 'error');
        }
      });
    }
  });
}


getStatutClass(statut: string | undefined): string {
  if (!statut) return 'bg-secondary text-white';

  switch (statut.toUpperCase()) {
    case 'EN_ATTENTE':
    case 'EN COURS':
    case 'PENDING':
      return 'bg-warning text-dark'; // Jaune
    case 'APPROUVE':
    case 'ACCEPTE':
    case 'ACCEPTÉ':
    case 'APPROUVÉ':
      return 'bg-success text-white'; // Vert
    case 'REJETE':
    case 'REJETÉ':
    case 'REFUSE':
      return 'bg-danger text-white'; // Rouge
    default:
      return 'bg-secondary text-white'; // Gris
  }
}




}
