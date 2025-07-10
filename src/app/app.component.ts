import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentUrl: string = '';

  constructor(private router: Router, private titleService: Title) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event) => {
      const navEndEvent = event as NavigationEnd; // ✅ cast ici
      this.currentUrl = navEndEvent.urlAfterRedirects;
      this.setTitle(this.currentUrl);
    });
  }

  setTitle(url: string) {
    let pageTitle = 'RHConge';
    if (url.includes('/home')) pageTitle = 'Home | RHConge';
    else if (url.includes('/demande')) pageTitle = 'Demande | RHConge';
    else if (url.includes('/validation')) pageTitle = 'Validation | RHConge';
    else if (url.includes('/login')) pageTitle = 'Connexion | RHConge';
    else if (url.includes('/register')) pageTitle = 'Inscription | RHConge';
    this.titleService.setTitle(pageTitle);
  }

  isLoginPage(): boolean {
    return this.currentUrl === '/login' || this.currentUrl === '/register';
  }
}
