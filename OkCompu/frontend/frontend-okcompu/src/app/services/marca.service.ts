import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Marca } from '../model/marca';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarcaService extends GenericService<Marca>{

  private marcaChange: Subject<Marca[]> = new Subject<Marca[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/marcas`)
  }

  setMarcaChange(data: Marca[]){
    this.marcaChange.next(data);
  }

  getMarcaChange(){
    return this.marcaChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}
