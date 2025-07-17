import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {
  private baseUrl = 'http://localhost:8081/api/validations'; // ⚠️ adapte cette URL si besoin

  constructor(private http: HttpClient) {}

  getValidationsByMatricule(matricule: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/validateur/${matricule}`);
  }

  updateValidation(validation: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${validation.id}`, validation);
  }
}
