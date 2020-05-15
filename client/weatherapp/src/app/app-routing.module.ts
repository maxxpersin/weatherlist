import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './_utilities/auth-guard/auth-guard';
import { LoginComponent } from './_components/login/login.component';
import { AboutComponent } from './_components/about/about.component';
import { ProfileComponent } from './_components/profile/profile.component';
import { HomeComponent } from './_components/home/home.component';
import { AddComponent } from './_components/add/add.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'about', component: AboutComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'add', component: AddComponent, canActivate: [AuthGuard]},
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
