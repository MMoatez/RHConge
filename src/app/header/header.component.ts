import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { ValidationService } from '../services/validation.service'; // ✅ Ajouté
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  currentUser: any = null;
  userDisplayName: string = '';
  validationsEnAttente: any[] = []; // ✅ Notifications
  private userSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private validationService: ValidationService, // ✅ Injecté
    private router: Router
  ) {}

  // Quand on clique sur une notif
goToValidation(validationId: number): void {
  this.router.navigate(['/validations'], { queryParams: { validationId } });
}

  

  ngOnInit(): void {
    // Subscribe to current user changes
    this.userSubscription = this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.userDisplayName = this.authService.getUserDisplayName();

      // Charger les validations en attente si l'utilisateur est connecté
      if (user && user.matricule) {
        this.loadValidations(user.matricule);
      }
    });
  }

  loadValidations(matricule: number): void {
    this.validationService.getValidationsByMatricule(matricule).subscribe(data => {
      // ✅ Filtrer seulement les validations en attente (approuve == null)
      this.validationsEnAttente = data.filter(v => v.approuve === null);
    });
  }

  onLogout(): void {
    this.authService.logout();
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
