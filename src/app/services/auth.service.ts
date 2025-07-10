import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/auth'; // Adjust this URL to match your backend
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    // Check if user is already logged in on service initialization
    const token = this.getToken();
    if (token) {
      const userInfo = this.decodeToken(token);
      this.currentUserSubject.next(userInfo);
    }
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password });
  }

  // Store token in localStorage and decode user info
  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
    const userInfo = this.decodeToken(token);
    this.currentUserSubject.next(userInfo);
  }

  // Get token from localStorage
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Decode JWT token to get user info
  private decodeToken(token: string): any {
    try {
      const payload = token.split('.')[1];
      const decodedPayload = atob(payload);
      return JSON.parse(decodedPayload);
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }

  // Get current user info
  getCurrentUser(): any {
    return this.currentUserSubject.value;
  }

  // Get user's display name
  getUserDisplayName(): string {
    const user = this.getCurrentUser();
    
    // Debug: Log the user object to see what's available
    console.log('User token payload:', user);
    
    // Now we have nom and prenom directly from the token
    const firstName = user?.prenom || '';
    const lastName = user?.nom || '';
    
    console.log('firstName:', firstName, 'lastName:', lastName);
    
    // If we have both first and last name, combine them
    if (firstName && lastName) {
      return `${firstName} ${lastName}`;
    }
    
    // If we only have one name, use it
    if (firstName) return firstName;
    if (lastName) return lastName;
    
    // Fallback to email or default
    return user?.email || user?.sub || 'Utilisateur';
  }

  // Get user matricule
  getUserMatricule(): number | null {
    const user = this.getCurrentUser();
    return user?.matricule || null;
  }

  // Get user role
  getUserRole(): string | null {
    const user = this.getCurrentUser();
    return user?.role || null;
  }

  // Get user conges info
  getCongesInfo(): any {
    const user = this.getCurrentUser();
    return {
      congesRestants: user?.congesRestants || 0,
      congesPris: user?.congesPris || 0,
      congesAnnuelsRestants: user?.congesAnnuelsRestants || 0
    };
  }

  // Get user manager info
  getManagerInfo(): any {
    const user = this.getCurrentUser();
    return user?.manager || null;
  }

  // Remove token from localStorage
  logout(): void {
    localStorage.removeItem('authToken');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  // Check if user is authenticated
  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }
}
