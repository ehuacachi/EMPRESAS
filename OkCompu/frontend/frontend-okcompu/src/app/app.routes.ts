import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MonedaComponent } from './pages/moneda/moneda.component';
import { MonedaEditComponent } from './pages/moneda/moneda-edit/moneda-edit.component';

export const routes: Routes = [
    // {path: '', redirectTo: 'login', pathMatch: 'full'},
    // {path: 'login', component: LoginComponent},
    { path: 'pages/moneda', component: MonedaComponent, children: [
        { path: 'new', component: MonedaEditComponent },
        { path: 'edit/:id', component: MonedaEditComponent }
    ]
    },
    
];
