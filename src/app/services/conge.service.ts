import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

  export interface Datee {
    id?: number;
    date: string;         // format ISO ex : '2025-07-09T09:00:00'
    matin: boolean;
    apresMidi: boolean;
  }

  export interface TypeC {
    id: number;
    name: string;
  }

  export interface Conge {
    id?: number;
    nbJour: number;
    dateDebut: Datee;
    dateFin: Datee;
    type: TypeC;
    motif?: string;  // Ajout du motif optionnel
  }
@Injectable({
  providedIn: 'root'
})
export class CongeService {

  private baseUrl = 'http://localhost:8081/conges';

  constructor(private http: HttpClient) {}



  getAllConges(): Observable<Conge[]> {
    return this.http.get<Conge[]>(this.baseUrl);
  }

  getCongeById(id: number): Observable<Conge> {
    return this.http.get<Conge>(`${this.baseUrl}/${id}`);
  }

  createConge(conge: Conge, matricule: number): Observable<Conge> {
    return this.http.post<Conge>(`${this.baseUrl}/${matricule}`, conge);
  }

  updateConge(id: number, conge: Conge): Observable<Conge> {
    return this.http.put<Conge>(`${this.baseUrl}/${id}`, conge);
  }

  deleteConge(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
