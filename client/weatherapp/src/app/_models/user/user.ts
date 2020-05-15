import { Location } from '../location/location';
import { Photo } from '../photo/photo';

export class User {
    public username: string;
    public email: string;
    public firstName: string;
    public lastName: string;
    public locations: Location[];
    public id: string;
    public profilePicture: Photo;

    constructor(init: any) {
        this.username = init.username;
        this.email = init.email;
        this.firstName = init.firstName;
        this.lastName = init.lastName;
        this.locations = init.locations;
        this.id = init.id;
        this.profilePicture = init.profilePicture;
    }
}
