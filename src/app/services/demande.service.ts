import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Status {
  id?: number;
  nom: string;
}

export interface Conge {
  id?: number;
  nbJour: number;
  dateDebut: any;
  dateFin: any;
  type: any;
  motif?: string;
}

export interface Autorisation {
  id?: number;
  motif: string;
  dateSortie: string;
  duree: string;
  description?: string;
}

export interface Demande {
  id?: number;
  conge?: Conge;
  autorisation?: Autorisation;
  status: Status;
  nbValidateur?: number;
  
  // Propriétés calculées pour l'affichage
  type?: string;
  dateDebut?: string;
  dateFin?: string;
  statut?: string;
  motif?: string;
  nombreJours?: number;
  dateCreation?: string;
  typeConge?: string;
  
  // Champs spécifiques aux autorisations
  dateSortie?: string;
  heureSortie?: string;
  duree?: string;
  description?: string;
}

@Injectable({
  providedIn: 'root'
})
export class DemandeService {
  private baseUrl = 'http://localhost:8081/demandes';

  constructor(private http: HttpClient) {}

  getDemandesByUser(matricule: number): Observable<Demande[]> {
    return this.http.get<Demande[]>(`${this.baseUrl}/user/${matricule}`);
  }

  getAllDemandes(): Observable<Demande[]> {
    return this.http.get<Demande[]>(this.baseUrl);
  }

  getDemandeById(id: number): Observable<Demande> {
    return this.http.get<Demande>(`${this.baseUrl}/${id}`);
  }
}
