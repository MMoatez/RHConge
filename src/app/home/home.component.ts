import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { CongeService, Conge, Datee, TypeC } from '../services/conge.service';
import { AutorisationService, Autorisation } from '../services/autorisation.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  showCongeForm: boolean = false;
  showAutorisationForm: boolean = false;
  congesInfo: any = {};
  userDisplayName: string = '';
  
  // Form data
  congeForm = {
    dateDebut: '',
    dateDebutMatin: true,  //new
    dateDebutApresMidi: true,  //new
    dateFin: '',
    dateFinMatin: true, //new
    dateFinApresMidi: true, //new
    typeConge: 'repos',  // Mise à jour de la valeur par défaut
    motif: '',
    nombreJours: 0
  };

  // Autorisation form data
  autorisationForm = {
    dateSortie: '',
    heureSortie: '',
    duree: '',
    motif: '',
    description: ''
  };

  constructor(
    private authService: AuthService,
    private congeService: CongeService,
    private autorisationService: AutorisationService
  ) {}

  minDate: string = '';
  
  ngOnInit(): void {
  const today = new Date();
  this.minDate = today.toISOString().split('T')[0]; // Format: YYYY-MM-DD

  // J Congés
    this.congesInfo = this.authService.getCongesInfo();
    this.userDisplayName = this.authService.getUserDisplayName();

    // Calculer le total des congés restants (année précédente + actuelle)
    //this.congesInfo.totalCongesRestants = (this.congesInfo.congesAnnuelsRestants || 0) + (this.congesInfo.congesRestants || 0);
    this.congesInfo.totalCongesRestants = this.congesInfo.congesRestants || 0;


      console.log('Infos congés:', this.congesInfo); // <== Vérifie que hAutorisation est bien un nombre ici
      console.log('hAutorisation:', this.congesInfo.hAutorisation);


  }


    


  showCongeFormulaire(): void {
    this.showCongeForm = true;
    this.showAutorisationForm = false;
  }

  showAutorisationFormulaire(): void {
    this.showAutorisationForm = true;
    this.showCongeForm = false;
  }

  hideAllForms(): void {
    this.showCongeForm = false;
    this.showAutorisationForm = false;
  }

  isDateRangeValid: boolean = true;
  isDateFinValid: boolean = true;

  onDateChange(): void {
  const today = new Date();
  const debut = new Date(this.congeForm.dateDebut);
  const fin = new Date(this.congeForm.dateFin);

  this.isDateRangeValid = true;
  this.isDateFinValid = true;
  this.congeForm.nombreJours = 0;

  if (this.congeForm.dateDebut && this.congeForm.dateFin) {
    // Valider si dateFin <= dateDebut
    if (fin < debut) {
      this.isDateRangeValid = false;
    }

    // Valider si dateFin < date système
    if (fin <= today) {
      this.isDateFinValid = false;
    }

    // Si tout est bon, calculer nombre de jours
    if (this.isDateRangeValid && this.isDateFinValid) {
      const diffTime = fin.getTime() - debut.getTime();
const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;

      this.congeForm.nombreJours = diffDays;
    }
  }
}



/*
  onDateChange(): void {
    if (this.congeForm.dateDebut && this.congeForm.dateFin) {
      const debut = new Date(this.congeForm.dateDebut);
      const fin = new Date(this.congeForm.dateFin);
      const diffTime = Math.abs(fin.getTime() - debut.getTime());
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
      this.congeForm.nombreJours = diffDays;
    }
  }*/

    

  onSubmitConge(): void {
    if (!this.isDateRangeValid) {
      // Vérification du total des congés disponibles avant soumission
    if ((this.congesInfo.totalCongesRestants || 0) <= 0) {
      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        text: 'Vous n\'avez plus de congés disponibles pour faire une demande.',
      });
      return;
    }
    // alert('La date de fin ne peut pas être antérieure à la date de début.');


    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "La date de fin ne peut pas être antérieure à la date de début.",
    });
    return;
  }
  if (!this.isDateFinValid) {
    // alert('La date de fin ne peut pas être antérieure à la date d\'aujourd\'hui.');

    Swal.fire({
  icon: "error",
  title: "Oops...",
  text: "La date de fin ne peut pas être antérieure à la date d\'aujourd\'hui.",
});
    return;
  }
    console.log('Demande de congé:', this.congeForm);
    
    // Récupérer le matricule depuis le token
    const matricule = this.authService.getUserMatricule();
    
    if (!matricule) {
      // alert('Erreur: Matricule utilisateur non trouvé. Veuillez vous reconnecter.');

      Swal.fire({
  icon: "error",
  title: "Oops...",
  text: "Erreur: Matricule utilisateur non trouvé. Veuillez vous reconnecter.",
});
      return;
    }
    
    // Créer les objets Date conformes à l'API
    const dateDebut: Datee = {
      date: `${this.congeForm.dateDebut}T09:00:00`, // Format ISO avec heure
      matin: this.congeForm.dateDebutMatin,        // boolean
      apresMidi: this.congeForm.dateDebutApresMidi // boolean
    };

    const dateFin: Datee = {
      date: `${this.congeForm.dateFin}T17:00:00`, // Format ISO avec heure
      matin: this.congeForm.dateFinMatin,         // boolean → true ou false
      apresMidi: this.congeForm.dateFinApresMidi  // boolean → true ou false
    };

    if (!this.congeForm.dateDebutMatin && !this.congeForm.dateDebutApresMidi) {
  // alert("Veuillez sélectionner au moins Matin ou Après-midi pour la date de début.");

  Swal.fire({
  icon: "warning",
  title: "Oops...",
  text: "Veuillez sélectionner au moins Matin ou Après-midi pour la date de début.",
});
  return;
}

