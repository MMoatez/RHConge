import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

  export interface Autorisation {
    id?: number;
    motif: string;
    dateSortie: string; // format ISO '2025-07-09T10:00:00'
    duree: string;
    description: string;
  }

@Injectable({
  providedIn: 'root'
})
export class AutorisationService {

  private baseUrl = 'http://localhost:8081/autorisation';

  constructor(private http: HttpClient) {}

  // Interface directement dans le service


  getAll(): Observable<Autorisation[]> {
    return this.http.get<Autorisation[]>(this.baseUrl);
  }

  getById(id: number): Observable<Autorisation> {
    return this.http.get<Autorisation>(`${this.baseUrl}/${id}`);
  }

  create(autorisation: Autorisation, matricule: number): Observable<Autorisation> {
    return this.http.post<Autorisation>(`${this.baseUrl}/${matricule}`, autorisation);
  }

  update(id: number, autorisation: Autorisation): Observable<Autorisation> {
    return this.http.put<Autorisation>(`${this.baseUrl}/${id}`, autorisation);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
