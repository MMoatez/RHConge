import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DemandeComponent } from './demande/demande.component';
import { ValidationComponent } from './validation/validation.component';


const routes: Routes = [
   { path: '', redirectTo: '/login', pathMatch: 'full' },
  //{ path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'demande', component: DemandeComponent },
  { path: 'validation', component: ValidationComponent },
  { path: 'validations', component: ValidationComponent }  //notification
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