if (!this.congeForm.dateFinMatin && !this.congeForm.dateFinApresMidi) {
  alert("Veuillez sélectionner au moins Matin ou Après-midi pour la date de fin.");
  return;
}


    // Mapper le type de congé vers l'ID
   const typeCongeMap: { [key: string]: number } = {
  'repos': 1,
  'mariage agent': 2,
  'circoncision': 3,
  'mariage enfant': 4,
  'deces parents': 6,
  'deces conjoint enfant': 7,
  'deces autre': 8
};


    const typeConge: TypeC = {
      id: typeCongeMap[this.congeForm.typeConge] || 1,
      name: this.congeForm.typeConge
    };

    // Créer l'objet Conge pour l'API
    const congeRequest: Conge = {
      nbJour: this.congeForm.nombreJours,
      dateDebut: dateDebut,
      dateFin: dateFin,
      type: typeConge,
      motif: this.congeForm.motif || undefined
    };

    // Envoyer la demande au backend avec le matricule
    

    this.congeService.createConge(congeRequest, matricule).subscribe({
    next: (response) => {
      console.log('Congé créé avec succès:', response);
      Swal.fire({
        icon: "success",
        title: "Message",
        text: "Demande de congé soumise avec succès!",
      });
      this.hideAllForms();
      this.resetForm();
    },
    error: (error) => {
      console.error('Erreur lors de la création du congé:', error);

      // Récupération du message précis du backend (si disponible)
      const errorMsg = error?.error?.message || 'Vous ne pouvez pas faire de demande de congé.<br> Congés restants = 0.<br> Ou <br> Congés demandes > Congé Restant';

      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        html: errorMsg,
      });
    }
  });
  }

  private resetForm(): void {
    this.congeForm = {
      dateDebut: '',
      dateDebutMatin: true,  //new
    dateDebutApresMidi: true,  //new
      dateFin: '',
      dateFinMatin: true, //new
    dateFinApresMidi: true, //new
      typeConge: 'repos',  // Mise à jour de la valeur par défaut
      motif: '',
      nombreJours: 0
    };
  }

  onSubmitAutorisation(): void {
  console.log('Demande d\'autorisation:', this.autorisationForm);

  const matricule = this.authService.getUserMatricule();

  if (!matricule) {
    Swal.fire({
      icon: 'error',
      title: 'Erreur',
      text: 'Matricule utilisateur non trouvé. Veuillez vous reconnecter.'
    });
    return;
  }

  // ✅ Vérifier les heures disponibles avant d'envoyer la requête
  // ✅ Ne pas bloquer la demande si motif est "Service"
  if (this.autorisationForm.motif !== 'Service' && this.congesInfo.hAutorisation <= 0) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Vous n'avez plus d'heures d'autorisation disponibles."
    });
    return;
  }


  if (this.autorisationForm.motif !== 'Service') {
    const dureeDemandee = parseInt(this.autorisationForm.duree, 10);
    if (this.congesInfo.hAutorisation < dureeDemandee) {
      Swal.fire({
        icon: "error",
        title: "Heures insuffisantes",
        text: "Vous dépassez votre quota d'heures restantes."
      });
      return;
    }
  }

  // ✅ Construire l’objet autorisation
  const autorisationRequest: Autorisation = {
    motif: this.autorisationForm.motif,
    dateSortie: `${this.autorisationForm.dateSortie}T${this.autorisationForm.heureSortie}:00`,
    duree: this.autorisationForm.duree,
    description: this.autorisationForm.description
  };

  // ✅ Envoyer la requête
  this.autorisationService.create(autorisationRequest, matricule).subscribe({
    next: (response) => {
      console.log('Autorisation créée avec succès:', response);
      Swal.fire({
        icon: "success",
        title: "Message",
        text: "Demande d'autorisation soumise avec succès!",
      });
      this.hideAllForms();
      this.resetAutorisationForm();
    },
    error: (error) => {
      console.error('Erreur lors de la création de l\'autorisation:', error);
      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        text: 'Une erreur est survenue lors de la demande.'
      });
    }
  });
}










  private resetAutorisationForm(): void {
    this.autorisationForm = {
      dateSortie: '',
      heureSortie: '',
      duree: '0',
      motif: '',
      description: ''
    };
  }

  isDebutChecked(): boolean {
  return this.congeForm.dateDebutMatin || this.congeForm.dateDebutApresMidi;
  }

  isFinChecked(): boolean {
  return this.congeForm.dateFinMatin || this.congeForm.dateFinApresMidi;
  }



  convertMinutesToHM(minutes: number | null | undefined): string {
  if (minutes == null || isNaN(minutes)) {
    return '0h 0min';  // valeur par défaut
  }
  const heures = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return `${heures}h ${mins}min`;
}





}
