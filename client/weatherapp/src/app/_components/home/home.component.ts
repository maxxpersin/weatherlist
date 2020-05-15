import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/_services/api/api.service';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';
import { FormGroup, FormControl } from '@angular/forms';
import { Location } from '../../_models/location/location';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user = this.auth.currentUserValue;
  photoLink: string = '';
  loading = false;
  locationForm = new FormGroup({
    location: new FormControl()
  });

  locations: Location[];
  public selectedLocation: any;

  constructor(
    private api: ApiService,
    private auth: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.api.getLocations()
      .subscribe(
        data => {
          this.locations = data;
        }, error => {
          console.log(error);
        }
      )
  }

  onSubmit() {
    console.log(this.locationForm.value);
  }

  updateSelectedLocation() {
    this.loading = true;
    let loc = this.locationForm.value;
    this.api.getLocation(loc.location)
      .subscribe(
        data => {
          this.selectedLocation = data.currentobservation;
          let photoName = this.selectedLocation.Weatherimage;
          let links = data.data.iconLink;
          links.forEach(link => {
            if (link.split('/')[link.split('/').length - 1] == photoName) {
              this.photoLink = link;
            }
          });
          this.loading = false;
        }, error => {
          this.loading = false;
          if (error.status >= 400 && error.status < 500) {
            this.auth.logout();
          }
        }
      )
  }

}
