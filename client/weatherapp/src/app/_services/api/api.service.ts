import { Injectable } from '@angular/core';
import { Interceptor } from '../../_utilities/interceptor/interceptor';
import { AuthenticationService } from '../authentication/authentication.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Photo } from 'src/app/_models/photo/photo';
import { Location } from '../../_models/location/location';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  config = 'api/v1';

  constructor(
    private interceptor: Interceptor,
    private auth: AuthenticationService,
    private http: HttpClient
  ) { }

  public searchLatLong(query: any): Observable<any> {
    return this.http.get<any>(`${this.config}/users/${this.auth.currentUserValue.id}/locations/gov?lat=${query.latitude}&lon=${query.longitude}`, { headers: { 'X-XSRF-TOKEN': this.auth.getCSRF() } });
  }

  public saveLocation(loc: any): Observable<Location> {
    return this.http.post<Location>(`${this.config}/users/${this.auth.currentUserValue.id}/locations`, loc, { headers: { 'X-XSRF-TOKEN': this.auth.getCSRF() } });
  }

  public getLocations(): Observable<Location[]> {
    return this.http.get<Location[]>(`${this.config}/users/${this.auth.currentUserValue.id}/locations`, { headers: { 'X-XSRF-TOKEN': this.auth.getCSRF() } });
  }

  public getLocation(lid: string): Observable<any> {
    return this.http.get<any>(`${this.config}/users/${this.auth.currentUserValue.id}/locations/${lid}`, { headers: { 'X-XSRF-TOKEN': this.auth.getCSRF() } });
  }

}
