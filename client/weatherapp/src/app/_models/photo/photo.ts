import { Binary } from '@angular/compiler';

export class Photo {
    public id: string;
    public owner: string;
    public image: any;
    public type: string;

    constructor(init: any) {
        this.id = init.id;
        this.owner = init.owner;
        this.image = init.image;
        this.type = init.type;
    }
}
