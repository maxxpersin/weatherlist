export class Location {
    public lat: string;
    public lon: string;
    public name: string;
    public owner: string;
    public id: string;
    constructor(init: any) {
        this.lat = init.lat;
        this.lon = init.lon;
        this.name = init.name;
        this.owner = init.owner;
        this.id = init.id;
    }
}
