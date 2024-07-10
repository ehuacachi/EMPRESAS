import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Marca } from '../model/marca';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MarcaService extends GenericService<Marca>{

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/marcas`)
  }
}
